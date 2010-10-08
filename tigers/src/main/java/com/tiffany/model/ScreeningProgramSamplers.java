package com.tiffany.model;

import java.util.*;
import javax.persistence.*;

/**
 * Author: Jane
 */
@Entity
    @Table(name="screeningprogramsamplers"
    , uniqueConstraints = {  }
    )
public class ScreeningProgramSamplers implements java.io.Serializable {

    // Fields

    private ScreeningProgramSamplersId id;
    private ScreeningProgram screeningProgram;
    private char complete;
    private List<ParameterNames> parameterNames = new ArrayList<ParameterNames>();
    //private List<ScreeningItem> screeningItems = new ArrayList<ScreeningItem>();

    // Constructors

    /** default constructor */
    public ScreeningProgramSamplers() {
    }

    /** minimal constructor */
    public ScreeningProgramSamplers(ScreeningProgramSamplersId id, ScreeningProgram screeningProgram, char complete) {
        this.id = id;
        this.screeningProgram = screeningProgram;
        this.complete = complete;
    }

    /** full constructor */
    public ScreeningProgramSamplers(ScreeningProgramSamplersId id, ScreeningProgram screeningProgram, char complete, List<ParameterNames> parameterNames) {
        this.id = id;
        this.screeningProgram = screeningProgram;
        this.complete = complete;
    }

    // Property accessors
    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name="id", column=@Column(name="ID", unique=false, nullable=false, insertable=true, updatable=true) ),
            @AttributeOverride(name="sampler", column=@Column(name="SAMPLER", unique=false, nullable=false, insertable=true, updatable=true) ) } )

    public ScreeningProgramSamplersId getId() {
        return this.id;
    }

    public void setId(ScreeningProgramSamplersId id) {
        this.id = id;
    }
    @ManyToOne

        @JoinColumn(name="ID", unique=false, nullable=false, insertable=false, updatable=false)

    public ScreeningProgram getScreeningprogram() {
        return this.screeningProgram;
    }

    public void setScreeningprogram(ScreeningProgram screeningProgram) {
        this.screeningProgram = screeningProgram;
    }
    @Column(name="COMPLETE", unique=false, nullable=false, insertable=true, updatable=true, length=1)
    public char getComplete() {
        return this.complete;
    }

    public void setComplete(char complete) {
        this.complete = complete;
    }

    public void setParameterNames(List<ParameterNames> parameterNames) {
		this.parameterNames = parameterNames;
	}
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "screeningitem", joinColumns = { @JoinColumn(name = "screeningprogram_id", referencedColumnName="id"),
                                                        @JoinColumn(name = "samplers_id", referencedColumnName="sampler")},
								inverseJoinColumns = { @JoinColumn(name = "parameter_id", referencedColumnName="id") })
    @OrderBy("id ASC")
	public List<ParameterNames> getParameterNames() {
		return parameterNames;
	}

}