package com.astro.erpAcs.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astro.erpAcs.entities.Office;
import com.astro.erpAcs.repositories.OfficeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OfficeService {

	private final OfficeRepository OfficeRepository;

	public OfficeService(OfficeRepository OfficeRepository) {
		this.OfficeRepository = OfficeRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Office> findAll() {
		return OfficeRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Office findById(Long id) {
		return OfficeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
	}
	
	@Transactional
	public Office register(Office office) {
		return OfficeRepository.save(office);
	}
	
	@Transactional
	public Office update(Long officeId, Office officeUpdateData){
		return OfficeRepository.findById(officeId)
				 .map(OfficeFound -> {
					 OfficeFound.setOfficeName(officeUpdateData.getOfficeName());
					 return OfficeRepository.save(OfficeFound);
				 })
				 .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado " + officeId));
	}
	
	public void delete(Long officeId) {
		try {
			OfficeRepository.findById(officeId)
				  .map(post -> {
					  	OfficeRepository.deleteById(officeId);
					  	return "Usuário excluido com sucesso";
					  })
				  .orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado"));
		}
		catch (DataIntegrityViolationException e) {
			throw new RuntimeException("Violação de integridade");
		}
	}
}
