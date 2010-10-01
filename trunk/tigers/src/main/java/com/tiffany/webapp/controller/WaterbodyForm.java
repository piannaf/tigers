package com.tiffany.webapp.controller;

import com.tiffany.model.Waterbody;
import com.tiffany.model.ParameterThresholds;

import java.util.*;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

/**
 * Author: Jane
 */
public class WaterbodyForm {

    private Waterbody waterbody;
    private List<ParameterThresholds> parameterThresholds;

    public WaterbodyForm() {
        parameterThresholds = LazyList.decorate(new ArrayList(),
                    FactoryUtils.instantiateFactory(ParameterThresholds.class));
    }

    public Waterbody getWaterbody() {
        return waterbody;
    }

    public void setWaterbody(Waterbody waterbody) {
        this.waterbody = waterbody;
    }

    public List<ParameterThresholds> getParameterThresholds() {
        return parameterThresholds;
    }

    public void setParameterThresholds(List<ParameterThresholds> parameterThresholds) {
        this.parameterThresholds = parameterThresholds;
    }
}
