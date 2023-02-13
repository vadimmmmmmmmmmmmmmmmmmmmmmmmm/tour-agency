package com.razkuuuuuuu.touragency.db.service;

import java.lang.String;

import com.razkuuuuuuu.touragency.db.dao.TourLocalisationInfoDao;
import com.razkuuuuuuu.touragency.entities.bean.CatalogFilterProperties;
import com.razkuuuuuuu.touragency.db.TransactionManager;
import com.razkuuuuuuu.touragency.db.dao.TourDao;
import com.razkuuuuuuu.touragency.entities.bean.TourCreationProperties;
import com.razkuuuuuuu.touragency.entities.entity.TourLocalisationInfo;
import com.razkuuuuuuu.touragency.entities.entity.Tour;
import com.razkuuuuuuu.touragency.exception.AppException;
import jakarta.servlet.ServletContext;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.*;
import static com.razkuuuuuuu.touragency.constants.ErrorMessages.DATABASE_ERROR;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.*;
import static com.razkuuuuuuu.touragency.db.SqlUtils.formatStringValue;

public class TourService {
    private static final Logger log = Logger.getLogger(TourService.class);
    private final TransactionManager transactionManager;
    private final ServletContext servletContext;

    public TourService(ServletContext servletContext) {
        this.servletContext=servletContext;
        this.transactionManager = (TransactionManager) servletContext.getAttribute(TRANSACTION_MANAGER);
    }
    public List<Tour> getFilteredAdminCatalogAtPage(int pageNumber, int pageItemsCount, CatalogFilterProperties properties) throws AppException {
        //language=MySQL
        String query = "SELECT tour.id, " +
                "       tour.on_fire, " +
                "       tour.image_file, " +
                "       titles.en_title, " +
                "       descriptions.en_description, " +
                "       titles.ua_title, " +
                "       descriptions.ua_description, " +
                "       titles.ru_title, " +
                "       descriptions.ru_description, " +
                "       tour.type, " +
                "       tour.ticket_price, " +
                "       tour.ticket_count, " +
                "       Ifnull(registered_tickets.count, 0) AS registered, " +
                "       Ifnull(paid_tickets.count, 0)       AS paid, " +
                "       tour.departure_takeoff_date, " +
                "       tour.departure_takeoff_time, " +
                "       tour.return_takeoff_date, " +
                "       tour.return_takeoff_time, " +
                "       city_name_localisation.name         AS city_name, " +
                "       hotel_name_localisation.name        AS hotel_name, " +
                "       hotel.star_rating, " +
                "       tour.discount_amount, " +
                "       tour.discount_per, " +
                "       tour.discount_max, " +
                "       tour.tour_rating " +
                "FROM   tour " +
                "       JOIN (SELECT ttl.title_tour_id                AS tour_id, " +
                "                    Ifnull(title_en.title_text, '-') AS en_title, " +
                "                    Ifnull(title_ua.title_text, '-') AS ua_title, " +
                "                    Ifnull(title_ru.title_text, '-') AS ru_title " +
                "             FROM   tour_title_localisation ttl " +
                "                    LEFT JOIN (SELECT ttl.title_tour_id           AS tour_id, " +
                "                                      Ifnull(ttl.title_text, '-') AS title_text " +
                "                               FROM   tour_title_localisation ttl " +
                "                               WHERE  language = 'en') AS title_en " +
                "                           ON title_en.tour_id = ttl.title_tour_id " +
                "                    LEFT JOIN (SELECT ttl.title_tour_id           AS tour_id, " +
                "                                      Ifnull(ttl.title_text, '-') AS title_text " +
                "                               FROM   tour_title_localisation ttl " +
                "                               WHERE  language = 'ua') AS title_ua " +
                "                           ON title_ua.tour_id = ttl.title_tour_id " +
                "                    LEFT JOIN (SELECT ttl.title_tour_id           AS tour_id, " +
                "                                      Ifnull(ttl.title_text, '-') AS title_text " +
                "                               FROM   tour_title_localisation ttl " +
                "                               WHERE  language = 'ru') AS title_ru " +
                "                           ON title_ru.tour_id = ttl.title_tour_id " +
                "             GROUP  BY ttl.title_tour_id) AS titles " +
                "         ON titles.tour_id = tour.id " +
                "       JOIN (SELECT tdl.description_tour_id                AS tour_id, " +
                "                    Ifnull(description_en.text, '-') AS en_description, " +
                "                    Ifnull(description_ua.text, '-') AS ua_description, " +
                "                    Ifnull(description_ru.text, '-') AS ru_description " +
                "             FROM   tour_description_localisation tdl " +
                "                    LEFT JOIN (SELECT tdl.description_tour_id           AS tour_id, " +
                "                                      Ifnull(tdl.description_text, '-') AS text " +
                "                               FROM   tour_description_localisation tdl " +
                "                               WHERE  language = 'en') AS description_en " +
                "                           ON description_en.tour_id = tdl.description_tour_id " +
                "                    LEFT JOIN (SELECT tdl.description_tour_id           AS tour_id, " +
                "                                      Ifnull(tdl.description_text, '-') AS text " +
                "                               FROM   tour_description_localisation tdl " +
                "                               WHERE  language = 'ua') AS description_ua " +
                "                           ON description_ua.tour_id = tdl.description_tour_id        " +
                "                    LEFT JOIN (SELECT tdl.description_tour_id           AS tour_id, " +
                "                                      Ifnull(tdl.description_text, '-') AS text " +
                "                               FROM   tour_description_localisation tdl " +
                "                               WHERE  language = 'ru') AS description_ru " +
                "                           ON description_ru.tour_id = tdl.description_tour_id " +
                "             GROUP  BY tdl.description_tour_id) AS descriptions " +
                "         ON descriptions.tour_id = tour.id " +
                "       JOIN tour_description_localisation " +
                "         ON tour.id = tour_description_localisation.description_tour_id " +
                "       JOIN hotel " +
                "         ON hotel.id = tour.hotel_id " +
                "       JOIN hotel_name_localisation " +
                "         ON hotel_name_localisation.hotel_id = hotel.id " +
                "       JOIN city " +
                "         ON city.id = tour.city_id " +
                "       JOIN city_name_localisation " +
                "         ON city_name_localisation.city_id = city.id " +
                "       LEFT JOIN (SELECT Ifnull(Sum(orders.ticket_count), 0) AS count, " +
                "                         Ifnull(orders.tour_id, tour.id)     AS tour_id " +
                "                  FROM   orders " +
                "                         RIGHT JOIN tour " +
                "                                 ON orders.tour_id = tour.id " +
                "                  WHERE  state = 'registered' " +
                "                  GROUP  BY tour_id) AS registered_tickets " +
                "              ON tour.id = registered_tickets.tour_id " +
                "       LEFT JOIN (SELECT Ifnull(Sum(orders.ticket_count), 0) AS count, " +
                "                         Ifnull(orders.tour_id, tour.id)     AS tour_id " +
                "                  FROM   orders " +
                "                         RIGHT JOIN tour " +
                "                                 ON orders.tour_id = tour.id " +
                "                  WHERE  state = 'paid' " +
                "                  GROUP  BY tour_id) AS paid_tickets " +
                "              ON tour.id = paid_tickets.tour_id " +
                "WHERE " + getWhereParametersFromProperties(properties) + " " +
                "GROUP BY tour.id " +
                "ORDER BY tour_rating DESC, tour.on_fire DESC " +
                "LIMIT " + (pageNumber-1)*pageItemsCount + " , " + pageItemsCount;

        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            return dao.getAllExtended(statement);
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public List<Tour> getFilteredUserCatalogAtPage(int pageNumber, int pageItemsCount, CatalogFilterProperties properties) throws AppException {
        //language=MySQL
        String query = "SELECT tour.id, " +
                "       tour.on_fire, " +
                "       tour.image_file, " +
                "       tour_title_localisation.title_text, " +
                "       tour_description_localisation.description_text, " +
                "       tour.type, " +
                "       tour.ticket_price, " +
                "       tour.ticket_count, " +
                "       Ifnull(registered_tickets.count, 0) AS registered, " +
                "       Ifnull(paid_tickets.count, 0)       AS paid, " +
                "       tour.departure_takeoff_date, " +
                "       tour.departure_takeoff_time, " +
                "       tour.return_takeoff_date, " +
                "       tour.return_takeoff_time, " +
                "       city_name_localisation.name         AS city_name, " +
                "       hotel_name_localisation.name        AS hotel_name, " +
                "       hotel.star_rating, " +
                "       tour.discount_amount, " +
                "       tour.discount_per, " +
                "       tour.discount_max, " +
                "       tour.tour_rating " +
                "FROM   tour " +
                "       JOIN tour_title_localisation" +
                "         ON tour.id = tour_title_localisation.title_tour_id " +
                "       JOIN tour_description_localisation " +
                "         ON tour.id = " +
                "            tour_description_localisation.description_tour_id " +
                "       JOIN hotel " +
                "         ON hotel.id = tour.hotel_id " +
                "       JOIN hotel_name_localisation " +
                "         ON hotel_name_localisation.hotel_id = hotel.id " +
                "       JOIN city " +
                "         ON city.id = tour.city_id " +
                "       JOIN city_name_localisation " +
                "         ON city_name_localisation.city_id = city.id " +
                "       LEFT JOIN (SELECT Ifnull(Sum(orders.ticket_count), 0) AS count, " +
                "                         Ifnull(orders.tour_id, tour.id) AS tour_id " +
                "                  FROM   orders " +
                "                         RIGHT JOIN tour " +
                "                                 ON orders.tour_id = tour.id " +
                "                  WHERE  state = 'registered' " +
                "                  GROUP  BY tour_id) AS registered_tickets " +
                "              ON tour.id = registered_tickets.tour_id " +
                "       LEFT JOIN (SELECT Ifnull(Sum(orders.ticket_count), 0) AS count, " +
                "                         Ifnull(orders.tour_id, tour.id) AS tour_id " +
                "                  FROM   orders " +
                "                         RIGHT JOIN tour " +
                "                                 ON orders.tour_id = tour.id " +
                "                  WHERE  state = 'paid' " +
                "                  GROUP  BY tour_id) AS paid_tickets " +
                "              ON tour.id = paid_tickets.tour_id " +
                "WHERE " + getWhereParametersFromProperties(properties) + " " +
                "GROUP BY tour.id, tour_title_localisation.language " +
                "ORDER BY tour_rating DESC, " +
                "tour.on_fire DESC, " + setSortConditionString(properties.getSortCode()) + " " +
                "LIMIT " + (pageNumber-1)*pageItemsCount + " , " + pageItemsCount;

        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            return dao.getAll(statement);
        } catch (SQLException exception) {
            log.error(exception.getMessage(), exception);
            throw new AppException(DATABASE_ERROR);
        }
    }

