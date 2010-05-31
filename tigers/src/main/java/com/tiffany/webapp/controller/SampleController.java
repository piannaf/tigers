package com.tiffany.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tiffany.model.Sample;
import com.tiffany.service.GenericManager;

public class SampleController implements Controller{
        private final Log log = LogFactory.getLog(SampleController.class);
        private GenericManager<Sample, Long> sampleManager = null;
        
        public void setSampleManager(GenericManager<Sample, Long> sampleManager) {
                this.sampleManager = sampleManager;
        }
        
        public ModelAndView handleRequest(HttpServletRequest request, 
                        HttpServletResponse response) throws Exception {
                log.debug("entering 'handleRequest' method...");
                return new ModelAndView().addObject(sampleManager.getAll());
        }
}

