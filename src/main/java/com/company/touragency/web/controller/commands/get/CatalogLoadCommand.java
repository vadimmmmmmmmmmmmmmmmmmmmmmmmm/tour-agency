package com.razkuuuuuuu.touragency.web.controller.commands.get;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.entities.bean.CatalogFilterProperties;
import com.razkuuuuuuu.touragency.web.message.Message;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommand;
import com.razkuuuuuuu.touragency.entities.entity.Tour;
import com.razkuuuuuuu.touragency.db.service.TourService;
import com.razkuuuuuuu.touragency.exception.AppException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.JspFiles.*;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.*;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.*;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.*;
import static com.razkuuuuuuu.touragency.web.message.MessageNames.INVALID_DATA;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.ERROR_PAGE;

public class CatalogLoadCommand extends LoadCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //check not required
        return true;
    }

    @Override
    public void fillRequestData(HttpServletRequest request, HttpServletResponse response, Integer role) throws AppException {
        //get TourService object from servlet context;
        ServletContext context = request.getServletContext();
        TourService tourService = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getTourService(context);

        //set items per page count
        int pageItemsCount=4;

        //get page number in string
        String pageNumberString = request.getParameter(PAGE_NUMBER);

        int pageNumber;
        try {
            //try parsing page number string
            pageNumber = pageNumberString!=null ? Integer.parseInt(pageNumberString) : 1;
        } catch (NumberFormatException e) {
            //trigger redirect to error page on fail
            throw new AppException(INVALID_DATA);
        }

        //setup catalog filter properties
        CatalogFilterProperties properties = new CatalogFilterProperties();
        try {
            properties = setupProperties(request, role);
        } catch (AppException e) {
            addMessage(request.getSession(), Message.MessageType.ERROR, INVALID_DATA);
        }

        List<Tour> tours = (role<MANAGER)
                //get tours in user locale only
                ? tourService.getFilteredUserCatalogAtPage(pageNumber, pageItemsCount, properties)
                //get tours in all locales with '-' on unsupported locales
                : tourService.getFilteredAdminCatalogAtPage(pageNumber,pageItemsCount,properties);

        //count pages count using pageItemsCount value
        double pageCount = Math.ceil((double) ((role<MANAGER) ? tourService.getUserCatalogFilteredItemsCount(properties) : tourService.getAdminCatalogFilteredItemsCount(properties))/pageItemsCount);

        //if items count is less than set count per page, make it 1
        if(pageCount==0) pageCount=1;

        //add page number to requestScope
        request.setAttribute(PAGE_NUMBER, pageNumber);
        //add page count to requestScope
        request.setAttribute(PAGE_COUNT, (int) pageCount);
        //add tour list to requestScope
        request.setAttribute(TOUR_LIST, tours);
    }

    @Override
    public Reroute proceed(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //single-locale cases
        if (role.equals(NO_USER)) {
            return new Reroute(FORWARD, NO_USER_CATALOG_JSP);
        }
        if (role.equals(CUSTOMER)) {
            return new Reroute(FORWARD, CUSTOMER_CATALOG_JSP);
        }
        //multiple-locale cases
        if (role.equals(MANAGER)) {
            return new Reroute(FORWARD, MANAGER_CATALOG_JSP);
        }
        if (role.equals(ADMIN)) {
            return new Reroute(FORWARD, ADMIN_CATALOG_JSP);
        }
        //trigger error if role value isn't correct
        return new Reroute(REDIRECT, ERROR_PAGE);
    }

    private CatalogFilterProperties setupProperties(HttpServletRequest request, Integer role) throws AppException {
        CatalogFilterProperties properties = null;
        //get properties.toString() value from url
        String propertiesString = request.getParameter(FILTER_PROPERTIES);
        //if url contains it, parse
        if (propertiesString!=null) {
            propertiesString="filter="+propertiesString;
            properties = CatalogFilterProperties.fromString(propertiesString);
        }
        //if it does not, fill with default values
        if (properties == null) {
            properties = new CatalogFilterProperties();
            properties.setExcursionSelected(true);
            properties.setShoppingSelected(true);
            properties.setVacationSelected(true);
            properties.setSortCode(0);
        }
        //set locale restrictions according to role
        properties.setMustBeInUserLocale(role<MANAGER);
        //show only tours available to book for all roles below admin
        if (role<ADMIN) {
            properties.setTicketsAvailable(true);
            properties.setBeginDateAfterCurrent(true);
        //otherwise, show every tour in database
        } else {
            properties.setTicketsAvailable(false);
            properties.setBeginDateAfterCurrent(false);
        }

        //add user's locale to properties
        properties.setUserLocale((String) request.getSession().getAttribute(LOCALE));

        String propertiesValue = properties.toString();
        propertiesValue = propertiesValue.substring(7);

        //add properties to requestScope to show on jsp
        try {
            request.setAttribute(FILTER_PROPERTIES, URLEncoder.encode(propertiesValue, String.valueOf(StandardCharsets.UTF_8)));
        } catch (UnsupportedEncodingException e) {
            throw new AppException();
        }

        return properties;
    }
}
