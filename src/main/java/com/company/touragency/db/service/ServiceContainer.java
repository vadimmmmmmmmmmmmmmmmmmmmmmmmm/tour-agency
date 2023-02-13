package com.razkuuuuuuu.touragency.db.service;

import jakarta.servlet.ServletContext;

public class ServiceContainer {
    public UserService getUserService(ServletContext context) {
        return new UserService(context);
    }
    public TourService getTourService(ServletContext context) {
        return new TourService(context);
    }
    public OrderService getOrderService(ServletContext context) {
        return new OrderService(context);
    }
    public CityService getCityService(ServletContext context) {
        return new CityService(context);
    }
    public HotelService getHotelService(ServletContext context) {
        return new HotelService(context);
    }
}
