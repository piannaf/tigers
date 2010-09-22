package com.tiffany.service;

import com.tiffany.model.ScreeningFrequency;

import javax.jws.WebService;
import java.util.List;

/**
 * Author: Jane
 */
@WebService
public interface ScreeningFrequencyManager extends GenericManager<ScreeningFrequency, Long> {

   public List<ScreeningFrequency> findBySampler(Long id);
}
