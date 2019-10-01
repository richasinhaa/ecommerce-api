package com.api.ecommerce.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductAttributeMap implements Serializable {
	
	@JsonIgnore
	private String productId;
	
	@JsonProperty("attribute_name")
	private String attributeName;

	@JsonProperty("attribute_value_id")
	private Long attributeValueId;

	@JsonProperty("attribute_value")
	private String attributeValue;

	public ProductAttributeMap(String attributeName, Long attributeValueId, String attributeValue) {
		super();
		this.attributeName = attributeName;
		this.attributeValueId = attributeValueId;
		this.attributeValue = attributeValue;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Long getAttributeValueId() {
		return attributeValueId;
	}

	public void setAttributeValueId(Long attributeValueId) {
		this.attributeValueId = attributeValueId;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeName == null) ? 0 : attributeName.hashCode());
		result = prime * result + ((attributeValue == null) ? 0 : attributeValue.hashCode());
		result = prime * result + ((attributeValueId == null) ? 0 : attributeValueId.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		ProductAttributeMap other = (ProductAttributeMap) obj;
		if (attributeName == null) {
			if (other.attributeName != null)
				return false;
		} else if (!attributeName.equals(other.attributeName))
			return false;
		if (attributeValue == null) {
			if (other.attributeValue != null)
				return false;
		} else if (!attributeValue.equals(other.attributeValue))
			return false;
		if (attributeValueId == null) {
			if (other.attributeValueId != null)
				return false;
		} else if (!attributeValueId.equals(other.attributeValueId))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductAttributeMap [productId=" + productId + ", attributeName=" + attributeName
				+ ", attributeValueId=" + attributeValueId + ", attributeValue=" + attributeValue + "]";
	}
	
}
