package com.tiffany.service;

import java.util.List;
import javax.jws.WebService;
import com.tiffany.model.Waterbody;

@WebService
public interface WaterbodyManager extends GenericManager<Waterbody, String> {
	
	List<Waterbody> findLikeName(String search);

}
