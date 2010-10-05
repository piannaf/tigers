package com.tiffany.webapp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.tiffany.model.Sample;
import com.tiffany.model.Sampler;
import com.tiffany.model.User;
import com.tiffany.service.SampleManager;
import com.tiffany.service.SamplerManager;
import com.tiffany.service.UserManager;

public class SampleSearchController extends BaseFormController {
	private SampleManager sampleManager = null;
	private SamplerManager samplerManager = null;
	private UserManager userManager = null;
	
	public SampleSearchController() {
		setCommandClass(SampleSearch.class);
		setCommandName("search");
	}
	
	public void setSampleManager(SampleManager sampleManager) {
		this.sampleManager = sampleManager;
	}
	public void setSamplerManager(SamplerManager samplerManager) {
		this.samplerManager = samplerManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	//================= formBackingObject ===============================================
	protected Object formBackingObject(HttpServletRequest request) throws Exception {		
		log.debug("entering \"formBackingObject\" method in SampleSearchController...");
		SampleSearch search = new SampleSearch();
		return search;
	}
	
	//======================= referenceData =============================================
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
		log.debug("entering \"referenceData\" method in SampleSearchController...");
		Map<String, Object> referenceData = new HashMap();
		
		//========= sampler list ==========
		List<String> samplerIdList = new ArrayList<String>();
		User remoteUser = userManager.getUserByUsername(request.getRemoteUser());
		List<String> samplerList = sampleManager.getMySamplerIdList(remoteUser);
		if (samplerList.size() != 0) {
			samplerIdList.add("ALL");
			samplerIdList.addAll(samplerList);
		} else {
			saveError(request, "No sampler available");
		}
		List<Sample> sampleList = sampleManager.findSamplesByLab(remoteUser);
		//======================================
		referenceData.put("samplerIdList", samplerIdList);
		referenceData.put("sampleList", sampleList);
		return referenceData;
	}
    
    //====================== onSubmit ===================================================
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors) throws Exception {
		log.debug("entering \"onSubmit\" method");
		
		String success = getSuccessView();
		
		User remoteUser = userManager.getUserByUsername(request.getRemoteUser());
		SampleSearch search = (SampleSearch)command;
		String tag = search.getSamplerId();
		String from = search.getFrom().trim();
		String to = search.getTo().trim();
		// Initialize search date range
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
		if (tag.equals("ALL")) {
			sampleList = sampleManager.findSamplesByLabAndDateRange(remoteUser, fromDate, toDate);
		} else {
			sampleList = sampleManager.findSamplesByLabAndSamplerAndDateRange( 
					remoteUser, tag, fromDate, toDate);
		}
		//=============================================================
		//========= sampler list ==========
		List<String> samplerIdList = new ArrayList<String>();
		List<String> samplerList = sampleManager.getMySamplerIdList(remoteUser);
		if (samplerList.size() != 0) {
			samplerIdList.add("ALL");
			samplerIdList.addAll(samplerList);
		} else {
			saveError(request, "No sampler available");
		}
		//=============== Add Objects =======================
		ModelAndView mv = new ModelAndView("laboratory/samplesearch", getCommandName(), command);
		mv.addObject("sampleList", sampleList);
		mv.addObject("samplerIdList", samplerIdList);
		mv.addObject("samplerId", tag);
		mv.addObject("firstTime", "nup");
		return mv;
	}
}
