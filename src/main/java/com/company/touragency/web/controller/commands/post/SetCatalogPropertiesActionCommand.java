package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.entities.bean.CatalogFilterProperties;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.razkuuuuuuu.touragency.constants.SessionAttributes.*;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.CATALOG_PAGE;

public class SetCatalogPropertiesActionCommand extends ActionCommand {
    final String EXCURSION_SELECTED = "excursion_selected";
    final String SHOPPING_SELECTED = "shopping_selected";
    final String VACATION_SELECTED = "vacation_selected";
    CatalogFilterProperties properties;
    @Override
    protected boolean authorisationCheck(Integer role) {
        //no check required
        return true;
    }
    @Override
    public boolean performAction(HttpServletRequest request, Integer role) {
        properties = new CatalogFilterProperties();
        properties.setUserLocale((String) request.getSession().getAttribute(LOCALE));
        properties.setExcursionSelected(request.getParameter(EXCURSION_SELECTED)!=null);
        properties.setShoppingSelected(request.getParameter(SHOPPING_SELECTED)!=null);
        properties.setVacationSelected(request.getParameter(VACATION_SELECTED)!=null);
        properties.setSortCode(Integer.parseInt(request.getParameter(SORT_CONDITION)));
        return true;
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        String propertiesValue = properties.toString();
        propertiesValue = propertiesValue.substring(7);
        try {
            return new Reroute(REDIRECT, CATALOG_PAGE + "?filter=" + URLEncoder.encode(propertiesValue, String.valueOf(StandardCharsets.UTF_8)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
