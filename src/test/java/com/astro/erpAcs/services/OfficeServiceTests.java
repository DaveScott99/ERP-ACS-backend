package com.astro.erpAcs.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.astro.erpAcs.dto.OfficeDTO;
import com.astro.erpAcs.dto.mapper.OfficeMapper;
import com.astro.erpAcs.entities.Office;
import com.astro.erpAcs.repositories.OfficeRepository;
import com.astro.erpAcs.services.exceptions.DatabaseException;
import com.astro.erpAcs.services.exceptions.EntityNotFoundException;
import com.astro.erpAcs.tests.Factory;

@ExtendWith(SpringExtension.class)
public class OfficeServiceTests {

	@InjectMocks
	private OfficeService service;
	
	@Mock
	private OfficeMapper mapper;
	
	@Mock
	private OfficeRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private PageImpl<Office> page;
	private Office office;
	private OfficeDTO officeDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		office = Factory.createOffice();	
		officeDTO = new OfficeDTO(office);

		page = new PageImpl<>(List.of(office));
		
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(office);
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(office));
		Mockito.when(mapper.toDTO(office)).thenReturn(officeDTO);
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}
	
	@Test
	public void registerShouldRegisterOffice() {
		officeDTO = service.register(office);
		
		Assertions.assertNotNull(office.getId());
		Mockito.verify(repository, Mockito.times(1)).save(office);
	}
	
	@Test
	public void findByIdShouldThrowEntityNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
	}
	
	@Test
	public void findByIdShouldReturnOfficeDTOWhenIdExists() {
        OfficeDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(officeDTO.getId(), result.getId());
    	Mockito.verify(repository, Mockito.times(1)).findById(existingId);
    }
	
	@Test
	public void findAllShouldReturnPage() {
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<OfficeDTO> result = service.findAll(pageable);
		
		Assertions.assertNotNull(result);
		Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	}
	
	@Test
	public void deleteShouldThrowEntityNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}

	
}
