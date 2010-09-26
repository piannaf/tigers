package com.tiffany.dao.hibernate;

import java.util.*;

import com.tiffany.dao.SamplerDao;
import com.tiffany.model.Sample;
import com.tiffany.model.Sampler;
import com.tiffany.model.User;

public class SamplerDaoHibernate extends GenericDaoHibernate<Sampler, Long> implements
		SamplerDao {

	public SamplerDaoHibernate() {
        super(Sampler.class);
	}

	public List<Sampler> findByWaterBody(String waterbody) {

		List samplers = getHibernateTemplate().find("from Sampler where lower(waterbody.name)=? order by tag",waterbody.toLowerCase());

		if(samplers.isEmpty()) {
			return null;
		} else {
			return samplers;
		}
	}

	public Sampler findOneByTag(String tag) {

		List<Sampler> samplers = getHibernateTemplate().find("from Sampler where tag=?",tag);

		if(samplers.isEmpty()) {
			return null;
		} else {
			return samplers.get(0);
		}
	}

	public Sampler getByTag(String tag) {
		List samplers = getHibernateTemplate().find("from Sampler where tag=?",tag);

		if(samplers.isEmpty()) {
			return null;
		} else {
			return (Sampler)samplers.get(0);
		}
	}

	public List<Sampler> findByLaboratory(User laboratory) {
		return getHibernateTemplate().find("from Sampler where laboratory=?", laboratory);
	}

	public List<Sampler> findByContractor(User contractor) {
		return getHibernateTemplate().find("from Sampler where contractor=?", contractor);
	}
	
	public List<Sampler> findByTag(String tag) {
		return getHibernateTemplate().find("from Sampler where tag=?", tag);
	}
	
	public List<Sampler> getAllOrderedByTag() {

		List samplers = getHibernateTemplate().find("from Sampler order by tag");

		if(samplers.isEmpty()) {
			return null;
		} else {
			return samplers;
		}
	}

}
