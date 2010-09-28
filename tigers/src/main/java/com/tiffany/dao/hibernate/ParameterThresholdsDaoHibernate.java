package com.tiffany.dao.hibernate;

import java.util.List;

import com.tiffany.dao.ParameterThresholdsDao;
import com.tiffany.model.ParameterThresholds;

public class ParameterThresholdsDaoHibernate extends GenericDaoHibernate<ParameterThresholds, Long> implements
		ParameterThresholdsDao {

	public ParameterThresholdsDaoHibernate() {
        super(ParameterThresholds.class);
	}

	public ParameterThresholds findByWaterBodyAndId(String waterBody, Long parameter) {
		Object[] args = {waterBody, parameter};
		List pt = getHibernateTemplate().find("from ParameterThresholds where waterbody.name=? and parameter.id=?", args);
		
		if(pt.isEmpty()) {
			return null;
		} else {
			return (ParameterThresholds)pt.get(0);
		}
	}
	
	public List<ParameterThresholds> findByWaterBody(String waterBody) {
		Object[] args = {waterBody};
		List<ParameterThresholds> ret = getHibernateTemplate().find("from ParameterThresholds where waterbody.name=?", args);
		
		if(ret.isEmpty()) {
			return null;
		} else {
			return ret;
		}
	}

}
