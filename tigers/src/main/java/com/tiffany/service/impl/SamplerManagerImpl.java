package com.tiffany.service.impl;

import java.util.List;

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

}
