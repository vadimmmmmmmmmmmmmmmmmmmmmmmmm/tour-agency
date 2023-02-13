package com.razkuuuuuuu.touragency.db.service;

import com.razkuuuuuuu.touragency.db.TransactionManager;
import com.razkuuuuuuu.touragency.db.dao.CityDao;
import com.razkuuuuuuu.touragency.entities.entity.City;
import com.razkuuuuuuu.touragency.exception.AppException;
import jakarta.servlet.ServletContext;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.*;

public class CityService {
    private final TransactionManager transactionManager;
    private final ServletContext servletContext;

    public CityService(ServletContext servletContext) {
        this.servletContext = servletContext;
        this.transactionManager = (TransactionManager) servletContext.getAttribute(TRANSACTION_MANAGER);
    }

    public List<City> getAllCitiesWUserLocalePreferable(String locale) throws AppException {
        //language=MySQL
        String query = "SELECT city.id, " +
                "       cnl.name " +
                "FROM   city " +
                "       LEFT JOIN city_name_localisation cnl " +
                "              ON city.id = cnl.city_id  " +
                "WHERE cnl.language=? " +
                "GROUP BY city.id ";

        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)) {
             statement.setString(1, locale);
             CityDao cityDao = (CityDao) servletContext.getAttribute(CITY_DAO);
             return cityDao.getAll(statement);
        } catch (SQLException e) {
            throw new AppException("database error");
        }
    }


    public int addCity() throws AppException{
        //language=MySQL
        String addQuery = "INSERT INTO city (city.id) VALUES (DEFAULT);";
        //language=MySQL
        String getIdQuery ="SELECT LAST_INSERT_ID();";

        CityDao cityDao = (CityDao) servletContext.getAttribute(CITY_DAO);

        try (Connection connection = transactionManager.getConnection()) {
            try (PreparedStatement preparedStatement = transactionManager.getPreparedStatement(connection,addQuery)) {
                cityDao.add(preparedStatement);
            }
            transactionManager.commit(connection);
            try (PreparedStatement preparedStatement = transactionManager.getPreparedStatement(connection, getIdQuery)) {
                return cityDao.getId(preparedStatement);
            }
        } catch (SQLException e) {
            throw new AppException("database error");
        }
    }

    public void setCityLocalisation(int cityId, String englishName, String ukrainianName, String russianName) throws AppException {
        //language=MySQL
        String query = "INSERT INTO city_name_localisation (city_name_localisation.city_id, city_name_localisation.language, city_name_localisation.name) VALUES " +
                "(? , 'en' , ?), " +
                "(? , 'ua' , ?), " +
                "(? , 'ru' , ?)";

        try (Connection connection = transactionManager.getConnection();
             PreparedStatement preparedStatement = transactionManager.getPreparedStatement(connection, query)) {
            preparedStatement.setInt(1, cityId);
            preparedStatement.setString(2, englishName);
            preparedStatement.setInt(3, cityId);
            preparedStatement.setString(4, ukrainianName);
            preparedStatement.setInt(5, cityId);
            preparedStatement.setString(6, russianName);

            CityDao dao = (CityDao) servletContext.getAttribute(CITY_DAO);
            dao.update(preparedStatement);
            transactionManager.commit(connection);

        } catch (SQLException e) {
            throw new AppException("database error");
        }
    }
}
