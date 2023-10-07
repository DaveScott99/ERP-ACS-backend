package com.astro.erpAcs.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.entities.Sector;
import com.astro.erpAcs.repositories.SectorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SectorService {

	private final SectorRepository sectorRepository;

	public SectorService(SectorRepository sectorRepository) {
		this.sectorRepository = sectorRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Sector> findAll() {
		return sectorRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Sector findById(Long id) {
		return sectorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));
	}
	
	@Transactional
	public Sector register(Sector sector) {
		return sectorRepository.save(sector);
	}
	
	public String addEmployee(Long sectorId, Employee employee) {
		
		return sectorRepository.findById(sectorId)
				.map(sectorFound -> {
					if (sectorFound.getEmployees().contains(employee)) {
						return "Funcionário já está cadastrado no setor";
					}
					
					sectorFound.getEmployees().add(employee);
					
					employee.setSector(sectorFound);
					
					sectorRepository.save(sectorFound);
					return "Funcionário cadastrado com sucesso";
						
			
				})
				.orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));
		
	}
	
	@Transactional
	public Sector update(Long sectorId, Sector sectorUpdateData){
		return sectorRepository.findById(sectorId)
				 .map(sectorFound -> {
					 sectorFound.setNameSector(sectorUpdateData.getNameSector());
					 sectorFound.setLeader(sectorUpdateData.getLeader());
					 return sectorRepository.save(sectorFound);
				 })
				 .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado " + sectorId));
	}
	
	public void delete(Long userId) {
		try {
			sectorRepository.findById(userId)
				  .map(post -> {
					  sectorRepository.deleteById(userId);
					  	return "Setor excluido com sucesso";
					  })
				  .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));
		}
		catch (DataIntegrityViolationException e) {
			throw new RuntimeException("Violação de integridade");
		}
	}
}
