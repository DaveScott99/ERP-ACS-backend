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

import com.astro.erpAcs.entities.Office;
import com.astro.erpAcs.services.OfficeService;

@RestController
@RequestMapping("/offices")
public class OfficeController {

	private final OfficeService OfficeService;

	public OfficeController(OfficeService OfficeService) {
		this.OfficeService = OfficeService;
	}
	
	@GetMapping
	public ResponseEntity<List<Office>> findAllPaged(){
		List<Office> users = OfficeService.findAll();
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping(value = "/{OfficeId}")
	public ResponseEntity<Office> findById(@PathVariable Long OfficeId){
		return ResponseEntity.ok().body(OfficeService.findById(OfficeId));
	}
	
	@PostMapping
	public ResponseEntity<Office> register(@RequestBody Office RegisterDTO) {
		Office user = OfficeService.register(RegisterDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}
	
	@PutMapping(value = "/{officeId}")
	public ResponseEntity<Office> update(@PathVariable Long officeId, @RequestBody Office userUpdateDto){
		return ResponseEntity.ok().body(OfficeService.update(officeId, userUpdateDto));
	}
	
	@DeleteMapping(value = "/{officeId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long officeId) {
		OfficeService.delete(officeId);
		return ResponseEntity.noContent().build();
	}
}
