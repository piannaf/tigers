package com.tiffany.webapp.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tiffany.model.Sample;
import com.tiffany.model.User;
import com.tiffany.service.GenericManager;
import com.tiffany.service.SampleManager;
import com.tiffany.service.UserManager;

public class SampleController extends BaseFormController implements Controller{
        private final Log log = LogFactory.getLog(SampleController.class);
        private SampleManager sampleManager = null;
        private UserManager userManager = null;
        
        public void setSampleManager(SampleManager sampleManager) {
                this.sampleManager = sampleManager;
        }
        
        public void setUserManager(UserManager userManager) {
	    this.userManager = userManager;
	}

	public ModelAndView handleRequest(HttpServletRequest request, 
                        HttpServletResponse response) throws Exception {
                log.debug("entering 'handleRequest' method...");
                
                String from = (String)request.getParameter("from");
                String to = (String)request.getParameter("to");
                
                User me = userManager.getUserByUsername((request
    		    .getRemoteUser()));
                
                
                if( from != null && to != null) {
                    SimpleDateFormat dateFormat = 
                        new SimpleDateFormat("yyyy-MM-dd");
                    Date fromDate = dateFormat.parse(from);
                    Date toDate = dateFormat.parse(to);
                    
                    if(fromDate.compareTo(toDate) > 0) {
                        Locale locale = request.getLocale();
                	saveMessage(request, getText("sample.swapDates", locale));
                    	return new ModelAndView().addObject(
                    		sampleManager.findSamplesByLab(me));
                    }
                    
                    List<Sample> sampleList = 
                		sampleManager.findSamplesByLabAndDateRange(
                			me, fromDate, toDate);
                    log.debug("SampleList: " + sampleList.toString());
                    
                    return new ModelAndView().addObject("sampleList", sampleList);
                }
                
                return new ModelAndView().addObject(sampleManager.findSamplesByLab(me));
        }
}

