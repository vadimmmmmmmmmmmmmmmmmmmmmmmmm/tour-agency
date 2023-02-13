package com.razkuuuuuuu.touragency.db.service;

import com.razkuuuuuuu.touragency.db.TransactionManager;
import com.razkuuuuuuu.touragency.db.dao.OrderDao;
import com.razkuuuuuuu.touragency.db.dao.TourDao;
import com.razkuuuuuuu.touragency.entities.entity.Order;
import com.razkuuuuuuu.touragency.exception.AppException;
import jakarta.servlet.ServletContext;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.*;
import static com.razkuuuuuuu.touragency.constants.ErrorMessages.DATABASE_ERROR;

public class OrderService {
    private static final Logger log = Logger.getLogger(OrderService.class);
    private final TransactionManager transactionManager;
    private final ServletContext servletContext;
    public OrderService(ServletContext servletContext) {
        this.servletContext = servletContext;
        this.transactionManager = (TransactionManager) servletContext.getAttribute(TRANSACTION_MANAGER);
    }

    public void cancelAllUserRegisteredOrders(int userId) throws AppException{
        //language=MySQL
        String query = "UPDATE orders SET orders.state='cancelled' WHERE orders.user_id=?";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
            statement.setInt(1,userId);
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            dao.update(statement);
            transactionManager.commit(connection);
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public List<Order> getCustomerOrders(int userId, String userLocale) throws AppException{
        //language=MySQL
        String query = "SELECT orders.id AS order_id," +
                "       tour.id AS tour_id," +
                "       tour.type AS tour_type," +
                "       tour.image_file AS tour_image," +
                "       tour_title_localisation.title_text AS tour_title," +
                "       orders.user_id AS user_id," +
                "       CONCAT(user.name,' ', user.surname) AS user_full_name," +
                "       orders.ticket_count," +
                "       orders.total_price," +
                "       orders.state " +
                "FROM orders " +
                "   JOIN tour ON tour.id=orders.tour_id " +
                "   JOIN tour_title_localisation on tour_title_localisation.title_tour_id=tour.id " +
                "   JOIN user ON orders.user_id=user.id " +
                "WHERE " + setSupportedLanguageString(userLocale) +
                "   AND orders.user_id="+userId+" " +
                "GROUP BY orders.id";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection,query)){
            OrderDao dao = (OrderDao) servletContext.getAttribute(ORDER_DAO);
            return dao.getAll(statement);
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public List<Order> getUnregisteredOrders() throws AppException {
        //language=MySQL
        String query = "SELECT orders.id AS order_id," +
                "       tour.id AS tour_id," +
                "       tour.type AS tour_type," +
                "       tour.image_file AS tour_image," +
                "       tour_title_localisation.title_text AS tour_title," +
                "       orders.user_id AS user_id," +
                "       CONCAT(user.name,' ', user.surname) AS user_full_name," +
                "       orders.ticket_count," +
                "       orders.total_price," +
                "       orders.state " +
                "FROM orders " +
                "   JOIN tour ON tour.id=orders.tour_id " +
                "   JOIN tour_title_localisation on tour_title_localisation.title_tour_id=tour.id " +
                "   JOIN user ON orders.user_id=user.id " +
                "WHERE orders.state='registered' " +
                "GROUP BY orders.id";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection,query)){
            OrderDao dao = (OrderDao) servletContext.getAttribute(ORDER_DAO);
            return dao.getAll(statement);
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    private String setSupportedLanguageString(String value) {
        return "tour.supported_languages LIKE '%"+value+"%' " +
                "AND tour_title_localisation.language='"+value+"' ";
    }

    public boolean register(int userId, int tourId, int count, BigDecimal price) throws AppException {
        //language=MySQL
        String query = "INSERT INTO orders  " +
                "            (orders.user_id,  " +
                "             orders.tour_id,  " +
                "             orders.ticket_count,  " +
                "             orders.total_price,  " +
                "             orders.state)  " +
                "VALUES      (?,  " +
                "             ?,  " +
                "             ?,  " +
                "             ?,  " +
                "             'registered');  ";
        int result;
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection,query)){
            statement.setInt(1,userId);
            statement.setInt(2,tourId);
            statement.setInt(3,count);
            statement.setBigDecimal(4,price);

            OrderDao dao = (OrderDao) servletContext.getAttribute(ORDER_DAO);
            result = dao.add(statement);
            transactionManager.commit(connection);
            return result==1;
        } catch (SQLException exception) {
            log.error(exception.getMessage(), exception);
            throw new AppException(DATABASE_ERROR);
        }
    }

    public boolean changeOrderState(Integer orderId, String newState) throws AppException {
        //language=MySQL
        String query = "UPDATE orders SET orders.state = '" + newState + "' WHERE orders.id="+orderId;
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection,query)){
            OrderDao dao = (OrderDao) servletContext.getAttribute(ORDER_DAO);
            int result = dao.update(statement);
            transactionManager.commit(connection);
            return result==1;
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public boolean removeAllOrdersWithTourId(int tourId) throws AppException{
        //language=MySQL
        String query = "DELETE FROM orders WHERE orders.tour_id=?";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection,query)){
            statement.setInt(1,tourId);
            OrderDao dao = (OrderDao) servletContext.getAttribute(ORDER_DAO);
            dao.update(statement);
            transactionManager.commit(connection);
            return true;
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public int getOrderTourId(int orderId) throws AppException{
        //language=MySQL
        String query = "SELECT orders.tour_id FROM orders WHERE id=?";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection,query)){
            statement.setInt(1,orderId);

            OrderDao dao = (OrderDao) servletContext.getAttribute(ORDER_DAO);
            return dao.getId(statement);
        } catch (SQLException e) {
            throw new AppException();
        }
    }
}
