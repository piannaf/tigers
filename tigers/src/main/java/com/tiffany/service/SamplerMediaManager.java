package com.tiffany.service;

import java.util.*;

import com.tiffany.service.GenericManager;
import com.tiffany.model.SamplerMedia;
import com.tiffany.model.LabelValue;

import javax.jws.WebService;

@WebService
public interface SamplerMediaManager extends GenericManager<SamplerMedia, Long> {
	List<SamplerMedia> findByTag(String tag);
}


