package com.tiffany.service;

import java.util.List;
import javax.jws.WebService;
import com.tiffany.model.Waterbody;

@WebService
public interface WaterbodyManager extends GenericManager<Waterbody, Long> {
	
	List<Waterbody> findLikeName(String search);
	String getWaterBodyType(String waterbody);
	List<Waterbody> getAll();
}
