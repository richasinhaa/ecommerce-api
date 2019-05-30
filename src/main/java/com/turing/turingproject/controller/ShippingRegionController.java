package com.turing.turingproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.model.ShippingRegion;
import com.turing.turingproject.repository.ShippingRegionRepository;

@RestController
@RequestMapping("/shipping/regions")
public class ShippingRegionController {
	
	@Autowired
	ShippingRegionRepository shippingRegionRepository;

	// Get All Shipping Regions
	@GetMapping("")
	public List<ShippingRegion> getAllRegions() {
		List<ShippingRegion> list = shippingRegionRepository.findAll();
		if (list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		return list;
	}

	// Get a Single Shipping Region By Id
	@GetMapping("/{shipping_region_id}")
	public List<ShippingRegion> getRegionById(@PathVariable(value = "shipping_region_id", required = true) Long regionId) {
		List<ShippingRegion> list = new ArrayList<ShippingRegion>();
		try {
			ShippingRegion region = shippingRegionRepository.findById(regionId).get();
			list.add(region);
		} catch(Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		if(list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		return list;
	}

}
