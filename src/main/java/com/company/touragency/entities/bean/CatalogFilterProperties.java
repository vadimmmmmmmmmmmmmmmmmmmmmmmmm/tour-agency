package com.razkuuuuuuu.touragency.entities.bean;

import com.razkuuuuuuu.touragency.exception.AppException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.razkuuuuuuu.touragency.web.message.MessageNames.INVALID_DATA;

public class CatalogFilterProperties implements Serializable {
    private static final String FILTER_PROPERTIES_ATTRIBUTE_REGEX = "^filter=\\{selected:(all|none|(\\[([a-z,]+)]))\\+sortCode:[0-6]}$";
    String userLocale;
    boolean vacationSelected;
    boolean excursionSelected;
    boolean shoppingSelected;

    /**
     * 0 - alphabetical order
     * 1 - price ascending
     * 2 - price descending
     * 3 - hotel rank ascending
     * 4 - hotel rank descending
     * 5 - people count ascending
     * 6 - people count descending
     */
    int sortCode;

    boolean beginDateAfterCurrent;
    boolean ticketsAvailable;

    public boolean isMustBeInUserLocale() {
        return mustBeInUserLocale;
    }

    public void setMustBeInUserLocale(boolean mustBeInUserLocale) {
        this.mustBeInUserLocale = mustBeInUserLocale;
    }

    boolean mustBeInUserLocale;

    public CatalogFilterProperties() {}

    @Override
    public String toString() {
        return "filter={" +
                "selected:"+formSelectedString()+"+"+
                "sortCode:" + sortCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CatalogFilterProperties)) return false;

        CatalogFilterProperties that = (CatalogFilterProperties) o;

        if (vacationSelected != that.vacationSelected) return false;
        if (excursionSelected != that.excursionSelected) return false;
        if (shoppingSelected != that.shoppingSelected) return false;
        return sortCode == that.sortCode;
    }

    @Override
    public int hashCode() {
        int result = (vacationSelected ? 1 : 0);
        result = 31 * result + (excursionSelected ? 1 : 0);
        result = 31 * result + (shoppingSelected ? 1 : 0);
        result = 31 * result + sortCode;
        return result;
    }

    public static CatalogFilterProperties fromString(String toBeParsed) throws AppException {
        if (!toBeParsed.matches(FILTER_PROPERTIES_ATTRIBUTE_REGEX)) {
            throw new AppException(INVALID_DATA);
        }
        CatalogFilterProperties parsed = new CatalogFilterProperties();
        String trimmed = toBeParsed.substring(8, toBeParsed.length()-1);
        String[] properties = trimmed.split("\\+");

        String selectedValue = properties[0].split(":")[1];
        if (selectedValue.equals("all")) {
            parsed.setVacationSelected(true);
            parsed.setExcursionSelected(true);
            parsed.setShoppingSelected(true);
        } else if (selectedValue.equals("none")) {
            parsed.setVacationSelected(false);
            parsed.setExcursionSelected(false);
            parsed.setShoppingSelected(false);
        } else {
            parsed.setVacationSelected(selectedValue.contains("vacation"));
            parsed.setExcursionSelected(selectedValue.contains("excursion"));
            parsed.setShoppingSelected(selectedValue.contains("shopping"));
        }

        String sortCodeValue = properties[1].split(":")[1];
        parsed.setSortCode(Integer.parseInt(sortCodeValue));
        return parsed;
    }

    private String formSelectedString() {
        List<String> selectedList = new ArrayList<>();
        if (vacationSelected) selectedList.add("vacation");
        if (excursionSelected) selectedList.add("excursion");
        if (shoppingSelected) selectedList.add("shopping");
        if (selectedList.size()==3) {
            return "all";
        }
        if (selectedList.size()==0) {
            return "none";
        }
        return '['+ String.join(",", selectedList) +']';
    }


    public String getUserLocale() {
        return userLocale;
    }

    public void setUserLocale(String userLocale) {
        this.userLocale = userLocale;
    }

    public boolean isVacationSelected() {
        return vacationSelected;
    }

    public void setVacationSelected(boolean vacationSelected) {
        this.vacationSelected = vacationSelected;
    }

    public boolean isExcursionSelected() {
        return excursionSelected;
    }

    public void setExcursionSelected(boolean excursionSelected) {
        this.excursionSelected = excursionSelected;
    }

    public boolean isShoppingSelected() {
        return shoppingSelected;
    }

    public void setShoppingSelected(boolean shoppingSelected) {
        this.shoppingSelected = shoppingSelected;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public boolean isBeginDateAfterCurrent() {
        return beginDateAfterCurrent;
    }

    public void setBeginDateAfterCurrent(boolean beginDateAfterCurrent) {
        this.beginDateAfterCurrent = beginDateAfterCurrent;
    }

    public boolean isTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(boolean ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }
}
