package com.astro.erpAcs.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astro.erpAcs.dto.OfficeDTO;
import com.astro.erpAcs.dto.mapper.OfficeMapper;
import com.astro.erpAcs.entities.Office;
import com.astro.erpAcs.repositories.OfficeRepository;
import com.astro.erpAcs.services.exceptions.DatabaseException;
import com.astro.erpAcs.services.exceptions.EntityNotFoundException;

@Service
public class OfficeService {

	private final OfficeRepository officeRepository;

	private final OfficeMapper officeMapper;
	
	public OfficeService(OfficeRepository officeRepository, OfficeMapper officeMapper) {
		this.officeRepository = officeRepository;
		this.officeMapper = officeMapper;
	}
	
	@Transactional(readOnly = true)
	public Page<OfficeDTO> findAll(Pageable pageable) {
		return officeRepository.findAll(pageable)
			.map(officeMapper::toDTO);
	}
	
	@Transactional(readOnly = true)
	public OfficeDTO findById(Long id) {
		return officeRepository.findById(id)
				.map(officeMapper::toDTO)
				.orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado"));
	}
	
	@Transactional
	public OfficeDTO register(Office office) {
		return officeMapper.toDTO(officeRepository.save(office));
	}
	
	@Transactional
	public OfficeDTO update(Long officeId, Office officeUpdateData){
		return officeRepository.findById(officeId)
				 .map(OfficeFound -> {
					 OfficeFound.setOfficeName(officeUpdateData.getOfficeName());
					 return officeMapper.toDTO(officeRepository.save(OfficeFound));
				 })
				 .orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado " + officeId));
	}
	
	public void delete(Long officeId) {
		try {
			officeRepository.deleteById(officeId);		
		}
		catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Cargo não encontrado ID: " + officeId);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade");
		}
	}
}
