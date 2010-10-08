package com.tiffany.service.impl;

import com.tiffany.service.ScreeningProgramManager;
import com.tiffany.model.ScreeningProgram;
import com.tiffany.dao.ScreeningProgramDao;

import javax.jws.WebService;

/**
 * Author: Jane
 */

@WebService(serviceName = "ScreeningProgramService", endpointInterface = "com.tiffany.service.ScreeningProgramManager")
public class ScreeningProgramManagerImpl  extends GenericManagerImpl<ScreeningProgram, Long> implements
		ScreeningProgramManager {

	ScreeningProgramDao screeningProgramDao;

	public ScreeningProgramManagerImpl(ScreeningProgramDao screeningProgramDao) {
		super(screeningProgramDao);
		this.screeningProgramDao = screeningProgramDao;
	}
}
