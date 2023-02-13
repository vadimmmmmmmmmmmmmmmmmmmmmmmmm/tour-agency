package com.razkuuuuuuu.touragency.db.service;

import com.razkuuuuuuu.touragency.db.TransactionManager;
import com.razkuuuuuuu.touragency.db.dao.HotelDao;
import com.razkuuuuuuu.touragency.entities.entity.Hotel;
import com.razkuuuuuuu.touragency.exception.AppException;
import jakarta.servlet.ServletContext;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.*;

public class HotelService {
    private static final Logger log = Logger.getLogger(HotelService.class);
    private final TransactionManager transactionManager;
    private final ServletContext servletContext;

    public HotelService(ServletContext servletContext) {
        this.servletContext = servletContext;
        this.transactionManager = (TransactionManager) servletContext.getAttribute(TRANSACTION_MANAGER);
    }

    public List<Hotel> getAllHotelsInCityWithUserLocalePreferable(int cityId, String locale) throws AppException {
        //language=MySQL
        String query = "SELECT hotel.id,  " +
                "       hnl.name  " +
                "FROM   hotel  " +
                "       LEFT JOIN hotel_name_localisation hnl  " +
                "              ON hotel.id = hnl.hotel_id  " +
                "WHERE  hotel.hotel_city_id=? " +
                "       AND hnl.language=?";

        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection,query)){
            statement.setInt(1,cityId);
            statement.setString(2,locale);
            HotelDao dao = (HotelDao) servletContext.getAttribute(HOTEL_DAO);
            return dao.getAll(statement);
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public int addHotelToCity(int cityId, int starRating) throws AppException{
        //language=MySQL
        String query = "INSERT INTO hotel " +
                "            (hotel.hotel_city_id, " +
                "             hotel.star_rating) " +
                "VALUES      (?, ?)";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection,query)){
            statement.setInt(1,cityId);
            statement.setInt(2,starRating);
            HotelDao dao = (HotelDao) servletContext.getAttribute(HOTEL_DAO);
            if(dao.add(statement)==1) {
                transactionManager.commit(connection);
            }
            return dao.getLastAddedId(statement);
        } catch (SQLException e) {
            throw new AppException();
        }
    }
    public void setHotelNameLocalisation(int hotelId, String englishName, String ukrainianName, String russianName) throws AppException {
        //language=MySQL
        String query = "INSERT INTO hotel_name_localisation " +
                "            (hotel_name_localisation.hotel_id, " +
                "             hotel_name_localisation.language, " +
                "             hotel_name_localisation.name) " +
                "VALUES      (?, " +
                "             'en', " +
                "             ?), " +
                "            (?, " +
                "             'ua', " +
                "             ?), " +
                "            (?, " +
                "             'ru', " +
                "             ?)";

        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection,query)){
            statement.setInt(1, hotelId);
            statement.setString(2, englishName);
            statement.setInt(3, hotelId);
            statement.setString(4, ukrainianName);
            statement.setInt(5, hotelId);
            statement.setString(6, russianName);

            HotelDao dao = (HotelDao) servletContext.getAttribute(HOTEL_DAO);
            if(dao.add(statement)==3) {
                transactionManager.commit(connection);
            }
        } catch (SQLException e) {
            throw new AppException();
        }
    }
}
