package com.razkuuuuuuu.touragency.web.tags;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.util.Objects;

import static com.razkuuuuuuu.touragency.constants.CustomTagComponents.*;
import static com.razkuuuuuuu.touragency.web.tags.LocaleNames.*;

public class LocaleSelectTag extends SimpleTagSupport {
    private String type;
    public void setType(String type) {this.type=type;}
    private String locale;
    public void setLocale(String locale) {
        this.locale=locale;
    }
    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        StringBuilder tagContent = new StringBuilder();
        if (type.equals("dropdown")) tagContent.append(formDropdown());
        if (type.equals("radio")) tagContent.append(formRadio());
        out.println(tagContent);
    }

    private StringBuilder formRadio() {
        StringBuilder tag = new StringBuilder();

        tag.append("<form class='mb-0' method='post'>");
            tag.append(formUaLocaleRadio());
                tag.append(formRuLocaleRadio());
                tag.append(formEnLocaleRadio());
            tag.append("<input type='hidden' name='command' value='change_locale'/>");
        tag.append("</form>");

        return tag;
    }

    private String formUaLocaleRadio() {
        return "<div class='form-check form-check-inline'>" +
                  "<input class='form-check-input' type='radio' name='locale'  value='ua' id='uaRadio' onclick='this.form.submit()'"+(locale.equals(LOCALE_UA) ? "checked" : "")+">" +
                  "<label class='form-check-label' for='uaRadio'>\uD83C\uDDFA\uD83C\uDDE6</label>" +
               "</div>";
    }
    private String formRuLocaleRadio() {
        return "<div class='form-check form-check-inline'>" +
                  "<input class='form-check-input' type='radio' name='locale'  value='ru' id='ruRadio' onclick='this.form.submit()'"+(locale.equals(LOCALE_RU) ? "checked" : "")+">" +
                  "<label class='form-check-label' for='ruRadio'>\uD83C\uDDF7\uD83C\uDDFA</label>" +
               "</div>";
    }
    private String formEnLocaleRadio() {
        return "<div class='form-check form-check-inline'>" +
                  "<input class='form-check-input' type='radio' name='locale'  value='en' id='enRadio' onclick='this.form.submit()'"+(locale.equals(LOCALE_EN) ? "checked" : "")+">" +
                  "<label class='form-check-label' for='enRadio'>\uD83C\uDDFA\uD83C\uDDF8</label>" +
               "</div>";
    }

    private StringBuilder formDropdown() {
        StringBuilder tag = new StringBuilder(LOCALE_DROPDOWN_TAG_UPPER_PART);
        if (Objects.equals(locale, LOCALE_RU)) {
            tag.append(DROPDOWN_OPTION_RU);
            tag.append(DROPDOWN_OPTION_UA);
            tag.append(DROPDOWN_OPTION_EN);
            tag.append(LOCALE_DROPDOWN_TAG_BOTTOM_PART);
            return tag;
        }
        if (Objects.equals(locale, LOCALE_UA)) {
            tag.append(DROPDOWN_OPTION_UA);
            tag.append(DROPDOWN_OPTION_RU);
            tag.append(DROPDOWN_OPTION_EN);
            tag.append(LOCALE_DROPDOWN_TAG_BOTTOM_PART);
            return tag;
        }
        if (Objects.equals(locale, LOCALE_EN)) {
            tag.append(DROPDOWN_OPTION_EN);
            tag.append(DROPDOWN_OPTION_UA);
            tag.append(DROPDOWN_OPTION_RU);
            tag.append(LOCALE_DROPDOWN_TAG_BOTTOM_PART);
            return tag;
        }
        return null;
    }
}
