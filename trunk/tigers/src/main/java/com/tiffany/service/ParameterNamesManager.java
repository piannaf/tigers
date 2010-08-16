package com.tiffany.service;

import java.util.*;

import com.tiffany.service.GenericManager;
import com.tiffany.model.ParameterNames;
//import com.tiffany.model.LabelValue;

import javax.jws.WebService;

@WebService
public interface ParameterNamesManager extends GenericManager<ParameterNames, Long> {
	List<ParameterNames> findByType(char type);
	ParameterNames getId(Long id);
	List<ParameterNames> getAll();
}


