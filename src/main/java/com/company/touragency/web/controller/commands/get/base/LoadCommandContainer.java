package com.razkuuuuuuu.touragency.web.controller.commands.get.base;

import com.razkuuuuuuu.touragency.web.controller.commands.Command;
import com.razkuuuuuuu.touragency.web.controller.commands.get.*;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.PathRegex.*;
import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommandName.*;

public class LoadCommandContainer {
    private static final Logger log = Logger.getLogger(LoadCommandContainer.class);
    private final Map<LoadCommandName, LoadCommand> commandMap = new HashMap<>();
    private final Map<String, LoadCommandName> pathRegexDefinedCommandNamesMap = new HashMap<>();

    /**
     * Fill maps with command-related pairs
     */
    public LoadCommandContainer() {
        fillCommandMap();
        fillPathNameMap();
        if(commandMap.size()!= pathRegexDefinedCommandNamesMap.size()) {
            log.error("Regex to name and name to objects maps size differ");
        }
        log.info("LoadCommand container has been successfully filled with command objects.");
    }

    /**
     * Retrieve command from map by its name.
     * @param name name of the command
     * @return Command to be executed in Controller.
     */
    public Command getCommand(LoadCommandName name) {
        if (commandMap.containsKey(name)) {
            log.info("LoadCommand ("+name+") was found, proceeding to controller");
            return commandMap.get(name);
        }
        return commandMap.get(name);
    }

    /**
     * fills map with page path regex - loadCommand name pairs
     */
    private void fillPathNameMap() {
        pathRegexDefinedCommandNamesMap.put(CATALOG_REGEX, CATALOG_LOAD_COMMAND);
        pathRegexDefinedCommandNamesMap.put(SIGN_IN_REGEX, SIGN_IN_LOAD_COMMAND);
        pathRegexDefinedCommandNamesMap.put(SIGN_UP_REGEX, SIGN_UP_LOAD_COMMAND);
        pathRegexDefinedCommandNamesMap.put(PROFILE_REGEX, PROFILE_LOAD_COMMAND);
        pathRegexDefinedCommandNamesMap.put(TOUR_INFO_REGEX, TOUR_INFO_LOAD_COMMAND);
        pathRegexDefinedCommandNamesMap.put(BOOK_TOUR_REGEX, BOOK_TOUR_LOAD_COMMAND);
        pathRegexDefinedCommandNamesMap.put(USERS_LIST_REGEX, USERS_LIST_LOAD_COMMAND);
        pathRegexDefinedCommandNamesMap.put(ADD_EMPLOYEE_REGEX, ADD_EMPLOYEE_LOAD_COMMAND);
        pathRegexDefinedCommandNamesMap.put(ERROR_REGEX, ERROR_PAGE_LOAD_COMMAND);
        pathRegexDefinedCommandNamesMap.put(CUSTOMER_BOOKING_REGEX, CUSTOMER_BOOKING_LOAD_COMMAND);
        pathRegexDefinedCommandNamesMap.put(BANNED_REGEX, BANNED_PAGE_LOAD_COMMAND);
        pathRegexDefinedCommandNamesMap.put(CREATE_TOUR_REGEX, CREATE_TOUR_LOAD_COMMAND);
    }

    /**
     * fills map with loadCommand name - loadCommand object pairs
     */
    private void fillCommandMap() {
        commandMap.put(CATALOG_LOAD_COMMAND, new CatalogLoadCommand());
        commandMap.put(SIGN_IN_LOAD_COMMAND, new SignInLoadCommand());
        commandMap.put(SIGN_UP_LOAD_COMMAND, new SignUpLoadCommand());
        commandMap.put(PROFILE_LOAD_COMMAND, new ProfileLoadCommand());
        commandMap.put(TOUR_INFO_LOAD_COMMAND, new TourInfoLoadCommand());
        commandMap.put(BOOK_TOUR_LOAD_COMMAND, new BookTourLoadCommand());
        commandMap.put(USERS_LIST_LOAD_COMMAND, new UsersListLoadCommand());
        commandMap.put(ADD_EMPLOYEE_LOAD_COMMAND, new AddEmployeeLoadCommand());
        commandMap.put(ERROR_PAGE_LOAD_COMMAND, new ErrorPageLoadCommand());
        commandMap.put(CUSTOMER_BOOKING_LOAD_COMMAND, new CustomerBookingLoadCommand());
        commandMap.put(BANNED_PAGE_LOAD_COMMAND, new BannedPageLoadCommand());
        commandMap.put(CREATE_TOUR_LOAD_COMMAND, new CreateTourLoadCommand());
    }

    /**
     * Get loadCommand name if opened page path matches one of regex
     * @param pathInfo path of the current opened page
     * @return name of loadCommand
     */
    public LoadCommandName getLoadCommandNameByRegex(String pathInfo) {
        for(String regex : pathRegexDefinedCommandNamesMap.keySet()) {
            if (pathInfo.matches(regex)) {
                log.info("Found name of command related to current page's address");
                return pathRegexDefinedCommandNamesMap.get(regex);
            }
        }
        log.trace("Current page's path ("+pathInfo+") doesn't match any regex");

        return null;
    }
}
