package com.tiffany.service;

import java.util.List;
import javax.jws.WebService;
import com.tiffany.model.ParameterThresholds;

@WebService
public interface ParameterThresholdsManager extends GenericManager<ParameterThresholds, Long> {
	
	ParameterThresholds findByWaterBodyAndId(String waterBody, Long parameter_id);

}
