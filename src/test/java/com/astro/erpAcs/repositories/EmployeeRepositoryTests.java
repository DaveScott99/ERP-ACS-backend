package com.astro.erpAcs.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.tests.Factory;

@DataJpaTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalEmployees;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 2L;
		nonExistingId = 100L;
		countTotalEmployees = 2L;
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExists() {
		Optional<Employee> result = employeeRepository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdDoesNotExists() {
		Optional<Employee> result = employeeRepository.findById(nonExistingId);
		Assertions.assertTrue(result.isEmpty());
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		
		Employee employee = Factory.createEmployee();
		employee.setId(null);
		employee = employeeRepository.save(employee);
		
		Assertions.assertNotNull(employee.getId());
		Assertions.assertEquals(countTotalEmployees + 1, employee.getId());
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		employeeRepository.deleteById(existingId);
		Optional<Employee> result =  employeeRepository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}
	
	/*
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {	
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			employeeRepository.deleteById(nonExistingId);
		});
	}
	*/
}
