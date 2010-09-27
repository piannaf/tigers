package com.tiffany.dao.hibernate;

import java.util.List;

import com.tiffany.dao.SamplerMediaDao;
import com.tiffany.model.SamplerMedia;

public class SamplerMediaDaoHibernate extends GenericDaoHibernate<SamplerMedia, Long> implements SamplerMediaDao {
    public SamplerMediaDaoHibernate() {
        super(SamplerMedia.class);
    }

	
	public List<SamplerMedia> findByTag(String tag) {
		Object[] tagParam = {tag};
		return getHibernateTemplate().find("from SamplerMedia where tag=?", tagParam);
	}
	public List<SamplerMedia> getId(Long id) {
		Object[] param = {id};
		return getHibernateTemplate().find("from SamplerMedia where id=?", param);
	}

}
