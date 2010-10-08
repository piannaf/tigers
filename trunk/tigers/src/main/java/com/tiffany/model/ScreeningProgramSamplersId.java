package com.tiffany.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
/**
 * Author: Jane
 */
@Embeddable
public class ScreeningProgramSamplersId implements java.io.Serializable{

    // Fields

     private Long id;
     private Long sampler;

    // Constructors

    /** default constructor */
    public ScreeningProgramSamplersId() {
    }


    /** full constructor */
    public ScreeningProgramSamplersId(Long id, Long sampler) {
        this.id = id;
        this.sampler = sampler;
    }


    // Property accessors
    @Column(name="ID", unique=false, nullable=false, insertable=true, updatable=true)

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name="SAMPLER", unique=false, nullable=false, insertable=true, updatable=true)

    public Long getSampler() {
        return this.sampler;
    }

    public void setSampler(Long sampler) {
        this.sampler = sampler;
    }




   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ScreeningProgramSamplersId) ) return false;
		 ScreeningProgramSamplersId castOther = ( ScreeningProgramSamplersId ) other;

		 return ( (this.getId()==castOther.getId()) || ( this.getId()!=null && castOther.getId()!=null && this.getId().equals(castOther.getId()) ) )
 && ( (this.getSampler()==castOther.getSampler()) || ( this.getSampler()!=null && castOther.getSampler()!=null && this.getSampler().equals(castOther.getSampler()) ) );
   }

   public int hashCode() {
         int result = 17;

         result = 37 * result + ( getId() == null ? 0 : this.getId().hashCode() );
         result = 37 * result + ( getSampler() == null ? 0 : this.getSampler().hashCode() );
         return result;
   }





}