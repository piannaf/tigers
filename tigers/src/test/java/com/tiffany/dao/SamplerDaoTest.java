package com.tiffany.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tiffany.model.Sampler;
import com.tiffany.model.Waterbody;

public class SamplerDaoTest 
	 extends BaseDaoTestCase{ 
		
		  private SamplerDao samplerDao;

		  /**
		   * Spring will automatically inject WaterbodyDao object on startup
		   * @param WaterbodyDao
		   */
		  public void setSamplerDao(SamplerDao samplerDao) {
		      this.samplerDao = samplerDao;
		  }

		  /**
		   * Test the save method
		   *
		   */
		  public void testSave(){
		     	    
			  Sampler s = new Sampler();
		      s.setTag("GW999");
		      s.setLatitude(new BigDecimal(1.5));
		      s.setLongitude(new BigDecimal(1.5));
		      s.setComp_screening_freq("weekly");
		      s.setPurpose("test");
		      
		/*      Waterbody w = new Waterbody();
		      w.setName("Atlantic");
		      w.setType('S');
		      
		      s.setWaterbody(w);*/
		    
		      samplerDao.save(s);
		      
		      s = samplerDao.findOneByTag("GW999");
		      assertNotNull(s);
		  }
		  
		  public void testgetAll() throws Exception {
		    	List wbs = samplerDao.getAll();
		    	log.debug(wbs);
		    	assertNotNull(wbs);
		    	 
		}

}
