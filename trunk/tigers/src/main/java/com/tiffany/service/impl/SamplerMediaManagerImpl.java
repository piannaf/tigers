package com.tiffany.service.impl;

import java.io.Serializable;
import java.util.*;

import javax.jws.WebService;

import com.tiffany.dao.SamplerMediaDao;
import com.tiffany.model.SamplerMedia;
import com.tiffany.model.LabelValue;
import com.tiffany.service.SamplerMediaManager;

@WebService(serviceName = "SamplerMediaService", endpointInterface = "com.tiffany.service.SamplerMediaManager")
public class SamplerMediaManagerImpl extends GenericManagerImpl<SamplerMedia, Long> 
		implements SamplerMediaManager{
	SamplerMediaDao samplerMediaDao;
	
	public SamplerMediaManagerImpl(SamplerMediaDao samplerMediaDao) {
		super(samplerMediaDao);
		this.samplerMediaDao = samplerMediaDao;
	}
	
	public List<SamplerMedia> findByTag(String tag) {
		return samplerMediaDao.findByTag(tag);
	}
}
