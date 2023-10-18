package com.astro.erpAcs.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.astro.erpAcs.dto.EmployeeDTO;
import com.astro.erpAcs.dto.EmployeeMinDTO;
import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping
	public ResponseEntity<Page<EmployeeMinDTO>> findAllPaged(@PageableDefault(size = 10) Pageable pageable){
		Page<EmployeeMinDTO> users = employeeService.findAll(pageable);
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping(value = "/{employeeId}")
	public ResponseEntity<EmployeeDTO> findById(@PathVariable Long employeeId){
		return ResponseEntity.ok().body(employeeService.findById(employeeId));
	}
	
	@PostMapping
	public ResponseEntity<EmployeeDTO> register(@RequestBody Employee employee) {
		EmployeeDTO user = employeeService.register(employee);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}
	
	@PutMapping(value = "/{employeeId}")
	public ResponseEntity<EmployeeDTO> update(@PathVariable Long employeeId, @RequestBody Employee userUpdateDto){
		return ResponseEntity.ok().body(employeeService.update(employeeId, userUpdateDto));
	}
	
	@DeleteMapping(value = "/{employeeId}")
	public ResponseEntity<Void> delete(@PathVariable Long employeeId) {
		employeeService.delete(employeeId);
		return ResponseEntity.noContent().build();
	}
}
