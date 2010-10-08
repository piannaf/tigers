package com.tiffany.dao;

import com.tiffany.model.ScreeningProgramSamplersId;
import com.tiffany.model.ScreeningProgramSamplers;

import java.util.List;

/**
 * Author: Jane
 */
public interface ScreeningProgramSamplersDao extends GenericDao<ScreeningProgramSamplers, ScreeningProgramSamplersId> {

    public List<ScreeningProgramSamplers> getSamplers(Long id);

}
