package com.tiffany.service;

import java.util.List;
import javax.jws.WebService;
import com.tiffany.model.Sampler;

@WebService
public interface SamplerManager extends GenericManager<Sampler, Long> {
	
	List<Sampler> findByWaterBody(String waterbody);
	Sampler findByTag(String tag);
	Sampler getByTag(String tag);

	List<String> getTagListForLaboratory(String laboratory);
	String getWaterBodyNameByTag(String tag);
	String getContractorByTag(String tag);
}
