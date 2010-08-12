package com.tiffany.dao;

import java.util.List;
import java.util.Set;

import com.tiffany.model.ScreeningFrequency;

public class ScreeningFrequencyTest extends BaseDaoTestCase{ 
	
	  private ScreeningFrequencyDao screeningFrequencyDao;

	  /**
	   * Spring will automatically inject ScreeningFrequencyDao object on startup
	   * @param ScreeningFrequencyDao
	   */
	  public void setScreeningFrequencyDao(ScreeningFrequencyDao screeningFrequencyDao) {
	      this.screeningFrequencyDao = screeningFrequencyDao;
	  }

	    public void testExists() throws Exception {
	        boolean b = screeningFrequencyDao.exists(2L);
	        assertTrue(b);
	    }
	    
	    public void testNotExists() throws Exception {
	        boolean b = screeningFrequencyDao.exists(10L);
	        assertFalse(b);
	    }
	    
	    public void testgetAll() throws Exception {
	    	List sf = screeningFrequencyDao.getAll();
	    	assertNotNull(sf);
	    	assertTrue(sf.size() == 2);
	}
	    public void testfindbytype() throws Exception {
	    	List sfs = screeningFrequencyDao.findBySampler("GW567");       
	    	assertTrue(sfs.size() == 1);
	    	sfs = screeningFrequencyDao.findBySampler("SW123");
	    	assertTrue(sfs.size() == 1);
	    }
	    public void testgetItems() throws Exception {
	    	ScreeningFrequency sf = screeningFrequencyDao.get(1L);
	    	Set pn = sf.getParameterNames();
	    	assertNotNull(pn);
	    	assertTrue(pn.size() == 3);
	}

}
