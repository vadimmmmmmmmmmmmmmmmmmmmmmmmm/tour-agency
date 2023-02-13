package com.razkuuuuuuu.touragency.web.controller.commands.post.base;

import com.razkuuuuuuu.touragency.web.controller.commands.Command;
import com.razkuuuuuuu.touragency.web.controller.commands.post.*;

import java.util.HashMap;
import java.util.Map;

import static com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommandNames.*;

public class ActionCommandContainer {
    private final Map<String, ActionCommand> commandMap = new HashMap<>();

    public ActionCommandContainer() {
        fillCommandMap();
    }

    private void fillCommandMap() {
        commandMap.put(SIGN_IN_ACTION_COMMAND, new SignInActionCommand());
        commandMap.put(SIGN_UP_ACTION_COMMAND, new SignUpActionCommand());
        commandMap.put(SIGN_OUT_ACTION_COMMAND, new SignOutActionCommand());
        commandMap.put(CHANGE_LOCALE_ACTION_COMMAND, new ChangeLocaleActionCommand());
        commandMap.put(BOOK_TOUR_ACTION_COMMAND, new BookTourActionCommand());
        commandMap.put(SET_CATALOG_PROPERTIES_ACTION_COMMAND, new SetCatalogPropertiesActionCommand());
        commandMap.put(ADD_EMPLOYEE_ACTION_COMMAND, new AddEmployeeActionCommand());
        commandMap.put(CHANGE_ORDER_STATE_ACTION_COMMAND, new ChangeOrderStateActionCommand());
        commandMap.put(CHANGE_BANNED_STATE_ACTION_COMMAND, new ChangeBannedStateActionCommand());
        commandMap.put(CHANGE_ON_FIRE_STATUS_ACTION_COMMAND, new ChangeOnFireStatusActionCommand());
        commandMap.put(SET_TOUR_DISCOUNT_ACTION_COMMAND, new SetTourDiscountActionCommand());
        commandMap.put(DELETE_TOUR_ACTION_COMMAND, new DeleteTourActionCommand());
        commandMap.put(SET_NEW_TOUR_CITY_ACTION_COMMAND, new SetNewTourCityActionCommand());
        commandMap.put(SET_NEW_TOUR_HOTEL_ACTION_COMMAND, new SetNewTourHotelActionCommand());
        commandMap.put(SET_NEW_TOUR_INFO_ACTION_COMMAND, new SetNewTourInfoActionCommand());
        commandMap.put(CHANGE_TOUR_LOCALISATION_TEXT_ACTION_COMMAND, new ChangeTourLocalisationTextActionCommand());
        commandMap.put(ADD_TOUR_LOCALISATION_TEXT_ACTION_COMMAND, new AddTourLocalisationTextActionCommand());
    }

    public boolean hasCommand(String commandName) {
        return commandMap.containsKey(commandName);
    }

    public Command getCommand(String commandName) {
        return commandMap.get(commandName);
    }
}
