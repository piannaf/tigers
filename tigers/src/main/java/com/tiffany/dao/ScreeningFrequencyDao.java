package com.tiffany.dao;

import java.util.List;

import com.tiffany.model.ScreeningFrequency;

public interface ScreeningFrequencyDao extends GenericDao<ScreeningFrequency, Long> {
	
	public List<ScreeningFrequency> findBySampler(Long id);

}