    private String getWhereParametersFromProperties(CatalogFilterProperties properties) {
        List<String> queryParts = new ArrayList<>();
        if (properties.isMustBeInUserLocale()) {
            queryParts.add(setSupportedLanguageString(properties.getUserLocale()));
        }

        boolean excursionSelected = properties.isExcursionSelected();
        boolean shoppingSelected = properties.isShoppingSelected();
        boolean vacationSelected = properties.isVacationSelected();
        if (excursionSelected || shoppingSelected || vacationSelected) {
            List<String> selectedTours = new ArrayList<>();
            if (excursionSelected) selectedTours.add(EXCURSION);
            if (shoppingSelected) selectedTours.add(SHOPPING);
            if (vacationSelected) selectedTours.add(VACATION);
            queryParts.add('('+selectedTours.stream().map(x -> "tour.type = '" + x + '\'')
                    .collect(Collectors.joining(" OR "))+')');
        } else {
            queryParts.add("NOT (tour.type='vacation' OR tour.type='excursion' OR tour.type='shopping')");
        }
        if (properties.isTicketsAvailable()) {

            queryParts.add("(IFNULL(registered_tickets.count, 0)+IFNULL(paid_tickets.count, 0)<tour.ticket_count)");
        }
        if (properties.isBeginDateAfterCurrent()) {
            queryParts.add("tour.departure_takeoff_date > CURDATE()");
        }

        return String.join(" AND ", queryParts);
    }

