package com.tiffany.service.impl;

import com.tiffany.model.ScreeningProgramSamplers;
import com.tiffany.model.ScreeningProgramSamplersId;
import com.tiffany.service.ScreeningProgramSamplersManager;
import com.tiffany.dao.ScreeningProgramSamplersDao;

import javax.jws.WebService;
import java.util.List;

/**
 * Author: Jane
 */
@WebService(serviceName = "ScreeningProgramSamplersService", endpointInterface = "com.tiffany.service.ScreeningProgramSamplersManager")
public class ScreeningProgramSamplersManagerImpl  extends GenericManagerImpl<ScreeningProgramSamplers, ScreeningProgramSamplersId> implements
        ScreeningProgramSamplersManager {

	ScreeningProgramSamplersDao screeningProgramSamplersDao;

	public ScreeningProgramSamplersManagerImpl(ScreeningProgramSamplersDao screeningProgramSamplersDao) {
		super(screeningProgramSamplersDao);
		this.screeningProgramSamplersDao = screeningProgramSamplersDao;
	}
    public List<ScreeningProgramSamplers> getSamplers(Long id) {
        return screeningProgramSamplersDao.getSamplers(id);
    }
}
