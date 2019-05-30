package com.turing.turingproject.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tax")
@EntityListeners(AuditingEntityListener.class)
public class Tax implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("tax_id")
	private Long taxId;
	
	@JsonProperty("tax_type")
	private String taxType;
	
	@JsonProperty("tax_percentage")
	private Double taxPercentage;

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taxId == null) ? 0 : taxId.hashCode());
		result = prime * result + ((taxPercentage == null) ? 0 : taxPercentage.hashCode());
		result = prime * result + ((taxType == null) ? 0 : taxType.hashCode());
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
		Tax other = (Tax) obj;
		if (taxId == null) {
			if (other.taxId != null)
				return false;
		} else if (!taxId.equals(other.taxId))
			return false;
		if (taxPercentage == null) {
			if (other.taxPercentage != null)
				return false;
		} else if (!taxPercentage.equals(other.taxPercentage))
			return false;
		if (taxType == null) {
			if (other.taxType != null)
				return false;
		} else if (!taxType.equals(other.taxType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tax [taxId=" + taxId + ", taxType=" + taxType + ", taxPercentage=" + taxPercentage + "]";
	}
}
