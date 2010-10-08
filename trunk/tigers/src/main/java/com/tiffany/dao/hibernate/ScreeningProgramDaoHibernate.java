package com.tiffany.dao.hibernate;

import com.tiffany.model.ScreeningProgram;
import com.tiffany.dao.ScreeningProgramDao;

/**
 * Author: Jane
 */
public class ScreeningProgramDaoHibernate extends GenericDaoHibernate<ScreeningProgram, Long> implements
        ScreeningProgramDao {

    public ScreeningProgramDaoHibernate() {
        super(ScreeningProgram.class);
    }
}
