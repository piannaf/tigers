package com.tiffany.dao;

import java.util.List;

import com.tiffany.model.Waterbody;

public interface WaterbodyDao extends GenericDao<Waterbody, String> {
	
	public List<Waterbody> findLikeName(String search);
	public List<Waterbody> findByName(String name);
	public List<Waterbody> getAll();
}
