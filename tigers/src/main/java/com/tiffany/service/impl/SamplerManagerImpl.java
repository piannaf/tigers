package com.tiffany.service.impl;

import java.util.*;

import javax.jws.WebService;

import com.tiffany.dao.SamplerDao;
import com.tiffany.model.Sampler;
import com.tiffany.service.SamplerManager;

@WebService(serviceName = "SamplerService", endpointInterface = "com.tiffany.service.SamplerManager")
public class SamplerManagerImpl extends GenericManagerImpl<Sampler, String> implements

SamplerManager {

	SamplerDao samplerDao;
		
	public SamplerManagerImpl(SamplerDao samplerDao) {
		super(samplerDao);
		this.samplerDao = samplerDao;
	}		
		
	public List<Sampler> findByWaterBody(String tag) {
		return samplerDao.findByWaterBody(tag);
	}
	
	public List<String> getTagListForLaboratory(String laboratory) {
		List<String> tagList = new ArrayList<String>();
		Set<String> tagSet = new HashSet<String>();
		List<Sampler> samplerList = samplerDao.findByLaboratory(laboratory);
		Iterator it = samplerList.iterator();
		while (it.hasNext()) {
			Sampler sampler = (Sampler)it.next();
			tagSet.add(sampler.getTag());
		}
		tagList.addAll(tagSet);
		return tagList;
	}
	
	public String getWaterBodyNameByTag(String tag) {
		List<Sampler> samplerList = samplerDao.findByTag(tag);
		if (samplerList.size() == 0) return null;
		return samplerList.get(0).getWaterbody();
	}
	
	public String getContractorByTag(String tag) {
		List<Sampler> samplerList = samplerDao.findByTag(tag);
		if (samplerList.size() == 0) return null;
		return samplerList.get(0).getContractor();
	}
}
