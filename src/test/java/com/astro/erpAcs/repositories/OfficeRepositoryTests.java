package com.astro.erpAcs.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.astro.erpAcs.entities.Office;
import com.astro.erpAcs.tests.Factory;

@DataJpaTest
public class OfficeRepositoryTests {

	@Autowired
	private OfficeRepository officeRepository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalOffices;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 100L;
		countTotalOffices = 1L;
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExists() {
		Optional<Office> result = officeRepository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdDoesNotExists() {
		Optional<Office> result = officeRepository.findById(nonExistingId);
		Assertions.assertTrue(result.isEmpty());
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		
		Office office = Factory.createOffice();
		office.setId(null);
		office = officeRepository.save(office);
		
		Assertions.assertNotNull(office.getId());
		Assertions.assertEquals(countTotalOffices + 1, office.getId());
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		officeRepository.deleteById(existingId);
		Optional<Office> result =  officeRepository.findById(existingId);
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
