package com.tiffany.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import javax.persistence.*;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * This class represents the "samplermedia" object in TiGERS
 *
 * @author CSSE3004GC
 */
@Entity
@Table(name="samplermedia")
public class SamplerMedia extends BaseObject {
    
    private Long id;
    private String tag;
    private String description;
    private String fileName;                    
        
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable=false, length = 5)
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@Column(nullable=false, length = 200)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(nullable=false, length = 100)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof SamplerMedia))
			return false;
		SamplerMedia castOther = (SamplerMedia) other;
		return new EqualsBuilder().append(tag, castOther.tag)
				.append(fileName, castOther.fileName).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(591165033, -1663524385).append(tag)
				.append(fileName).toHashCode();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("tag", tag).append("fileName",
				fileName).toString();
	}
}
