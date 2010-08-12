package com.tiffany.dao.hibernate;

import com.tiffany.dao.ContractorDao;
import com.tiffany.model.Contractor;

public class ContractorDaoHibernate extends GenericDaoHibernate<Contractor, String> implements
		ContractorDao {

	public ContractorDaoHibernate() {
		super(Contractor.class);
	}
}
