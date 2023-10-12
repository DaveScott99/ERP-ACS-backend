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
import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.services.EmployeeService;
import com.astro.erpAcs.util.MessageResponse;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeService EmployeeService;

	public EmployeeController(EmployeeService EmployeeService) {
		this.EmployeeService = EmployeeService;
	}
	
	@GetMapping
	public ResponseEntity<Page<EmployeeDTO>> findAllPaged(@PageableDefault(size = 10) Pageable pageable){
		Page<EmployeeDTO> users = EmployeeService.findAll(pageable);
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping(value = "/{employeeId}")
	public ResponseEntity<EmployeeDTO> findById(@PathVariable Long employeeId){
		return ResponseEntity.ok().body(EmployeeService.findById(employeeId));
	}
	
	@PostMapping
	public ResponseEntity<EmployeeDTO> register(@RequestBody Employee employee) {
		EmployeeDTO user = EmployeeService.register(employee);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}
	
	@PutMapping(value = "/{employeeId}")
	public ResponseEntity<EmployeeDTO> update(@PathVariable Long employeeId, @RequestBody Employee userUpdateDto){
		return ResponseEntity.ok().body(EmployeeService.update(employeeId, userUpdateDto));
	}
	
	@DeleteMapping(value = "/{employeeId}")
	public ResponseEntity<MessageResponse> delete(@PathVariable Long employeeId) {
		return ResponseEntity.ok().body(EmployeeService.delete(employeeId));
	}
}
