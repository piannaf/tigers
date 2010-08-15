package com.tiffany.dao.hibernate;

import java.util.List;

import com.tiffany.dao.WaterbodyDao;
import com.tiffany.model.Waterbody;

public class WaterbodyDaoHibernate extends GenericDaoHibernate<Waterbody, String> implements
		WaterbodyDao {

	public WaterbodyDaoHibernate() {
		super(Waterbody.class);
	}

	public List<Waterbody> findLikeName(String search) {
		
		List waterbodies = getHibernateTemplate().find("from Waterbody where lower(name) like ?",(search + "%").toLowerCase());
		
		if(waterbodies.isEmpty()) {
			return null;
		} else {
			return waterbodies;
		}
	}
	
	public List<Waterbody> findByName(String name) {
		return getHibernateTemplate().find("from Waterbody where name=?", name);
	}
	
	public List<Waterbody> getAll() {
		List waterbodies = getHibernateTemplate().find("from Waterbody order by name");
		if(waterbodies.isEmpty()) {
			return null;
		} else {
			return waterbodies;
		}
	}

}
