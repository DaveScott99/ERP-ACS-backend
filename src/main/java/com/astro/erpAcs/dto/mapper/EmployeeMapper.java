package com.astro.erpAcs.dto.mapper;

import org.springframework.stereotype.Component;

import com.astro.erpAcs.dto.EmployeeDTO;
import com.astro.erpAcs.dto.EmployeeMinDTO;
import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.entities.Office;

@Component
public class EmployeeMapper {

	public EmployeeDTO toDTO(Employee employee) {
		if (employee == null) {
			return null;
		}
		return new EmployeeDTO(employee);
	}
	
	public EmployeeMinDTO toMinDTO(Employee employee) {
		if (employee == null) {
			return null;
		}
		return new EmployeeMinDTO(employee);
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
		employee.setOffice(new Office(employeeDTO.getOffice().getId(), employeeDTO.getOffice().getOfficeName()));
		employee.setSector(employeeDTO.getSector());
		
		return employee;
		
	}
	
}
