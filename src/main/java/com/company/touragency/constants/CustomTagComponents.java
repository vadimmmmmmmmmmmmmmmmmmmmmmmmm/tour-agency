package com.razkuuuuuuu.touragency.constants;

public class CustomTagComponents {
    public static final String DROPDOWN_OPTION_UA = "<option class=\"text-center\" value=\"ua\">\uD83C\uDDFA\uD83C\uDDE6</option>";
    public static final String DROPDOWN_OPTION_EN = "<option class=\"text-center\" value=\"en\">\uD83C\uDDFA\uD83C\uDDF8</option>";
    public static final String DROPDOWN_OPTION_RU = "<option class=\"text-center\" value=\"ru\">\uD83C\uDDF7\uD83C\uDDFA</option>";
    public static final String LOCALE_DROPDOWN_TAG_UPPER_PART = "<form class=\"w-auto\" method=\"post\" name=\"locale_form\">\n" +
            "<input type=\"hidden\" name=\"command\" value=\"change_locale\"/>\n" +
            "<select class=\"form-select form-select-lg border-0 rounded-0 shadow-sm\" id=\"locale\" name=\"locale\" onchange=\"this.form.submit()\">";
    public static final String LOCALE_DROPDOWN_TAG_BOTTOM_PART = "</select>\n" +
            "</form>";
}
