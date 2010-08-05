package com.tiffany.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;

import com.tiffany.webapp.controller.SampleSearch;
import com.tiffany.service.SampleManager;
import com.tiffany.model.Sample;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SampleSearchController extends BaseFormController {
	private SampleManager sampleManager = null;
	
	public SampleSearchController() {
		setCommandClass(SampleSearch.class);
		setCommandName("search");
	}
	
	public void setSampleManager(SampleManager sampleManager) {
		this.sampleManager = sampleManager;
	}
	
	protected Object formBackingObject(HttpServletRequest request) throws Exception {		
		log.debug("entering \"formBackingObject\" method in SampleSearchController...");
		SampleSearch search = new SampleSearch();
		return search;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors) throws Exception {
		log.debug("entering \"onSubmit\" method in SampleSearchController...");
		
		String success = getSuccessView();
		
		SampleSearch search = (SampleSearch)command;
		String tag = search.getSamplerId();
		String from = search.getFrom().trim();
		String to = search.getTo().trim();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = dateFormat.parse("1990-01-01");
		Date toDate = new Date();
		
		if (!from.equals("")) {
			fromDate = dateFormat.parse(from);
		}
		if (!to.equals("")) {
			toDate = dateFormat.parse(to);
		}
		//=============================================================
		List<Sample> sampleList = new ArrayList();
		if (tag.equals("")) {
			sampleList = sampleManager.findSampleByDateRange(fromDate, toDate);
		} else {
			sampleList = sampleManager.findSamplesByTagAndDateRange(tag, fromDate, toDate);
		}
		
		ModelAndView mv = new ModelAndView("officer/samplesearch", getCommandName(), command);
		mv.addObject("sampleList", sampleList);
		mv.addObject("firstTime", "nup");
		return mv;
	}
}
