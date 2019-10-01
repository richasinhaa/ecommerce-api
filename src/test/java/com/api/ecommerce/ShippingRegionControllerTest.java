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

import com.api.ecommerce.controller.ShippingRegionController;
import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.model.ShippingRegion;
import com.api.ecommerce.repository.ShippingRegionRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ShippingRegionControllerTest {
	
	@Mock
	ShippingRegionRepository repository;

	@InjectMocks
	ShippingRegionController controller;
	
	@Test
	public void getAllRegionsSuccessTest() {
		List<ShippingRegion> list = new ArrayList<ShippingRegion>();
		ShippingRegion s = new ShippingRegion();
		s.setShippingRegion("dummy_region");
		s.setShippingRegionId(Long.valueOf(1));
		list.add(s);

		Mockito.when(repository.findAll())
				.thenReturn(list);

		List<ShippingRegion> response = controller.getAllRegions();

		assertEquals(response.size(), 1);

	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getAllRegionsFailureTest() {
		List<ShippingRegion> list = new ArrayList<ShippingRegion>();

		Mockito.when(repository.findAll())
				.thenReturn(list);

		controller.getAllRegions();
	}
	
	@Test
	public void getRegionByIdSuccessTest() {
		ShippingRegion s = new ShippingRegion();
		s.setShippingRegion("dummy_region");
		s.setShippingRegionId(Long.valueOf(1));

		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(s));
		
		List<ShippingRegion> res = controller.getRegionById(Long.valueOf(1));
		
		assertEquals(res.size(), 1);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getRegionByIdFailureTest() {
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		controller.getRegionById(Long.valueOf(1));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getRegionByIdNullTest() {
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(null);
		controller.getRegionById(Long.valueOf(1));
	}

}
