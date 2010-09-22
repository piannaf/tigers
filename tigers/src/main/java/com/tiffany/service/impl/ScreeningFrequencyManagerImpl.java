package com.tiffany.service.impl;

import com.tiffany.model.ScreeningFrequency;
import com.tiffany.service.ScreeningFrequencyManager;
import com.tiffany.dao.GenericDao;
import com.tiffany.dao.ScreeningFrequencyDao;

import javax.jws.WebService;
import java.util.List;

/**
 * Author: Jane
 */
@WebService(serviceName = "ScreeningFrequencyService", endpointInterface = "com.tiffany.service.ScreeningFrequencyManager")
public class ScreeningFrequencyManagerImpl extends GenericManagerImpl<ScreeningFrequency, Long> implements
        ScreeningFrequencyManager {

    ScreeningFrequencyDao screeningFrequencyDao;

    public ScreeningFrequencyManagerImpl(ScreeningFrequencyDao screeningFrequencyDao) {
		super(screeningFrequencyDao);
		this.screeningFrequencyDao = screeningFrequencyDao;
    }

    public List<ScreeningFrequency> findBySampler(Long id) {
        return screeningFrequencyDao.findBySampler(id);
    }
}
