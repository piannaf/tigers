package com.tiffany.service;

import com.tiffany.model.ScreeningProgramSamplers;
import com.tiffany.model.ScreeningProgramSamplersId;

import javax.jws.WebService;
import java.util.List;

/**
 * Author: Jane
 */
@WebService
public interface ScreeningProgramSamplersManager extends GenericManager<ScreeningProgramSamplers, ScreeningProgramSamplersId>{

    List<ScreeningProgramSamplers> getSamplers(Long id);

}
