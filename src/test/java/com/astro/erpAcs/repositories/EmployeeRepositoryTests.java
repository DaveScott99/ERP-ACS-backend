package com.astro.erpAcs.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.astro.erpAcs.entities.Employee;

@DataJpaTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		long existingId = 2L;
		employeeRepository.deleteById(existingId);
		Optional<Employee> result =  employeeRepository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}
	
}
