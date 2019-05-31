package com.turing.turingproject;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.turing.turingproject.controller.DepartmentController;
import com.turing.turingproject.model.Department;
import com.turing.turingproject.model.Product;
import com.turing.turingproject.model.Result;
import com.turing.turingproject.repository.DepartmentRepository;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentControllerTest {
	
	@Mock
	DepartmentRepository departmentRepository;

	@InjectMocks
	DepartmentController controller;
	
	@Test
	public void getAllDepartmentsTest() {
		List<Department> list = new ArrayList<Department>();
		Department d = new Department();
		d.setDepartmentId(Long.valueOf(1));
		d.setName("dummy_department");
		d.setDescription("some description");
		list.add(d);

		Mockito.when(departmentRepository.findAll())
				.thenReturn(list);

		List<Department> response = controller.getAllDepartments();

		assertEquals(response.size(), 1);

	}

}
