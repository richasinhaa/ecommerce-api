package com.api.ecommerce;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.api.ecommerce.controller.AttributeController;
import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.model.Attribute;
import com.api.ecommerce.model.AttributeValue;
import com.api.ecommerce.model.ProductAttributeMap;
import com.api.ecommerce.repository.AttributeRepository;
import com.api.ecommerce.repository.AttributeValueRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AttributeControllerTest {

	@Mock
	AttributeRepository attributeRepository;

	@Mock
	AttributeValueRepository attributeValueRepository;

	@InjectMocks
	AttributeController controller;

	@Test
	public void getAllAttributesSuccessTest() {
		Attribute a = new Attribute();
		a.setAttribute_id(Long.valueOf(1));
		a.setName("dummy_attribute");
		List<Attribute> list = new ArrayList<Attribute>();
		list.add(a);

		Mockito.when(attributeRepository.findAll()).thenReturn(list);

		List<Attribute> response = controller.getAllAttributes();

		assertEquals(response.size(), 1);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getAllAttributesFailureTest() {
		List<Attribute> l = new ArrayList<Attribute>();
		Mockito.when(attributeRepository.findAll()).thenReturn(l);
		controller.getAllAttributes();
	}

	@Test
	public void getAttributeByIdSuccessTest() {
		Attribute a = new Attribute();
		a.setAttribute_id(Long.valueOf(1));
		a.setName("dummy_attribute");

		Mockito.when(attributeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(a));
		
		Attribute res = controller.getAttributeById(Long.valueOf(1));
		
		assertEquals(res.getName(), "dummy_attribute");
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getAttributeByIdFailureTest() {
		Mockito.when(attributeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		controller.getAttributeById(Long.valueOf(1));
	}
	
	@Test
	public void getAllAttributeValuesByIdSuccessTest() {
		AttributeValue v = new AttributeValue();
		v.setAttribute_value_id(Long.valueOf(1));
		v.setAttributeId(Long.valueOf(1));
		v.setValue("dummy_value");
		List<AttributeValue> list = new ArrayList<AttributeValue>();
		list.add(v);
		
		Mockito.when(attributeValueRepository.findByAttributeId(Mockito.anyLong())).thenReturn(list);
		
		List<AttributeValue> res = controller.getAllAttributeValuesById(Long.valueOf(1));
		
		assertEquals(res.get(0).getAttributeId(), Long.valueOf(1));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getAllAttributeValuesByIdFailureTest() {
		List<AttributeValue> list = new ArrayList<AttributeValue>();
		Mockito.when(attributeValueRepository.findByAttributeId(Mockito.anyLong())).thenReturn(list);
		controller.getAllAttributeValuesById(Long.valueOf(1));
	}
	
	@Test
	public void getAllAttributeValuesForProductSuccessTest() {
		ProductAttributeMap m = new ProductAttributeMap("dummy_attribute", Long.valueOf(1), "dummy_value");
		List<ProductAttributeMap> list = new ArrayList<ProductAttributeMap>();
		list.add(m);
		
		Mockito.when(attributeValueRepository.findByProductId(Mockito.anyLong())).thenReturn(list);
		
		List<ProductAttributeMap> res = controller.getAllAttributeValuesForProduct(Long.valueOf(10));
		
		assertEquals(res.get(0).getAttributeName(), "dummy_attribute");
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getAllAttributeValuesForProductFailureTest() {
		List<ProductAttributeMap> list = new ArrayList<ProductAttributeMap>();
		Mockito.when(attributeValueRepository.findByProductId(Mockito.anyLong())).thenReturn(list);
		controller.getAllAttributeValuesForProduct(Long.valueOf(1));
	}

}
