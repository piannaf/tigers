package com.tiffany.service.impl;

import java.io.Serializable;
import java.util.*;

import javax.jws.WebService;

import com.tiffany.dao.ParameterNamesDao;
import com.tiffany.model.ParameterNames;
//import com.tiffany.model.LabelValue;
import com.tiffany.service.ParameterNamesManager;

@WebService(serviceName = "ParameterNamesService", endpointInterface = "com.tiffany.service.ParameterNamesManager")
public class ParameterNamesManagerImpl extends GenericManagerImpl<ParameterNames, Long> 
		implements ParameterNamesManager{
	ParameterNamesDao parameterNamesDao;
	
	public ParameterNamesManagerImpl(ParameterNamesDao parameterNamesDao) {
		super(parameterNamesDao);
		this.parameterNamesDao = parameterNamesDao;
	}
	
	public List<ParameterNames> findByType(char type) {
		return parameterNamesDao.findByType(type);
	}
	
	public ParameterNames getId(Long id) {
		return parameterNamesDao.getId(id);
	}
	
	public List<ParameterNames> getAll() {
		return parameterNamesDao.getAll();
	}
}
