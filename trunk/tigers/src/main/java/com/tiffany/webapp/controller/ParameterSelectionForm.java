package com.tiffany.webapp.controller;

import com.tiffany.model.ParameterNames;

import java.util.*;

/**
 * Author: Jane
 */
public class ParameterSelectionForm {

    private int pageNo;
    private Long screeningProgramId;
    private int noSamplers;
    private Long samplerid;
    private List<ParameterNames> parameterNames= new ArrayList<ParameterNames>();

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public Long getScreeningProgramId() {
        return screeningProgramId;
    }

    public void setScreeningProgramId(Long screeningProgramId) {
        this.screeningProgramId = screeningProgramId;
    }

    public Long getSamplerid() {
        return samplerid;
    }

    public void setSamplerid(Long samplerid) {
        this.samplerid = samplerid;
    }

    public List<ParameterNames> getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(List<ParameterNames> parameterNames) {
        this.parameterNames = parameterNames;
    }

    public int getNoSamplers() {
        return noSamplers;
    }

    public void setNoSamplers(int noSamplers) {
        this.noSamplers = noSamplers;
    }
}
