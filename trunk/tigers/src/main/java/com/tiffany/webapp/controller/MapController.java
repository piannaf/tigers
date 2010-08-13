package com.tiffany.webapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import com.tiffany.Constants;
import com.tiffany.model.Sample;
import com.tiffany.model.User;
import com.tiffany.service.RoleManager;
import com.tiffany.service.SampleManager;
import com.tiffany.service.UserExistsException;
import com.tiffany.webapp.util.RequestUtil;
import org.springframework.mail.MailException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
 * Controller to show interactive map.
 *
 */
public class MapController extends BaseFormController {
	private final Log log = LogFactory.getLog(MapController.class);
	
    public MapController() {
    }

    public ModelAndView handleRequest(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
	    log.debug("entering MapController 'handleRequest' method...");
	    
	    return new ModelAndView();
	}
}
