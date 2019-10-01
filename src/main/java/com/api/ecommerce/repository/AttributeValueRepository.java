package com.api.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.ecommerce.model.AttributeValue;
import com.api.ecommerce.model.ProductAttributeMap;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
	/**
	 * Returns list of attribute values
	 *
	 * @param id - Id
	 * @return - List<AttributeValue>
	 */
	@Query("SELECT v FROM AttributeValue v where v.attributeId = ?1")
	List<AttributeValue> findByAttributeId(Long id);
	
	/**
	 * Returns list of product attribute map
	 *
	 * @param productId - Product Id
	 * @return - List<ProductAttributeMap>
	 */
	@Query("select new com.api.ecommerce.model.ProductAttributeMap(a.name as attributeName,v.attribute_value_id as attributeValueId,v.value as attributeValue) from ProductAttribute pa join AttributeValue v on pa.attributeValueId=v.attribute_value_id join Attribute a on v.attributeId=a.attribute_id WHERE pa.productId= ?1")
	List<ProductAttributeMap> findByProductId(Long productId);

}
