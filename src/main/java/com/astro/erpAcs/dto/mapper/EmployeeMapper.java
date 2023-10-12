package com.astro.erpAcs.dto.mapper;

import org.springframework.stereotype.Component;

import com.astro.erpAcs.dto.EmployeeDTO;
import com.astro.erpAcs.entities.Employee;

@Component
public class EmployeeMapper {

	public EmployeeDTO toDTO(Employee employee) {
		if (employee == null) {
			return null;
		}
		return new EmployeeDTO(employee);
	}
	
	public Employee toEntity(EmployeeDTO employeeDTO) {
		
		if (employeeDTO == null) {
			return null;
		}
		
		Employee employee = new Employee();
		
		if (employeeDTO.getId() != null) {
			employee.setId(employeeDTO.getId());
		}
		
		employee.setFirstName(employeeDTO.getFirstName());
		employee.setLastName(employeeDTO.getLastName());
		employee.setOffice(employeeDTO.getOffice());
		employee.setSector(employeeDTO.getSector());
		
		return employee;
		
	}
	
}
