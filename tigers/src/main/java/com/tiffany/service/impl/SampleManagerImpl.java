package com.tiffany.service.impl;

import java.io.Serializable;
import java.util.*;

import javax.jws.WebService;

import com.tiffany.dao.SampleDao;
import com.tiffany.model.Sample;
import com.tiffany.model.LabelValue;
import com.tiffany.service.SampleManager;

@WebService(serviceName = "SampleService", endpointInterface = "com.tiffany.service.SampleManager")
public class SampleManagerImpl extends GenericManagerImpl<Sample, Long> 
		implements SampleManager{
	SampleDao sampleDao;
	
	public SampleManagerImpl(SampleDao sampleDao) {
		super(sampleDao);
		this.sampleDao = sampleDao;
	}
	
	public List<LabelValue> findSampleByDateRange(Date start, Date end) {
		List<Sample> samples = sampleDao.findByDateRange(start, end);
		List<LabelValue> list = new ArrayList<LabelValue>();
		
        for (Sample sample : samples) {
            list.add(new LabelValue(sample.getDate_taken().toString(), 
            		sample.getDate_taken().toString()));
        }
        
		return list;
	}
	
	public List<Sample> findSamplerId() {
		List<Sample> samples = sampleDao.findSamplerId();
		return samples;
	}
}
