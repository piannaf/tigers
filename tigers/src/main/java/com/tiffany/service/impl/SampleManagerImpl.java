package com.tiffany.service.impl;

import java.io.Serializable;
import java.util.*;

import javax.jws.WebService;

import com.tiffany.dao.SampleDao;
import com.tiffany.model.Sample;
import com.tiffany.model.LabelValue;
import com.tiffany.model.User;
import com.tiffany.service.SampleManager;

@WebService(serviceName = "SampleService", endpointInterface = "com.tiffany.service.SampleManager")
public class SampleManagerImpl extends GenericManagerImpl<Sample, Long> 
		implements SampleManager{
	SampleDao sampleDao;
	
	public SampleManagerImpl(SampleDao sampleDao) {
		super(sampleDao);
		this.sampleDao = sampleDao;
	}
	
	public List<Sample> findSampleByDateRange(Date start, Date end) {
		List<Sample> samples = sampleDao.findByDateRange(start, end);
//		List<LabelValue> list = new ArrayList<LabelValue>();
//		
//        for (Sample sample : samples) {
//            list.add(new LabelValue(sample.getDate_taken().toString(), 
//            		sample.getDate_taken().toString()));
//        }
//        
		return samples;
	}
	
	public List<Sample> findSamplerId() {
		List<Sample> samples = sampleDao.findSamplerId();
		return samples;
	}
	
	public List<Sample> findSamplesByTagAndDateRange(String tag, Date from, Date to) {
		return sampleDao.findSamplesByTagAndDateRange(tag, from, to);
	}
	
	public List<Sample> findSamplesByTag(String tag) {
		return sampleDao.findSamplesByTag(tag);
	}

	public List<Sample> findSamplesByLab(User lab) {
	    return sampleDao.findSamplesByLab(lab);
	}

	public List<Sample> findSamplesByLabAndDateRange(User lab, Date from,
		Date to) {
		return sampleDao.findSamplesByLabAndDateRange(lab, from, to);
	}
	
	public List<Sample> findSamplesByLabAndSamplerAndDateRange(User lab, String samplerId, 
			Date from, Date to) {
			return sampleDao.findSamplesByLabAndSamperIdAndDateRange(lab, samplerId, from, to);
	}
	
	public List<String> getMySamplerIdList(User lab) {
		List<Sample> sampleList = findSamplesByLab(lab);
		Set<String> samplerIdList = new HashSet<String>();
		for (Sample sample : sampleList) {
			samplerIdList.add(sample.getSampler().getTag());
		}
		List<String> ids = new ArrayList<String>();
		ids.addAll(samplerIdList);
		Collections.sort(ids);
		return ids;
	}
}
