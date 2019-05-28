package com.turing.turingproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.model.Attribute;
import com.turing.turingproject.model.AttributeValue;
import com.turing.turingproject.model.ProductAttributeMap;
import com.turing.turingproject.repository.AttributeRepository;
import com.turing.turingproject.repository.AttributeValueRepository;

@RestController
@RequestMapping("/attributes")
public class AttributeController {

	@Autowired
	AttributeRepository attributeRepository;

	@Autowired
	AttributeValueRepository attributeValueRepository;

	// Get All Attributes
	@GetMapping("")
	public List<Attribute> getAllAttributes() {
		List<Attribute> list = attributeRepository.findAll();
		if (list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		return list;
	}

	// Get a Single Attribute
	@GetMapping("/{attribute_id}")
	public Attribute getAttributeById(@PathVariable(value = "attribute_id", required = true) Long attributeId) {
		return attributeRepository.findById(attributeId).orElseThrow(
				() -> new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500"));
	}

	// Get All Attribute Values
	@GetMapping("/values/{attribute_id}")
	public List<AttributeValue> getAllAttributeValuesById(
			@PathVariable(value = "attribute_id", required = true) Long attributeId) {
		List<AttributeValue> list = attributeValueRepository.findByAttributeId(attributeId);
		if (list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		return list;
	}
	
	// Get All Attribute Values For A Product Id
		@GetMapping("/inProduct/{product_id}")
		public List<ProductAttributeMap> getAllAttributeValuesForProduct(
				@PathVariable(value = "product_id", required = true) Long productId) {
			List<ProductAttributeMap> list = attributeValueRepository.findByProductId(productId);
			if (list.isEmpty()) {
				throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
			}
			return list;
		}

}
