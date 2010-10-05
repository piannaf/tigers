package com.tiffany.service;

import java.util.*;

import com.tiffany.service.GenericManager;
import com.tiffany.model.Sample;
import com.tiffany.model.LabelValue;
import com.tiffany.model.User;

import javax.jws.WebService;

@WebService
public interface SampleManager extends GenericManager<Sample, Long> {
	List<Sample> findSampleByDateRange(Date start, Date end);
	List<Sample> findSamplerId();
	List<Sample> findSamplesByTagAndDateRange(String tag, Date from, Date to);
	List<Sample> findSamplesByTag(String tag);
	List<Sample> findSamplesByLab(User lab);
	List<Sample> findSamplesByLabAndDateRange(User lab, Date from, Date to);
	List<Sample> findSamplesByLabAndSamplerAndDateRange(User lab, String samplerId, Date from, Date to);
	List<String> getMySamplerIdList(User lab);
}


