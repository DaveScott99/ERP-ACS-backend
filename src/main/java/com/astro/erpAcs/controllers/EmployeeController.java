package com.astro.erpAcs.controllers;

import java.net.URI;
import java.util.List;

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

import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeService EmployeeService;

	public EmployeeController(EmployeeService EmployeeService) {
		this.EmployeeService = EmployeeService;
	}
	
	@GetMapping
	public ResponseEntity<List<Employee>> findAllPaged(){
		List<Employee> users = EmployeeService.findAll();
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping(value = "/{employeeId}")
	public ResponseEntity<Employee> findById(@PathVariable Long employeeId){
		return ResponseEntity.ok().body(EmployeeService.findById(employeeId));
	}
	
	@PostMapping
	public ResponseEntity<Employee> register(@RequestBody Employee RegisterDTO) {
		Employee user = EmployeeService.register(RegisterDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}
	
	@PutMapping(value = "/{employeeId}")
	public ResponseEntity<Employee> update(@PathVariable Long employeeId, @RequestBody Employee userUpdateDto){
		return ResponseEntity.ok().body(EmployeeService.update(employeeId, userUpdateDto));
	}
	
	@DeleteMapping(value = "/{employeeId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long employeeId) {
		EmployeeService.delete(employeeId);
		return ResponseEntity.noContent().build();
	}
}
