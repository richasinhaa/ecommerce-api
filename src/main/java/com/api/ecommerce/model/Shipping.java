package com.api.ecommerce.model;

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
@Table(name = "shipping")
@EntityListeners(AuditingEntityListener.class)
public class Shipping implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("shipping_id")
	private Long shippingId;
	
	@JsonProperty("shipping_type")
	private String shippingType;
	
	@JsonProperty("shipping_cost")
	private Double shippingCost;
	
	@JsonProperty("shipping_region_id")
	private Long shippingRegionId;

	public Long getShippingId() {
		return shippingId;
	}

	public void setShippingId(Long shippingId) {
		this.shippingId = shippingId;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public Double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
	}

	public Long getShippingRegionId() {
		return shippingRegionId;
	}

	public void setShippingRegionId(Long shippingRegionId) {
		this.shippingRegionId = shippingRegionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shippingCost == null) ? 0 : shippingCost.hashCode());
		result = prime * result + ((shippingId == null) ? 0 : shippingId.hashCode());
		result = prime * result + ((shippingRegionId == null) ? 0 : shippingRegionId.hashCode());
		result = prime * result + ((shippingType == null) ? 0 : shippingType.hashCode());
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
		Shipping other = (Shipping) obj;
		if (shippingCost == null) {
			if (other.shippingCost != null)
				return false;
		} else if (!shippingCost.equals(other.shippingCost))
			return false;
		if (shippingId == null) {
			if (other.shippingId != null)
				return false;
		} else if (!shippingId.equals(other.shippingId))
			return false;
		if (shippingRegionId == null) {
			if (other.shippingRegionId != null)
				return false;
		} else if (!shippingRegionId.equals(other.shippingRegionId))
			return false;
		if (shippingType == null) {
			if (other.shippingType != null)
				return false;
		} else if (!shippingType.equals(other.shippingType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Shipping [shippingId=" + shippingId + ", shippingType=" + shippingType + ", shippingCost="
				+ shippingCost + ", shippingRegionId=" + shippingRegionId + "]";
	}
}
