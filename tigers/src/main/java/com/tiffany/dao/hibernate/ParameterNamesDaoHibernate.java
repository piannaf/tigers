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
		List parameterNames = getHibernateTemplate().find("from ParameterNames where type='B' or type=?",type);
		
		if(parameterNames.isEmpty()) {
			return null;
		} else {
			return parameterNames;
		}
	}
	
	public ParameterNames getId(Long id) {
		List parameterNames = getHibernateTemplate().find("from ParameterNames where id=?", id);
		
		if(parameterNames.isEmpty()) {
			return null;
		} else {
			return (ParameterNames)parameterNames.get(0);
		}
	}
	
	public List<ParameterNames> getAll() {
		List parameterNames = getHibernateTemplate().find("from ParameterNames order by parameter_id");
		
		if(parameterNames.isEmpty()) {
			return null;
		} else {
			return parameterNames;
		}
	}

}
