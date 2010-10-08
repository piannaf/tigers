package com.tiffany.webapp.controller;

import com.tiffany.model.ParameterNames;

import java.util.*;

/**
 * Author: Jane
 * bodge to get round difficulty of having objects as composite keys
 */
public class ScreeningItem {
    private String tag;
    private List<ParameterNames> parameterNames = new ArrayList<ParameterNames>();

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<ParameterNames> getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(List<ParameterNames> parameterNames) {
        this.parameterNames = parameterNames;
    }
}
