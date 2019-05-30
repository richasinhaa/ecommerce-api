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
@Table(name = "shipping_region")
@EntityListeners(AuditingEntityListener.class)
public class ShippingRegion implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("shipping_region_id")
	private Long shippingRegionId;
	
	@JsonProperty("shipping_region")
	private String shippingRegion;

	public Long getShippingRegionId() {
		return shippingRegionId;
	}

	public void setShippingRegionId(Long shippingRegionId) {
		this.shippingRegionId = shippingRegionId;
	}

	public String getShippingRegion() {
		return shippingRegion;
	}

	public void setShippingRegion(String shippingRegion) {
		this.shippingRegion = shippingRegion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shippingRegion == null) ? 0 : shippingRegion.hashCode());
		result = prime * result + ((shippingRegionId == null) ? 0 : shippingRegionId.hashCode());
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
		ShippingRegion other = (ShippingRegion) obj;
		if (shippingRegion == null) {
			if (other.shippingRegion != null)
				return false;
		} else if (!shippingRegion.equals(other.shippingRegion))
			return false;
		if (shippingRegionId == null) {
			if (other.shippingRegionId != null)
				return false;
		} else if (!shippingRegionId.equals(other.shippingRegionId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ShippingRegion [shippingRegionId=" + shippingRegionId + ", shippingRegion=" + shippingRegion + "]";
	}
}
