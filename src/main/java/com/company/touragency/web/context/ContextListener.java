package com.razkuuuuuuu.touragency.web.context;

import com.razkuuuuuuu.touragency.db.dao.*;
import com.razkuuuuuuu.touragency.db.TransactionManager;
import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.*;

public class ContextListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("Context initialization");
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute(USER_DAO, new UserDao());
        servletContext.setAttribute(TOUR_DAO, new TourDao());
        servletContext.setAttribute(ORDER_DAO, new OrderDao());
        servletContext.setAttribute(CITY_DAO, new CityDao());
        servletContext.setAttribute(HOTEL_DAO, new HotelDao());
        servletContext.setAttribute(SERVICE_CONTAINER, new ServiceContainer());
        servletContext.setAttribute(TOUR_LOCALISATION_INFO_DAO, new TourLocalisationInfoDao());
        servletContext.setAttribute(TRANSACTION_MANAGER, TransactionManager.getInstance());
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Context destroyed");
    }

}
