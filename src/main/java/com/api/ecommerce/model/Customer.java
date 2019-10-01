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

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "customer")
@EntityListeners(AuditingEntityListener.class)
public class Customer implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("customer_id")
	private Long customerId;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	@JsonProperty("credit_card")
	private String creditCard;
	
	@JsonProperty("address_1")
	private String address1;
	
	@JsonProperty("address_2")
	private String address2;
	
	private String city;
	
	private String region;

	@JsonProperty("postal_code")
	private String postalCode;
	
	@JsonProperty("shipping_region_id")
	private int shippingRegionId;
	
	@JsonProperty("day_phone")
	private String dayPhone;
	
	@JsonProperty("eve_phone")
	private String evePhone;
	
	@JsonProperty("mob_phone")
	private String mobPhone;
	
	public Customer() {
		super();
	}

	public Customer(@NotBlank String name, @NotBlank String email, @NotBlank String password,
			String creditCard, String address1, String address2, String city, String region,
			String postalCode, int shippingRegionId, String dayPhone, String evePhone, String mobPhone) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.creditCard = creditCard;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.region = region;
		this.postalCode = postalCode;
		this.shippingRegionId = shippingRegionId;
		this.dayPhone = dayPhone;
		this.evePhone = evePhone;
		this.mobPhone = mobPhone;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public int getShippingRegionId() {
		return shippingRegionId;
	}

	public void setShippingRegionId(int shippingRegionId) {
		this.shippingRegionId = shippingRegionId;
	}

	public String getDayPhone() {
		return dayPhone;
	}

	public void setDayPhone(String dayPhone) {
		this.dayPhone = dayPhone;
	}

	public String getEvePhone() {
		return evePhone;
	}

	public void setEvePhone(String evePhone) {
		this.evePhone = evePhone;
	}

	public String getMobPhone() {
		return mobPhone;
	}

	public void setMobPhone(String mobPhone) {
		this.mobPhone = mobPhone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result + ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((creditCard == null) ? 0 : creditCard.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((dayPhone == null) ? 0 : dayPhone.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((evePhone == null) ? 0 : evePhone.hashCode());
		result = prime * result + ((mobPhone == null) ? 0 : mobPhone.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + shippingRegionId;
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
		Customer other = (Customer) obj;
		if (address1 == null) {
			if (other.address1 != null)
				return false;
		} else if (!address1.equals(other.address1))
			return false;
		if (address2 == null) {
			if (other.address2 != null)
				return false;
		} else if (!address2.equals(other.address2))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (creditCard == null) {
			if (other.creditCard != null)
				return false;
		} else if (!creditCard.equals(other.creditCard))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (dayPhone == null) {
			if (other.dayPhone != null)
				return false;
		} else if (!dayPhone.equals(other.dayPhone))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (evePhone == null) {
			if (other.evePhone != null)
				return false;
		} else if (!evePhone.equals(other.evePhone))
			return false;
		if (mobPhone == null) {
			if (other.mobPhone != null)
				return false;
		} else if (!mobPhone.equals(other.mobPhone))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (shippingRegionId != other.shippingRegionId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", creditCard=" + creditCard + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city
				+ ", region=" + region + ", postalCode=" + postalCode + ", shippingRegionId=" + shippingRegionId
				+ ", dayPhone=" + dayPhone + ", evePhone=" + evePhone + ", mobPhone=" + mobPhone + "]";
	}

}
