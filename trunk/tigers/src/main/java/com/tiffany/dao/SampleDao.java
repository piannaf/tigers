package com.tiffany.dao;

import com.tiffany.model.Sample;
import java.util.*;

public interface SampleDao extends GenericDao<Sample, Long>{
    /**
     * Gets Course information based on coursecode
     * @param coursecode the coursecode
     * @return populated course object
     */
    public List<Sample> findByDateRange(Date start, Date end);
    public List<Sample> findSamplerId();
}
