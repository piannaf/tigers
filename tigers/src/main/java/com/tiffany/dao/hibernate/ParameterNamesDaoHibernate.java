package com.tiffany.dao.hibernate;

import java.util.List;
import java.util.Map;

import com.tiffany.dao.ParameterNamesDao;
import com.tiffany.model.ParameterNames;
import com.tiffany.model.Waterbody;

public class ParameterNamesDaoHibernate extends GenericDaoHibernate<ParameterNames, Long> implements
		ParameterNamesDao {

	public ParameterNamesDaoHibernate() {
		super(ParameterNames.class);
	}

	public List<ParameterNames> findByType(char type) {
		log.debug(type);
		List parameterNames = getHibernateTemplate().find("from ParameterNames where type=?",type);
		
		if(parameterNames.isEmpty()) {
			return null;
		} else {
			return parameterNames;
		}
	}
	
	public ParameterNames getId(Long id) {
		List parameterNames = getHibernateTemplate().find("from ParameterNames where parameter_id=?", id);
		
		if(parameterNames.isEmpty()) {
			return null;
		} else {
			return (ParameterNames)parameterNames.get(0);
		}
	}
	
	public List<ParameterNames> getAll() {
		List parameterNames = getHibernateTemplate().find("from ParameterNames");
		
		if(parameterNames.isEmpty()) {
			return null;
		} else {
			return parameterNames;
		}
	}

}
