package com.astro.erpAcs.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astro.erpAcs.dto.EmployeeDTO;
import com.astro.erpAcs.dto.EmployeeMinDTO;
import com.astro.erpAcs.dto.mapper.EmployeeMapper;
import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.repositories.EmployeeRepository;
import com.astro.erpAcs.services.exceptions.EntityNotFoundException;
import com.astro.erpAcs.util.MessageResponse;
import com.astro.erpAcs.util.StatusMessage;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final EmployeeMapper employeeMapper;

	public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
		this.employeeRepository = employeeRepository;
		this.employeeMapper = employeeMapper;
	}
	
	@Transactional
	public Page<EmployeeMinDTO> findAll(Pageable pageable) {
		return employeeRepository.findAll(pageable)
				.map(employeeMapper::toMinDTO);
	}
	
	@Transactional
	public EmployeeDTO findById(Long id) {
		return employeeRepository.findById(id)
				.map(employeeMapper::toDTO)
				.orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado ID: " + id));
	}
	
	public EmployeeDTO register(Employee employee) {
		return employeeMapper.toDTO(employeeRepository.save(employee));
	}
	
	public EmployeeDTO update(Long employeeId, Employee employeeUpdateData){
		return employeeRepository.findById(employeeId)
				 .map(employeeFound -> {
					 employeeFound.setFirstName(employeeUpdateData.getFirstName());
					 employeeFound.setLastName(employeeUpdateData.getLastName());
					 return employeeMapper.toDTO(employeeRepository.save(employeeFound));
				 })
				 .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado ID: " + employeeId));
	}
	
	public MessageResponse delete(Long employeeId) {
		try {
			return employeeRepository.findById(employeeId)
				  .map(employeeFound -> {
					  	employeeRepository.deleteById(employeeId);
					  	return new MessageResponse("Funcionário excluido com sucesso", StatusMessage.SUCCESSFUL);
					  })
				  .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
		}
		catch (DataIntegrityViolationException e) {
			throw new RuntimeException("Violação de integridade");
		}
	}
}
