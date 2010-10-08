package com.tiffany.dao.hibernate;

import com.tiffany.model.ScreeningProgramSamplers;
import com.tiffany.model.ScreeningProgramSamplersId;
import com.tiffany.dao.ScreeningProgramSamplersDao;

import java.util.List;

/**
 * Author: Jane
 */
public class ScreeningProgramSamplersDaoHibernate extends GenericDaoHibernate<ScreeningProgramSamplers, ScreeningProgramSamplersId> implements
        ScreeningProgramSamplersDao {

    public ScreeningProgramSamplersDaoHibernate() {
        super(ScreeningProgramSamplers.class);
    }

    public List<ScreeningProgramSamplers> getSamplers(Long id) {
		return getHibernateTemplate().
                find("from ScreeningProgramSamplers s where s.id.id =? order by s.id.sampler ", id);
	}
}
