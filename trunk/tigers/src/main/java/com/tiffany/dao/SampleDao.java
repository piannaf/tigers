package com.tiffany.dao;

import com.tiffany.model.Sample;
import com.tiffany.model.User;

import java.util.*;

public interface SampleDao extends GenericDao<Sample, Long>{
    /**
     * Gets Course information based on coursecode
     * @param coursecode the coursecode
     * @return populated course object
     */
    public List<Sample> findByDateRange(Date start, Date end);
    public List<Sample> findSamplesByTagAndDateRange(String tag, Date start, Date end);
    public List<Sample> findSamplesByTag(String tag);
    public List<Sample> findSamplerId();
    public List<Sample> findSamplesByLab(User lab);
    public List<Sample> findSamplesByLabAndDateRange(User lab, Date from,
	    Date to);
}
