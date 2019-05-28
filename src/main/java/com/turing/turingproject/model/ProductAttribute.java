package com.turing.turingproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "product_attribute")
@EntityListeners(AuditingEntityListener.class)
public class ProductAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;

	@NotBlank
	@Column(name = "attribute_value_id")
	private Long attributeValueId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getAttributeValueId() {
		return attributeValueId;
	}

	public void setAttributeValueId(Long attributeValueId) {
		this.attributeValueId = attributeValueId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		ProductAttribute other = (ProductAttribute) obj;
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
		return "ProductAttribute [productId=" + productId + ", attributeValueId=" + attributeValueId + "]";
	}

}
