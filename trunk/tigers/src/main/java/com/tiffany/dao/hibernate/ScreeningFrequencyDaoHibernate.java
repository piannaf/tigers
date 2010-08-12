package com.tiffany.dao.hibernate;

import java.util.List;
import java.util.Map;

import com.tiffany.dao.ScreeningFrequencyDao;
import com.tiffany.model.ParameterNames;
import com.tiffany.model.ScreeningFrequency;

public class ScreeningFrequencyDaoHibernate extends GenericDaoHibernate<ScreeningFrequency, Long>
		implements ScreeningFrequencyDao {

	public ScreeningFrequencyDaoHibernate() {
		super(ScreeningFrequency.class);
	}

	public List<ScreeningFrequency> findBySampler(String tag) {
		
		List screeningFrequency = getHibernateTemplate().find("from ScreeningFrequency where tag=?",tag);
		
		if(screeningFrequency.isEmpty()) {
			return null;
		} else {
			return screeningFrequency;
		}
	}

}
