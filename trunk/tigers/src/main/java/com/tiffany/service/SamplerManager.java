package com.tiffany.service;

import java.util.List;
import com.tiffany.model.User;
import javax.jws.WebService;
import com.tiffany.model.Sampler;

@WebService
public interface SamplerManager extends GenericManager<Sampler, Long> {
	
	List<Sampler> findByWaterBody(String waterbody);
	Sampler findOneByTag(String tag);
	Sampler getByTag(String tag);

	List<String> getTagListForLaboratory(User laboratory);
	String getWaterBodyNameByTag(String tag);
	String getContractorByTag(String tag);
	List<Sampler> findByTag(String tag);
	User getContractorByTag2(String tag);
	String getWaterBodyTypeByTag(String tag);
	public List<Sampler> getAllOrderedByTag();
}
