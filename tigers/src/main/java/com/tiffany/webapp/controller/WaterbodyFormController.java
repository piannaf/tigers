package com.tiffany.webapp.controller;

import com.tiffany.service.WaterbodyManager;
import com.tiffany.model.Waterbody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * Author: Jane
 */
public class WaterbodyFormController  extends BaseFormController {
/**
 * This class controls the "waterbody" maintenance form
 *
 * @author Jane
 */
	private WaterbodyManager waterbodyManager;

    public void setWaterbodyManager(WaterbodyManager waterbodyManager) {
        this.waterbodyManager = waterbodyManager;
    }

    public WaterbodyFormController() {
        setCommandClass(Waterbody.class);
        setCommandName("waterbody");
        // This stops the creation of a new command object each time the screen is refreshed
        setSessionForm(true);
    }

    protected Object formBackingObject(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            Waterbody waterbody = waterbodyManager.get(new Long(id));
            return waterbody;
        }
        Waterbody waterbody = new Waterbody();
            return waterbody;
    }

    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
    throws Exception {

        Waterbody waterbody = (Waterbody) command;

        boolean isNew = (waterbody.getId() == null);

        String success = getSuccessView();
        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
        	waterbodyManager.remove(waterbody.getId());
        	saveMessage(request, getText("waterbody.deleted", locale));
        } else {
        	waterbody = waterbodyManager.save(waterbody);
        	String key = (isNew) ? "waterbody.added" : "waterbody.updated";
        	saveMessage(request, getText(key, waterbody.getName(), locale));
        }

        return new ModelAndView(success);
    }    
}