    private String setSupportedLanguageString(String value) {
        //language=MySQL
        return "tour.supported_languages LIKE '%"+value+"%' " +
                "AND tour_description_localisation.language='"+value+"' " +
                "AND tour_title_localisation.language='"+value+"' " +
                "AND city_name_localisation.language='"+value+"' " +
                "AND hotel_name_localisation.language='"+value+"' ";
    }

    private String setSortConditionString (Integer condition) {
        if (condition == null) return "";
        return condition == 1 ? "tour.ticket_price ASC" :
               condition == 2 ? "tour.ticket_price DESC" :
               condition == 3 ? "hotel.star_rating ASC" :
               condition == 4 ? "hotel.star_rating DESC" :
               condition == 5 ? "tour.ticket_count ASC" :
               condition == 6 ? "tour.ticket_count DESC" :
               "tour_title_localisation.title_text ASC";
    }


    public Tour getByIdInUserLocale(int id, String locale) throws AppException {
        //language=MySQL
        String query = "SELECT tour.id, " +
                "       tour.on_fire, " +
                "       tour.image_file, " +
                "       tour_title_localisation.title_text, " +
                "       tour_description_localisation.description_text, " +
                "       tour.type, " +
                "       tour.ticket_price, " +
                "       tour.ticket_count, " +
                "       Ifnull(registered_tickets.count, 0) AS registered, " +
                "       Ifnull(paid_tickets.count, 0)       AS paid, " +
                "       tour.departure_takeoff_date, " +
                "       tour.departure_takeoff_time, " +
                "       tour.return_takeoff_date, " +
                "       tour.return_takeoff_time, " +
                "       city_name_localisation.name         AS city_name, " +
                "       hotel_name_localisation.name        AS hotel_name, " +
                "       hotel.star_rating, " +
                "       tour.discount_amount, " +
                "       tour.discount_per, " +
                "       tour.discount_max, " +
                "       tour.tour_rating " +
                "FROM   tour " +
                "       JOIN tour_title_localisation" +
                "         ON tour.id = tour_title_localisation.title_tour_id " +
                "       JOIN tour_description_localisation " +
                "         ON tour.id = " +
                "            tour_description_localisation.description_tour_id " +
                "       JOIN hotel " +
                "         ON hotel.id = tour.hotel_id " +
                "       JOIN hotel_name_localisation " +
                "         ON hotel_name_localisation.hotel_id = hotel.id " +
                "       JOIN city " +
                "         ON city.id = tour.city_id " +
                "       JOIN city_name_localisation " +
                "         ON city_name_localisation.city_id = city.id " +
                "       LEFT JOIN (SELECT Ifnull(Sum(orders.ticket_count), 0) AS count, " +
                "                         Ifnull(orders.tour_id, tour.id) AS tour_id " +
                "                  FROM   orders " +
                "                         RIGHT JOIN tour " +
                "                                 ON orders.tour_id = tour.id " +
                "                  WHERE  state = 'registered' " +
                "                  GROUP  BY tour_id) AS registered_tickets " +
                "              ON tour.id = registered_tickets.tour_id " +
                "       LEFT JOIN (SELECT Ifnull(Sum(orders.ticket_count), 0) AS count, " +
                "                         Ifnull(orders.tour_id, tour.id) AS tour_id " +
                "                  FROM   orders " +
                "                         RIGHT JOIN tour " +
                "                                 ON orders.tour_id = tour.id " +
                "                  WHERE  state = 'paid' " +
                "                  GROUP  BY tour_id) AS paid_tickets " +
                "              ON tour.id = paid_tickets.tour_id " +
                "WHERE "+setSupportedLanguageString(locale)+" AND tour.id="+id;

        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            return dao.getOne(statement);
        } catch (SQLException exception) {
            log.error(exception.getMessage(), exception);
            throw new AppException(DATABASE_ERROR);
        }
    }

    public void changeOnFireStatus(int tourId, boolean newStatus) throws AppException{
        //language=MySQL
        String query = "UPDATE tour SET tour.on_fire=? WHERE tour.id=?";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
            statement.setBoolean(1, newStatus);
            statement.setInt(2, tourId);
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            boolean isSuccessful = dao.update(statement)==1;
            if (isSuccessful) {
                transactionManager.commit(connection);
            } else {
                transactionManager.rollback(connection);
            }
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public boolean setTourDiscount(int tourId, int discount, int perEvery, int maxDiscount) throws AppException{
        //language=MySQL
        String query = "UPDATE tour SET tour.discount_amount=?" +
                ", tour.discount_per=?" +
                ", tour.discount_max=?" +
                "WHERE tour.id=?";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
            statement.setInt(1, discount);
            statement.setInt(2, perEvery);
            statement.setInt(3, maxDiscount);
            statement.setInt(4, tourId);
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            boolean isSuccessful = dao.update(statement)==1;
            if (isSuccessful) {
                transactionManager.commit(connection);
            } else {
                transactionManager.rollback(connection);
            }
            return isSuccessful;
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public boolean removeTour(int tourId) throws AppException{
        //language=MySQL
        String query = "DELETE FROM tour WHERE tour.id=?";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
            statement.setInt(1, tourId);
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            boolean isSuccessful = dao.update(statement)==1;
            if (isSuccessful) {
                transactionManager.commit(connection);
            } else {
                transactionManager.rollback(connection);
            }
            return isSuccessful;
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public int getUserCatalogFilteredItemsCount(CatalogFilterProperties properties) throws AppException{
        //language=MySQL
        String query = "SELECT COUNT(DISTINCT tour.id) " +
                "FROM   tour_agency.tour " +
                "       JOIN tour_title_localisation " +
                "         ON tour.id = tour_title_localisation.title_tour_id " +
                "       JOIN tour_description_localisation " +
                "         ON tour.id = " +
                "            tour_description_localisation.description_tour_id " +
                "       JOIN hotel " +
                "         ON hotel.id = tour.hotel_id " +
                "       JOIN hotel_name_localisation " +
                "         ON hotel_name_localisation.hotel_id = hotel.id " +
                "       JOIN city " +
                "         ON city.id = tour.city_id " +
                "       JOIN city_name_localisation " +
                "         ON city_name_localisation.city_id = city.id " +
                "       LEFT JOIN (SELECT Ifnull(Sum(orders.ticket_count), 0) AS count, " +
                "                         Ifnull(orders.tour_id, tour.id) AS tour_id " +
                "                  FROM   orders " +
                "                         RIGHT JOIN tour " +
                "                                 ON orders.tour_id = tour.id " +
                "                  WHERE  state = 'registered' " +
                "                  GROUP  BY tour_id) AS registered_tickets " +
                "              ON tour.id = registered_tickets.tour_id " +
                "       LEFT JOIN (SELECT Ifnull(Sum(orders.ticket_count), 0) AS count, " +
                "                         Ifnull(orders.tour_id, tour.id) AS tour_id " +
                "                  FROM   orders " +
                "                         RIGHT JOIN tour " +
                "                                 ON orders.tour_id = tour.id " +
                "                  WHERE  state = 'paid' " +
                "                  GROUP  BY tour_id) AS paid_tickets " +
                "              ON tour.id = paid_tickets.tour_id " +
                "WHERE " + getWhereParametersFromProperties(properties);
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            return dao.getCount(statement);
        } catch (SQLException exception) {
            log.error(exception.getMessage(), exception);
            throw new AppException(DATABASE_ERROR);
        }
    }

    public boolean addNewTour(TourCreationProperties properties) throws AppException {

        try (Connection connection = transactionManager.getConnection()){
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);

            //language=MySQL
            String insertTourQuery = "INSERT INTO tour (tour.type, tour.departure_takeoff_date, tour.departure_takeoff_time, tour.return_takeoff_date, tour.return_takeoff_time, tour.city_id, tour.hotel_id, tour.ticket_count, tour.ticket_price, tour.discount_amount, tour.discount_per, tour.discount_max, tour.supported_languages, tour.on_fire) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0);";
            try (PreparedStatement statement = transactionManager.getPreparedStatement(connection, insertTourQuery)){
                statement.setString(1,properties.getTourType());
                statement.setString(2, properties.getDepartureTakeoffDate());
                statement.setString(3, properties.getDepartureTakeoffTime());
                statement.setString(4, properties.getReturnTakeoffDate());
                statement.setString(5, properties.getReturnTakeoffTime());
                statement.setInt(6,properties.getCityId());
                statement.setInt(7,properties.getHotelId());
                statement.setInt(8,properties.getTicketCount());
                statement.setBigDecimal(9,properties.getTicketPrice());
                statement.setInt(10, properties.getDiscountAmount());
                statement.setInt(11, properties.getDiscountPer());
                statement.setInt(12, properties.getDiscountMax());
                statement.setString(13, properties.getLocalisationList().stream().map(TourLocalisationInfo::getLocale).collect(Collectors.joining(",")));
                dao.add(statement);
            }

            transactionManager.commit(connection);

            int tourId;
            //language=MySQL
            String getIdQuery ="SELECT LAST_INSERT_ID();";
            try (PreparedStatement statement = transactionManager.getPreparedStatement(connection, getIdQuery)) {
                tourId = dao.getId(statement);
            }

            if (tourId!=0) {
                try (PreparedStatement statement = transactionManager.getPreparedStatement(connection, generateTitleLocalisationQuery(properties.getLocalisationList(), tourId))) {
                    dao.add(statement);
                }
                transactionManager.commit(connection);

                try (PreparedStatement statement = transactionManager.getPreparedStatement(connection, generateDescriptionLocalisationQuery(properties.getLocalisationList(), tourId))) {
                    dao.add(statement);
                }
                transactionManager.commit(connection);
                return true;
            } else {
                transactionManager.rollback(connection);
                return false;
            }
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    private String generateTitleLocalisationQuery(List<TourLocalisationInfo> localisationList, int tourId) {
        StringBuilder tourTitleQuery = new StringBuilder("INSERT INTO tour_title_localisation " +
                "           (tour_title_localisation.title_tour_id, " +
                "           tour_title_localisation.language, " +
                "           tour_title_localisation.title_text) " +
                "VALUES     ");
        localisationList.forEach(info -> tourTitleQuery.append("(").append(tourId).append(",'").append(info.getLocale()).append("','").append(formatStringValue(info.getTitle())).append("')").append(','));
        tourTitleQuery.delete(tourTitleQuery.length()-1,tourTitleQuery.length());
        return tourTitleQuery.toString();
    }
    private String generateDescriptionLocalisationQuery(List<TourLocalisationInfo> localisationList, int tourId) {
        StringBuilder tourDescriptionQuery = new StringBuilder("INSERT INTO tour_description_localisation " +
                "           (tour_description_localisation.description_tour_id, " +
                "           tour_description_localisation.language, " +
                "           tour_description_localisation.description_text) " +
                "VALUES     ");
        localisationList.forEach(info -> tourDescriptionQuery.append("(").append(tourId).append(",'").append(info.getLocale()).append("','").append(formatStringValue(info.getDescription())).append("')").append(','));
        tourDescriptionQuery.delete(tourDescriptionQuery.length()-1,tourDescriptionQuery.length());
        return tourDescriptionQuery.toString();
    }

    public boolean updateLocale(int tourId, String localeToBeChanged, String newTitle, String newDescription) throws AppException{
        try (Connection connection = transactionManager.getConnection()){
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);

            //language=MySQL
            String updateTitleQuery = "UPDATE tour_title_localisation SET tour_title_localisation.title_text=? WHERE language=? and title_tour_id=?";
            try (PreparedStatement statement = transactionManager.getPreparedStatement(connection, updateTitleQuery)) {
                statement.setString(1,newTitle);
                statement.setString(2,localeToBeChanged);
                statement.setInt(3, tourId);
                dao.update(statement);
            }
            transactionManager.commit(connection);

            //language=MySQL
            String updateDescriptionQuery = "UPDATE tour_description_localisation SET tour_description_localisation.description_text=? WHERE language=? and description_tour_id=?";
            try (PreparedStatement statement = transactionManager.getPreparedStatement(connection, updateDescriptionQuery)) {
                statement.setString(1,newDescription);
                statement.setString(2,localeToBeChanged);
                statement.setInt(3, tourId);
                dao.update(statement);
            }
            transactionManager.commit(connection);

            return true;
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public boolean addLocaleText(int tourId, String newLocale, String newTitle, String newDescription) throws AppException{
        try {
            Connection connection = transactionManager.getConnection();
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            //language=MySQL
            String addTitleQuery = "INSERT INTO tour_title_localisation (title_tour_id, language, title_text) VALUES (?, ?, ?)";
            PreparedStatement statement = transactionManager.getPreparedStatement(connection, addTitleQuery);
            statement.setInt(1, tourId);
            statement.setString(2,newLocale);
            statement.setString(3,newTitle);
            dao.add(statement);
            transactionManager.commit(connection);

            //language=MySQL
            String updateDescriptionQuery = "INSERT INTO tour_description_localisation (description_tour_id, language, description_text) VALUES (?, ?, ?)";
            statement = transactionManager.getPreparedStatement(connection, updateDescriptionQuery);
            statement.setInt(1, tourId);
            statement.setString(2,newLocale);
            statement.setString(3,newDescription);
            dao.add(statement);
            transactionManager.commit(connection);

            return true;
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public Map<String, TourLocalisationInfo> getAllLocales(int tourId) throws AppException {
        //language=MySQL
        String selectWithAllLocalesQuery = "SELECT tour_title_localisation.language, " +
                "           title_text, " +
                "           description_text " +
                "FROM       tour_title_localisation " +
                "JOIN       tour_description_localisation " +
                "           ON tour_title_localisation.language=tour_description_localisation.language " +
                "WHERE      description_tour_id=? AND title_tour_id=? " +
                "GROUP BY   tour_title_localisation.language";

        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection, selectWithAllLocalesQuery)){
            statement.setInt(1, tourId);
            statement.setInt(2, tourId);

            TourLocalisationInfoDao infoDao = (TourLocalisationInfoDao) servletContext.getAttribute(TOUR_LOCALISATION_INFO_DAO);
            HashMap<String, TourLocalisationInfo> languagesMap = new HashMap<>();
            infoDao.getAll(statement).forEach(
                    localisation -> languagesMap.put(localisation.getLocale(), localisation)
            );
            return languagesMap;
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public boolean removeAllLocales(int tourId) throws AppException{
        try (Connection connection = transactionManager.getConnection()){
            TourLocalisationInfoDao dao = (TourLocalisationInfoDao) servletContext.getAttribute(TOUR_LOCALISATION_INFO_DAO);

            //language=MySQL
            String deleteTitleQuery = "DELETE FROM tour_title_localisation WHERE title_tour_id=?";
            try (PreparedStatement statement = transactionManager.getPreparedStatement(connection,deleteTitleQuery)) {
                statement.setInt(1,tourId);
                dao.update(statement);
            }
            transactionManager.commit(connection);

            //language=MySQL
            String deleteDescriptionQuery = "DELETE FROM tour_description_localisation WHERE description_tour_id=?";
            try (PreparedStatement statement = transactionManager.getPreparedStatement(connection,deleteDescriptionQuery)) {
                statement.setInt(1,tourId);
                dao.update(statement);
            }
            transactionManager.commit(connection);

            return true;
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public int getAdminCatalogFilteredItemsCount(CatalogFilterProperties properties) throws AppException {
        //language=MySQL
        String query = "SELECT COUNT(DISTINCT tour.id) " +
                "FROM   tour " +
                "       JOIN (SELECT ttl.title_tour_id                AS tour_id, " +
                "                    Ifnull(title_en.title_text, '-') AS en_title, " +
                "                    Ifnull(title_ua.title_text, '-') AS ua_title, " +
                "                    Ifnull(title_ru.title_text, '-') AS ru_title " +
                "             FROM   tour_title_localisation ttl " +
                "                    LEFT JOIN (SELECT ttl.title_tour_id           AS tour_id, " +
                "                                      Ifnull(ttl.title_text, '-') AS title_text " +
                "                               FROM   tour_title_localisation ttl " +
                "                               WHERE  language = 'en') AS title_en " +
                "                           ON title_en.tour_id = ttl.title_tour_id " +
                "                    LEFT JOIN (SELECT ttl.title_tour_id           AS tour_id, " +
                "                                      Ifnull(ttl.title_text, '-') AS title_text " +
                "                               FROM   tour_title_localisation ttl " +
                "                               WHERE  language = 'ua') AS title_ua " +
                "                           ON title_ua.tour_id = ttl.title_tour_id " +
                "                    LEFT JOIN (SELECT ttl.title_tour_id           AS tour_id, " +
                "                                      Ifnull(ttl.title_text, '-') AS title_text " +
                "                               FROM   tour_title_localisation ttl " +
                "                               WHERE  language = 'ru') AS title_ru " +
                "                           ON title_ru.tour_id = ttl.title_tour_id " +
                "             GROUP  BY ttl.title_tour_id) AS titles " +
                "         ON titles.tour_id = tour.id " +
                "       JOIN (SELECT tdl.description_tour_id                AS tour_id, " +
                "                    Ifnull(description_en.text, '-') AS en_description, " +
                "                    Ifnull(description_ua.text, '-') AS ua_description, " +
                "                    Ifnull(description_ru.text, '-') AS ru_description " +
                "             FROM   tour_description_localisation tdl " +
                "                    LEFT JOIN (SELECT tdl.description_tour_id           AS tour_id, " +
                "                                      Ifnull(tdl.description_text, '-') AS text " +
                "                               FROM   tour_description_localisation tdl " +
                "                               WHERE  language = 'en') AS description_en " +
                "                           ON description_en.tour_id = tdl.description_tour_id " +
                "                    LEFT JOIN (SELECT tdl.description_tour_id           AS tour_id, " +
                "                                      Ifnull(tdl.description_text, '-') AS text " +
                "                               FROM   tour_description_localisation tdl " +
                "                               WHERE  language = 'ua') AS description_ua " +
                "                           ON description_ua.tour_id = tdl.description_tour_id        " +
                "                    LEFT JOIN (SELECT tdl.description_tour_id           AS tour_id, " +
                "                                      Ifnull(tdl.description_text, '-') AS text " +
                "                               FROM   tour_description_localisation tdl " +
                "                               WHERE  language = 'ru') AS description_ru " +
                "                           ON description_ru.tour_id = tdl.description_tour_id " +
                "             GROUP  BY tdl.description_tour_id) AS descriptions " +
                "         ON descriptions.tour_id = tour.id " +
                "       JOIN tour_description_localisation " +
                "         ON tour.id = tour_description_localisation.description_tour_id " +
                "       JOIN hotel " +
                "         ON hotel.id = tour.hotel_id " +
                "       JOIN hotel_name_localisation " +
                "         ON hotel_name_localisation.hotel_id = hotel.id " +
                "       JOIN city " +
                "         ON city.id = tour.city_id " +
                "       JOIN city_name_localisation " +
                "         ON city_name_localisation.city_id = city.id " +
                "       LEFT JOIN (SELECT Ifnull(Sum(orders.ticket_count), 0) AS count, " +
                "                         Ifnull(orders.tour_id, tour.id)     AS tour_id " +
                "                  FROM   orders " +
                "                         RIGHT JOIN tour " +
                "                                 ON orders.tour_id = tour.id " +
                "                  WHERE  state = 'registered' " +
                "                  GROUP  BY tour_id) AS registered_tickets " +
                "              ON tour.id = registered_tickets.tour_id " +
                "       LEFT JOIN (SELECT Ifnull(Sum(orders.ticket_count), 0) AS count, " +
                "                         Ifnull(orders.tour_id, tour.id)     AS tour_id " +
                "                  FROM   orders " +
                "                         RIGHT JOIN tour " +
                "                                 ON orders.tour_id = tour.id " +
                "                  WHERE  state = 'paid' " +
                "                  GROUP  BY tour_id) AS paid_tickets " +
                "              ON tour.id = paid_tickets.tour_id " +
                "WHERE " + getWhereParametersFromProperties(properties);
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            return dao.getCount(statement);
        } catch (SQLException exception) {
            log.error(exception.getMessage(), exception);
            throw new AppException(DATABASE_ERROR);
        }
    }

    public Tour getByIdExtended(int id) throws AppException {
        //language=MySQL
        String query = "SELECT tour.id, " +
                "       tour.on_fire, " +
                "       tour.image_file, " +
                "       titles.en_title, " +
                "       descriptions.en_description, " +
                "       titles.ua_title, " +
                "       descriptions.ua_description, " +
                "       titles.ru_title, " +
                "       descriptions.ru_description, " +
                "       tour.type, " +
                "       tour.ticket_price, " +
                "       tour.ticket_count, " +
                "       Ifnull(registered_tickets.count, 0) AS registered, " +
                "       Ifnull(paid_tickets.count, 0)       AS paid, " +
                "       tour.departure_takeoff_date, " +
                "       tour.departure_takeoff_time, " +
                "       tour.return_takeoff_date, " +
                "       tour.return_takeoff_time, " +
                "       city_name_localisation.name         AS city_name, " +
                "       hotel_name_localisation.name        AS hotel_name, " +
                "       hotel.star_rating, " +
                "       tour.discount_amount, " +
                "       tour.discount_per, " +
                "       tour.discount_max, " +
                "       tour.tour_rating " +
                "FROM   tour " +
                "       JOIN (SELECT ttl.title_tour_id                AS tour_id, " +
                "                    Ifnull(title_en.title_text, '-') AS en_title, " +
                "                    Ifnull(title_ua.title_text, '-') AS ua_title, " +
                "                    Ifnull(title_ru.title_text, '-') AS ru_title " +
                "             FROM   tour_title_localisation ttl " +
                "                    LEFT JOIN (SELECT ttl.title_tour_id           AS tour_id, " +
                "                                      Ifnull(ttl.title_text, '-') AS title_text " +
                "                               FROM   tour_title_localisation ttl " +
                "                               WHERE  language = 'en') AS title_en " +
                "                           ON title_en.tour_id = ttl.title_tour_id " +
                "                    LEFT JOIN (SELECT ttl.title_tour_id           AS tour_id, " +
                "                                      Ifnull(ttl.title_text, '-') AS title_text " +
                "                               FROM   tour_title_localisation ttl " +
                "                               WHERE  language = 'ua') AS title_ua " +
                "                           ON title_ua.tour_id = ttl.title_tour_id " +
                "                    LEFT JOIN (SELECT ttl.title_tour_id           AS tour_id, " +
                "                                      Ifnull(ttl.title_text, '-') AS title_text " +
                "                               FROM   tour_title_localisation ttl " +
                "                               WHERE  language = 'ru') AS title_ru " +
                "                           ON title_ru.tour_id = ttl.title_tour_id " +
                "             GROUP  BY ttl.title_tour_id) AS titles " +
                "         ON titles.tour_id = tour.id " +
                "       JOIN (SELECT tdl.description_tour_id                AS tour_id, " +
                "                    Ifnull(description_en.text, '-') AS en_description, " +
                "                    Ifnull(description_ua.text, '-') AS ua_description, " +
                "                    Ifnull(description_ru.text, '-') AS ru_description " +
                "             FROM   tour_description_localisation tdl " +
                "                    LEFT JOIN (SELECT tdl.description_tour_id           AS tour_id, " +
                "                                      Ifnull(tdl.description_text, '-') AS text " +
                "                               FROM   tour_description_localisation tdl " +
                "                               WHERE  language = 'en') AS description_en " +
                "                           ON description_en.tour_id = tdl.description_tour_id " +
                "                    LEFT JOIN (SELECT tdl.description_tour_id           AS tour_id, " +
                "                                      Ifnull(tdl.description_text, '-') AS text " +
                "                               FROM   tour_description_localisation tdl " +
                "                               WHERE  language = 'ua') AS description_ua " +
                "                           ON description_ua.tour_id = tdl.description_tour_id        " +
                "                    LEFT JOIN (SELECT tdl.description_tour_id           AS tour_id, " +
                "                                      Ifnull(tdl.description_text, '-') AS text " +
                "                               FROM   tour_description_localisation tdl " +
                "                               WHERE  language = 'ru') AS description_ru " +
                "                           ON description_ru.tour_id = tdl.description_tour_id " +
                "             GROUP  BY tdl.description_tour_id) AS descriptions " +
                "         ON descriptions.tour_id = tour.id " +
                "       JOIN tour_description_localisation " +
                "         ON tour.id = tour_description_localisation.description_tour_id " +
                "       JOIN hotel " +
                "         ON hotel.id = tour.hotel_id " +
                "       JOIN hotel_name_localisation " +
                "         ON hotel_name_localisation.hotel_id = hotel.id " +
                "       JOIN city " +
                "         ON city.id = tour.city_id " +
                "       JOIN city_name_localisation " +
                "         ON city_name_localisation.city_id = city.id " +
                "       LEFT JOIN (SELECT Ifnull(Sum(orders.ticket_count), 0) AS count, " +
                "                         Ifnull(orders.tour_id, tour.id)     AS tour_id " +
                "                  FROM   orders " +
                "                         RIGHT JOIN tour " +
                "                                 ON orders.tour_id = tour.id " +
                "                  WHERE  state = 'registered' " +
                "                  GROUP  BY tour_id) AS registered_tickets " +
                "              ON tour.id = registered_tickets.tour_id " +
                "       LEFT JOIN (SELECT Ifnull(Sum(orders.ticket_count), 0) AS count, " +
                "                         Ifnull(orders.tour_id, tour.id)     AS tour_id " +
                "                  FROM   orders " +
                "                         RIGHT JOIN tour " +
                "                                 ON orders.tour_id = tour.id " +
                "                  WHERE  state = 'paid' " +
                "                  GROUP  BY tour_id) AS paid_tickets " +
                "              ON tour.id = paid_tickets.tour_id " +
                "WHERE tour.id="+id;
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            return dao.getOneExtended(statement);
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public boolean addLocale(int tourId, String newLocale) throws AppException {
        //language=MySQL
        String updateQuery = "UPDATE tour SET supported_languages = concat(supported_languages, ',', ?) WHERE id=?";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement preparedStatement = transactionManager.getPreparedStatement(connection, updateQuery)){
            preparedStatement.setString(1, newLocale);
            preparedStatement.setInt(2, tourId);

            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            boolean success = dao.update(preparedStatement)==1;
            transactionManager.commit(connection);
            return success;
        } catch (SQLException e) {
            throw new AppException();
        }
    }



    public boolean increaseRating(int tourId) throws AppException{
        //language=MySQL
        String updateQuery = "UPDATE tour SET tour_rating=(tour_rating+1) WHERE id=?";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement preparedStatement = transactionManager.getPreparedStatement(connection, updateQuery)){
            preparedStatement.setInt(1, tourId);

            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            boolean success = dao.update(preparedStatement)==1;
            transactionManager.commit(connection);
            return success;
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public boolean decreaseRating(int tourId) throws AppException{
        //language=MySQL
        String updateQuery = "UPDATE tour SET tour_rating=(tour_rating-1) WHERE id=?";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement preparedStatement = transactionManager.getPreparedStatement(connection, updateQuery)){
            preparedStatement.setInt(1, tourId);

            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            boolean success = dao.update(preparedStatement)==1;
            transactionManager.commit(connection);
            return success;
        } catch (SQLException e) {
            throw new AppException();
        }
    }

    public void decreaseRatingForAllUserBookings(int userId) throws AppException {
        //language=MySQL
        String updateQuery = "UPDATE tour SET tour_rating=(tour_rating-2) " +
                "WHERE id in (SELECT tour_id FROM orders WHERE user_id=? AND state='paid')";
        try (Connection connection = transactionManager.getConnection();
             PreparedStatement preparedStatement = transactionManager.getPreparedStatement(connection, updateQuery)){
            preparedStatement.setInt(1, userId);

            TourDao dao = (TourDao) servletContext.getAttribute(TOUR_DAO);
            dao.update(preparedStatement);
            transactionManager.commit(connection);
        } catch (SQLException e) {
            throw new AppException();
        }

    }
}
