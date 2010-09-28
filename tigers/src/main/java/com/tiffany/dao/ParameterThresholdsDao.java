package com.tiffany.dao;

import java.util.List;

import com.tiffany.model.ParameterThresholds;

public interface ParameterThresholdsDao extends GenericDao<ParameterThresholds, Long> {
    public ParameterThresholds findByWaterBodyAndId(String waterBody, Long parameter_id);
	public List<ParameterThresholds> findByWaterBody(String waterBody);
}
