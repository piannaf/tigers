package com.tiffany.dao;

import java.util.List;

import com.tiffany.model.Sample;
import com.tiffany.model.Sampler;
import com.tiffany.model.User;

public interface SamplerDao extends GenericDao<Sampler, Long> {

    public List<Sampler> findByWaterBody(String waterBody);
    public Sampler findOneByTag(String tag);
    public Sampler getByTag(String tag);
    public List<Sampler> findByLaboratory(User laboratory);
    public List<Sampler> findByTag(String tag);
}
