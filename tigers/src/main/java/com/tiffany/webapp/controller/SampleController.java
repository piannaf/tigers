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
import com.tiffany.service.GenericManager;
import com.tiffany.service.SampleManager;

public class SampleController implements Controller{
        private final Log log = LogFactory.getLog(SampleController.class);
        private GenericManager<Sample, Long> sampleManager = null;
        
        public void setSampleManager(GenericManager<Sample, Long> sampleManager) {
                this.sampleManager = sampleManager;
        }
        
        public ModelAndView handleRequest(HttpServletRequest request, 
                        HttpServletResponse response) throws Exception {
                log.debug("entering 'handleRequest' method...");
                
                String from = (String)request.getParameter("from");
                String to = (String)request.getParameter("to");
                
                if( from != null && to != null) {
                    SimpleDateFormat dateFormat = 
                        new SimpleDateFormat("yyyy-MM-dd");
                    Date fromDate = dateFormat.parse(from);
                    Date toDate = dateFormat.parse(to);
                    
                    if(fromDate.compareTo(toDate) > 0) {
                    	return new ModelAndView().addObject(sampleManager.getAll());
                    }
                    
                    List<Sample> sampleList = ((SampleManager)sampleManager).findSampleByDateRange(fromDate, toDate);
                    log.debug("SampleList: " + sampleList.toString());
                    
                    return new ModelAndView().addObject("sampleList", sampleList);
                }
                
                return new ModelAndView().addObject(sampleManager.getAll());
        }
}

