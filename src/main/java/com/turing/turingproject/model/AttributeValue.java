package com.turing.turingproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "attribute_value")
@EntityListeners(AuditingEntityListener.class)
public class AttributeValue implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "attribute_value_id")
	private Long attribute_value_id;

	@NotBlank
	@JsonIgnore
	@Column(name = "attribute_id")
	private Long attributeId;

	@NotBlank
	@Column(name = "value")
	private String value;

	public Long getAttribute_value_id() {
		return attribute_value_id;
	}

	public void setAttribute_value_id(Long attribute_value_id) {
		this.attribute_value_id = attribute_value_id;
	}

	public Long getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeId == null) ? 0 : attributeId.hashCode());
		result = prime * result + ((attribute_value_id == null) ? 0 : attribute_value_id.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		AttributeValue other = (AttributeValue) obj;
		if (attributeId == null) {
			if (other.attributeId != null)
				return false;
		} else if (!attributeId.equals(other.attributeId))
			return false;
		if (attribute_value_id == null) {
			if (other.attribute_value_id != null)
				return false;
		} else if (!attribute_value_id.equals(other.attribute_value_id))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AttributeValue [attribute_value_id=" + attribute_value_id + ", attributeId=" + attributeId + ", value="
				+ value + "]";
	}

}
