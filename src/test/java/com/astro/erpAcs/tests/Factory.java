package com.astro.erpAcs.tests;

import com.astro.erpAcs.dto.EmployeeMinDTO;
import com.astro.erpAcs.dto.OfficeDTO;
import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.entities.Office;

public class Factory {

	public static Employee createEmployee() {
		return new Employee(1L, "Carlos", "Silva", new Office(1L, "Analista de Sistemas"));
	}
	
	public static EmployeeMinDTO createEmployeeDTO() {
		return new EmployeeMinDTO(createEmployee());
	}
	
	public static Office createOffice() {
		return new Office(1L, "Analista de Sistemas");
	}
	
	public static OfficeDTO createOfficeDTO() {
		return new OfficeDTO(createOffice());
	}
}
