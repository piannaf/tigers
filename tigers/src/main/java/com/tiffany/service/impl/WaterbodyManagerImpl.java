package com.tiffany.service.impl;

import java.util.List;

import javax.jws.WebService;

import com.tiffany.dao.GenericDao;
import com.tiffany.dao.WaterbodyDao;
import com.tiffany.model.Waterbody;
import com.tiffany.service.WaterbodyManager;

@WebService(serviceName = "WaterbodyService", endpointInterface = "com.tiffany.service.WaterbodyManager")
public class WaterbodyManagerImpl extends GenericManagerImpl<Waterbody, Long> implements
		WaterbodyManager {
	
	WaterbodyDao waterbodyDao;

	public WaterbodyManagerImpl(WaterbodyDao waterbodyDao) {
		super(waterbodyDao);
		this.waterbodyDao = waterbodyDao;
	}

	public List<Waterbody> findLikeName(String search) {
		return waterbodyDao.findLikeName(search);
	}

	public String getWaterBodyType(String waterbody) {
		List<Waterbody> waterbodyList = waterbodyDao.findByName(waterbody);
		if (waterbodyList.size() == 0) return null;
		return Character.toString(waterbodyList.get(0).getType());
	}
	
	public List<Waterbody> getAll() {
		return waterbodyDao.getAll();
	}

}
