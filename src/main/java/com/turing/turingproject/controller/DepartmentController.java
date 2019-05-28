package com.turing.turingproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.model.Department;
import com.turing.turingproject.repository.DepartmentRepository;

@RestController
@RequestMapping("")
public class DepartmentController {

	@Autowired
	DepartmentRepository departmentRepository;

	// Get All Departments
	@GetMapping("/departments")
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	// Get a Single Department
	@GetMapping("/departments/{department_id}")
	public Department getDepartmentById(@PathVariable(value = "department_id", required = true) Long departmentId) {
		return departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500"));
	}
}
