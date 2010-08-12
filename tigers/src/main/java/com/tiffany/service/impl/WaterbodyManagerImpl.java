package com.tiffany.service.impl;

import java.util.List;

import javax.jws.WebService;

import com.tiffany.dao.GenericDao;
import com.tiffany.dao.WaterbodyDao;
import com.tiffany.model.Waterbody;
import com.tiffany.service.WaterbodyManager;

@WebService(serviceName = "WaterbodyService", endpointInterface = "com.tiffany.service.WaterbodyManager")
public class WaterbodyManagerImpl extends GenericManagerImpl<Waterbody, String> implements
		WaterbodyManager {
	
	WaterbodyDao waterbodyDao;

	public WaterbodyManagerImpl(WaterbodyDao waterbodyDao) {
		super(waterbodyDao);
		this.waterbodyDao = waterbodyDao;
	}

	public List<Waterbody> findLikeName(String search) {
		return waterbodyDao.findLikeName(search);
	}

	public List<Waterbody> getAll() {
		return waterbodyDao.getAll();
	}

}
