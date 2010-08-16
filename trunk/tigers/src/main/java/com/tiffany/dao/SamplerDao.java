package com.tiffany.dao;

import java.util.List;

import com.tiffany.model.Sample;
import com.tiffany.model.Sampler;

public interface SamplerDao extends GenericDao<Sampler, String> {
   
    public List<Sampler> findByWaterBody(String waterBody);
    public Sampler getByTag(String tag);
    public List<Sampler> findByLaboratory(String laboratory);
    public List<Sampler> findByTag(String tag);
}
