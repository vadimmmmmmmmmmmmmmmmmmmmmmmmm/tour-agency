package com.razkuuuuuuu.touragency.web.controller.commands.get.base;

public class PathRegex {
    public static String ROOT_REGEX = "^\\/$";
    public static final String ERROR_REGEX = "^\\/error$";
    public static final String BANNED_REGEX = "^\\/banned$";

    public static final String SIGN_IN_REGEX = "^\\/sign_in$";
    public static final String SIGN_UP_REGEX = "^\\/sign_up$";

    public static final String CATALOG_REGEX = "^\\/catalog$";

    public static final String TOUR_INFO_REGEX = "^\\/catalog\\/tour$";
    public static final String BOOK_TOUR_REGEX = "^\\/catalog\\/book$";
    public static String EDIT_TOUR_REGEX = "^\\/catalog\\/edit_tour$";
    public static final String CREATE_TOUR_REGEX = "^\\/catalog\\/create$";

    public static final String PROFILE_REGEX = "^\\/profile$";
    public static String EDIT_PROFILE_REGEX = "^\\/profile\\/edit$";
    public static String BOOKING_REGEX = "^\\/profile\\/booking$";
    public static String MANAGER_ORDERS_REGEX = "^\\/orders$";
    public static final String USERS_LIST_REGEX = "^\\/users$";
    public static String EDIT_CUSTOMER_REGEX = "^\\/users\\/edit_customer$";
    public static String EDIT_EMPLOYEE_REGEX = "^\\/users\\/edit_employee$";
    public static final String ADD_EMPLOYEE_REGEX = "^\\/users\\/add$";
    public static final String CUSTOMER_BOOKING_REGEX = "^\\/bookings$";
}
