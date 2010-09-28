package com.tiffany.service.impl;

import java.util.List;

import javax.jws.WebService;

import com.tiffany.dao.ParameterThresholdsDao;
import com.tiffany.model.ParameterThresholds;
import com.tiffany.service.ParameterThresholdsManager;

@WebService(serviceName = "ParameterThresholdsService", endpointInterface = "com.tiffany.service.ParameterThresholdsManager")
public class ParameterThresholdsManagerImpl extends GenericManagerImpl<ParameterThresholds, Long> implements ParameterThresholdsManager {

	ParameterThresholdsDao parameterThresholdsDao;
		
	public ParameterThresholdsManagerImpl(ParameterThresholdsDao parameterThresholdsDao) {
		super(parameterThresholdsDao);
		this.parameterThresholdsDao = parameterThresholdsDao;
	}
		
	public ParameterThresholds findByWaterBodyAndId(String waterBody, Long parameter_id) {
		return parameterThresholdsDao.findByWaterBodyAndId(waterBody, parameter_id);
	}
	public List<ParameterThresholds> findByWaterBody(String waterBody) {
		return parameterThresholdsDao.findByWaterBody(waterBody);
	}

}
