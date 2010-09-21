package com.tiffany.dao.hibernate;

import java.util.List;

import com.tiffany.dao.ScreeningFrequencyDao;
import com.tiffany.model.ScreeningFrequency;
import com.tiffany.model.Sampler;

public class ScreeningFrequencyDaoHibernate extends GenericDaoHibernate<ScreeningFrequency, Long>
		implements ScreeningFrequencyDao {

	public ScreeningFrequencyDaoHibernate() {
		super(ScreeningFrequency.class);
	}

	public List<ScreeningFrequency> findBySampler(Long id) {
		Sampler s = new Sampler();
        s.setId(id);

		List screeningFrequency = getHibernateTemplate().find("from ScreeningFrequency where sampler=? order by frequency",s);
		
		if(screeningFrequency.isEmpty()) {
			return null;
		} else {
			return screeningFrequency;
		}
	}
}