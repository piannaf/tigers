package com.tiffany.dao;

import com.tiffany.model.SamplerMedia;
import java.util.*;

public interface SamplerMediaDao extends GenericDao<SamplerMedia, Long>{
    public List<SamplerMedia> findByTag(String tag);
    public List<SamplerMedia> getId(Long id);
}
