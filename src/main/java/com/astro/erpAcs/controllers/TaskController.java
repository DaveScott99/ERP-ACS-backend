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

import com.astro.erpAcs.entities.Task;
import com.astro.erpAcs.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private final TaskService TaskService;

	public TaskController(TaskService TaskService) {
		this.TaskService = TaskService;
	}
	
	@GetMapping
	public ResponseEntity<List<Task>> findAllPaged(){
		List<Task> users = TaskService.findAll();
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping(value = "/{taskId}")
	public ResponseEntity<Task> findById(@PathVariable Long taskId){
		return ResponseEntity.ok().body(TaskService.findById(taskId));
	}
	
	@PostMapping
	public ResponseEntity<Task> register(@RequestBody Task RegisterDTO) {
		Task user = TaskService.register(RegisterDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}
	
	@PutMapping(value = "/{taskId}")
	public ResponseEntity<Task> update(@PathVariable Long taskId, @RequestBody Task userUpdateDto){
		return ResponseEntity.ok().body(TaskService.update(taskId, userUpdateDto));
	}
	
	@DeleteMapping(value = "/{taskId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long taskId) {
		TaskService.delete(taskId);
		return ResponseEntity.noContent().build();
	}
}
