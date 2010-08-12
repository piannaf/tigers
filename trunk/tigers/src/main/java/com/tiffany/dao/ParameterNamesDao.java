package com.tiffany.dao;

import java.util.List;

import com.tiffany.model.ParameterNames;

public interface ParameterNamesDao extends GenericDao<ParameterNames, Long> {
	
	public List<ParameterNames> findByType(char type);

}
