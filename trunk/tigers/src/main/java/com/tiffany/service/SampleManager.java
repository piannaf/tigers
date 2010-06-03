package com.tiffany.service;

import java.util.*;

import com.tiffany.service.GenericManager;
import com.tiffany.model.Sample;
import com.tiffany.model.LabelValue;

import javax.jws.WebService;

@WebService
public interface SampleManager extends GenericManager<Sample, Long> {
	List<Sample> findSampleByDateRange(Date start, Date end);
	List<Sample> findSamplerId();
}


