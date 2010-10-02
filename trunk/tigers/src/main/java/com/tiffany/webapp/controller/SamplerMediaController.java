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

import com.tiffany.model.SamplerMedia;
import com.tiffany.service.GenericManager;
import com.tiffany.service.SamplerMediaManager;

public class SamplerMediaController implements Controller{
        private GenericManager<SamplerMedia, Long> samplerMediaManager = null;
        
        public void setSamplerMediaManager(GenericManager<SamplerMedia, Long> samplerMediaManager) {
                this.samplerMediaManager = samplerMediaManager;
        }
        
        public ModelAndView handleRequest(HttpServletRequest request, 
                        HttpServletResponse response) throws Exception {
                
                String tag = (String)request.getParameter("tag");
                if( tag == null ) throw new Exception("No tag specified");
				
				List<SamplerMedia> samplerMediaList = ((SamplerMediaManager)samplerMediaManager).findByTag(tag);
				return new ModelAndView().addObject("samplerMediaList", samplerMediaList).addObject("tag", tag);
        }
}

