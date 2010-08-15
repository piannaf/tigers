package com.tiffany.dao.hibernate;

import java.util.*;

import com.tiffany.dao.SamplerDao;
import com.tiffany.model.Sample;
import com.tiffany.model.Sampler;

public class SamplerDaoHibernate extends GenericDaoHibernate<Sampler, String> implements
		SamplerDao {

	public SamplerDaoHibernate() {
        super(Sampler.class);
	}

	public List<Sampler> findByWaterBody(String waterbody) {
		
		List samplers = getHibernateTemplate().find("from Sampler where waterbody=?",waterbody);
		
		if(samplers.isEmpty()) {
			return null;
		} else {
			return samplers;
		}
	}
	
	public List<Sampler> findByLaboratory(String laboratory) {
		return getHibernateTemplate().find("from Sampler where laboratory=?", laboratory);
	}
	
	public List<Sampler> findByTag(String tag) {
		return getHibernateTemplate().find("from Sampler where tag=?", tag);
	}

}
