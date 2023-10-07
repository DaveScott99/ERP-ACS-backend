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
import com.astro.erpAcs.entities.Sector;
import com.astro.erpAcs.services.SectorService;

@RestController
@RequestMapping("/sectors")
public class SectorController {

	private final SectorService sectorService;

	public SectorController(SectorService sectorService) {
		this.sectorService = sectorService;
	}
	
	@GetMapping
	public ResponseEntity<List<Sector>> findAllPaged(){
		List<Sector> sectors = sectorService.findAll();
		return ResponseEntity.ok().body(sectors);
	}
	
	@GetMapping(value = "/{sectorId}")
	public ResponseEntity<Sector> findById(@PathVariable Long sectorId){
		return ResponseEntity.ok().body(sectorService.findById(sectorId));
	}
	
	@PostMapping
	public ResponseEntity<Sector> register(@RequestBody Sector sector) {
		Sector sectorToRegistry = sectorService.register(sector);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(sectorToRegistry.getId()).toUri();
		return ResponseEntity.created(uri).body(sectorToRegistry);
	}
	
	@PostMapping(value = "/{sectorId}/addEmployee")
	public ResponseEntity<String> addEmployee(@PathVariable Long sectorId, @RequestBody Employee employee) {
		return ResponseEntity.ok(sectorService.addEmployee(sectorId, employee));
	}
	
	@PutMapping(value = "/{sectorId}")
	public ResponseEntity<Sector> update(@PathVariable Long sectorId, @RequestBody Sector sectorToUpdate){
		return ResponseEntity.ok().body(sectorService.update(sectorId, sectorToUpdate));
	}
	
	@DeleteMapping(value = "/{sectorId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long sectorId) {
		sectorService.delete(sectorId);
		return ResponseEntity.noContent().build();
	}
}
