package com.astro.erpAcs.tests;

import com.astro.erpAcs.dto.EmployeeMinDTO;
import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.entities.Office;

public class Factory {

	public static Employee createEmployee() {
		return new Employee(1L, "Carlos", "Silva", new Office("Analista de Sistemas"));
	}
	
	public static EmployeeMinDTO createEmployeeDTO() {
		return new EmployeeMinDTO(createEmployee());
	}
	
}
