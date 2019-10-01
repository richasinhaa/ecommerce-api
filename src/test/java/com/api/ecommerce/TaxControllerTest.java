package com.api.ecommerce;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.api.ecommerce.controller.TaxController;
import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.model.Tax;
import com.api.ecommerce.repository.TaxRepository;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TaxControllerTest {
	
	@Mock
	TaxRepository taxRepository;
	
	@InjectMocks
	TaxController controller;
	
	@Test
	public void getAllTaxesSuccessTest() {
		Tax t = new Tax();
		t.setTaxId(Long.valueOf(1));
		t.setTaxPercentage(Double.valueOf(5.0));
		t.setTaxType("some-type");
		List<Tax> l = new ArrayList<Tax>();
		l.add(t);
		
		Mockito.when(taxRepository.findAll()).thenReturn(l);
		
		List<Tax> list = controller.getAllTaxes();
		
		assertEquals(list.size(), 1);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getAllTaxesFailureTest() {
		List<Tax> l = new ArrayList<Tax>();
		
		Mockito.when(taxRepository.findAll()).thenReturn(l);
		
		controller.getAllTaxes();
	}
	
	@Test
	public void getTaxByIdSuccessTest() {
		Tax t = new Tax();
		t.setTaxId(Long.valueOf(1));
		t.setTaxPercentage(Double.valueOf(5.0));
		t.setTaxType("some-type");
		
		Mockito.when(taxRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(t));
		
		Tax res = controller.getTaxById(Long.valueOf(1));
		
		assertEquals(res.getTaxPercentage(), Double.valueOf(5.0));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getTaxByIdFailureTest() {
		Mockito.when(taxRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		controller.getTaxById(Long.valueOf(1));
	}

}
