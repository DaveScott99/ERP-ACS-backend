package com.astro.erpAcs.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.repositories.EmployeeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeService {

	private final EmployeeRepository EmployeeRepository;

	public EmployeeService(EmployeeRepository EmployeeRepository) {
		this.EmployeeRepository = EmployeeRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Employee> findAll() {
		return EmployeeRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Employee findById(Long id) {
		return EmployeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
	}
	
	@Transactional
	public Employee register(Employee Employee) {
		return EmployeeRepository.save(Employee);
	}
	
	@Transactional
	public Employee update(Long EmployeeId, Employee EmployeeUpdateData){
		return EmployeeRepository.findById(EmployeeId)
				 .map(EmployeeFound -> {
					 EmployeeFound.setFirstName(EmployeeUpdateData.getFirstName());
					 EmployeeFound.setLastName(EmployeeUpdateData.getLastName());
					 return EmployeeRepository.save(EmployeeFound);
				 })
				 .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado " + EmployeeId));
	}
	
	public void delete(Long EmployeeId) {
		try {
			EmployeeRepository.findById(EmployeeId)
				  .map(post -> {
					  	EmployeeRepository.deleteById(EmployeeId);
					  	return "Usuário excluido com sucesso";
					  })
				  .orElseThrow(() -> new EntityNotFoundException("Post não encontrado"));
		}
		catch (DataIntegrityViolationException e) {
			throw new RuntimeException("Violação de integridade");
		}
	}
}
