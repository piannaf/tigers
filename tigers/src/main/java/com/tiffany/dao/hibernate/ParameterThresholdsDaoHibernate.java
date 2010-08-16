package com.tiffany.dao.hibernate;

import java.util.List;

import com.tiffany.dao.ParameterThresholdsDao;
import com.tiffany.model.ParameterThresholds;

public class ParameterThresholdsDaoHibernate extends GenericDaoHibernate<ParameterThresholds, Long> implements
		ParameterThresholdsDao {

	public ParameterThresholdsDaoHibernate() {
        super(ParameterThresholds.class);
	}

	public ParameterThresholds findByWaterBodyAndId(String waterBody, Long parameter_id) {
		Object[] args = {waterBody, parameter_id};
		List pt = getHibernateTemplate().find("from ParameterThresholds where name=? and parameter_id=?", args);
		
		if(pt.isEmpty()) {
			return null;
		} else {
			return (ParameterThresholds)pt.get(0);
		}
	}

}
