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

import com.astro.erpAcs.dto.OfficeDTO;
import com.astro.erpAcs.entities.Office;
import com.astro.erpAcs.services.OfficeService;
import com.astro.erpAcs.util.MessageResponse;

@RestController
@RequestMapping("/offices")
public class OfficeController {

	private final OfficeService OfficeService;

	public OfficeController(OfficeService OfficeService) {
		this.OfficeService = OfficeService;
	}
	
	@GetMapping
	public ResponseEntity<Page<OfficeDTO>> findAllPaged(@PageableDefault(size = 10) Pageable pageable){
		Page<OfficeDTO> users = OfficeService.findAll(pageable);
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping(value = "/{OfficeId}")
	public ResponseEntity<OfficeDTO> findById(@PathVariable Long OfficeId){
		return ResponseEntity.ok().body(OfficeService.findById(OfficeId));
	}
	
	@PostMapping
	public ResponseEntity<OfficeDTO> register(@RequestBody Office office) {
		OfficeDTO officeDTO = OfficeService.register(office);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(office.getId()).toUri();
		return ResponseEntity.created(uri).body(officeDTO);
	}
	
	@PutMapping(value = "/{officeId}")
	public ResponseEntity<OfficeDTO> update(@PathVariable Long officeId, @RequestBody Office office){
		return ResponseEntity.ok().body(OfficeService.update(officeId, office));
	}
	
	@DeleteMapping(value = "/{officeId}")
	public ResponseEntity<MessageResponse> deletePost(@PathVariable Long officeId) {
		return ResponseEntity.ok().body(OfficeService.delete(officeId));
	}
}
