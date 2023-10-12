package com.astro.erpAcs.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astro.erpAcs.dto.OfficeDTO;
import com.astro.erpAcs.dto.mapper.OfficeMapper;
import com.astro.erpAcs.entities.Office;
import com.astro.erpAcs.repositories.OfficeRepository;
import com.astro.erpAcs.util.MessageResponse;
import com.astro.erpAcs.util.StatusMessage;

import jakarta.persistence.EntityNotFoundException;

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
	
	public MessageResponse delete(Long officeId) {
		try {
			return officeRepository.findById(officeId)
				  .map(post -> {
					  	officeRepository.deleteById(officeId);
					  	return new MessageResponse("Cargo excluido com sucesso", StatusMessage.SUCCESSFUL);					  	
					  })
				  .orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado"));
		}
		catch (DataIntegrityViolationException e) {
			throw new RuntimeException("Violação de integridade");
		}
	}
}
