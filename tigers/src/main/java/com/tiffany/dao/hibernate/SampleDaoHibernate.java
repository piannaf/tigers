package com.tiffany.dao.hibernate;

import java.util.Date;
import java.util.List;

import com.tiffany.dao.SampleDao;
import com.tiffany.model.Sample;

public class SampleDaoHibernate extends GenericDaoHibernate<Sample, Long> implements SampleDao{
    /**
     * Constructor to create a Generics-based version using Sample as the entity
     */
    public SampleDaoHibernate() {
        super(Sample.class);
    }

	public List<Sample> findByDateRange(Date start, Date end) {
		if(start.compareTo(end) > 0) {
			return null;
		}
		
		Object[] dates = {start, end};
		List samples = getHibernateTemplate().find(
				"from Course where date_taken between ? and ?", dates);
		if(samples.isEmpty()) {
			return null;
		} else {
			return samples;
		}
	}

}
