package com.turing.turingproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.model.Tax;
import com.turing.turingproject.repository.TaxRepository;

@RestController
@RequestMapping("/tax")
public class TaxController {

	@Autowired
	TaxRepository taxRepository;

	// Get All Taxes
	@GetMapping("")
	public List<Tax> getAllTaxes() {
		List<Tax> list = taxRepository.findAll();
		if (list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		return list;
	}

	// Get a Single Tax
	@GetMapping("/{tax_id}")
	public Tax getTaxById(@PathVariable(value = "tax_id", required = true) Long taxId) {
		return taxRepository.findById(taxId).orElseThrow(
				() -> new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500"));
	}

}
