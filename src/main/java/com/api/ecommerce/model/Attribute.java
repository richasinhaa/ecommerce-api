package com.api.ecommerce.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "attribute")
@EntityListeners(AuditingEntityListener.class)
public class Attribute implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attribute_id;

    @NotBlank
    private String name;

	public Long getAttribute_id() {
		return attribute_id;
	}

	public void setAttribute_id(Long attribute_id) {
		this.attribute_id = attribute_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribute_id == null) ? 0 : attribute_id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (attribute_id == null) {
			if (other.attribute_id != null)
				return false;
		} else if (!attribute_id.equals(other.attribute_id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Attribute [attribute_id=" + attribute_id + ", name=" + name + "]";
	}
}
