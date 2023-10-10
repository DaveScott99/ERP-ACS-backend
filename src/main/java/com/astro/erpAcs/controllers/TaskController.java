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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.astro.erpAcs.entities.Task;
import com.astro.erpAcs.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService TaskService) {
		this.taskService = TaskService;
	}
	
	@GetMapping
	public ResponseEntity<List<Task>> findAllPaged(){
		List<Task> users = taskService.findAll();
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping(value = "/{taskId}")
	public ResponseEntity<Task> findById(@PathVariable Long taskId){
		return ResponseEntity.ok().body(taskService.findById(taskId));
	}
	
	@PostMapping
	public ResponseEntity<Task> register(@RequestBody Task RegisterDTO) {
		Task user = taskService.register(RegisterDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}
	
	@PostMapping(value = "/addEmployee")
	public ResponseEntity<String> addEmployeeOnTask(@RequestParam Long taskId, @RequestParam Long employeeId) {
		return ResponseEntity.ok(taskService.addEmployeeOnTask(taskId, employeeId));
	}
	
	@PutMapping(value = "/{taskId}")
	public ResponseEntity<Task> update(@PathVariable Long taskId, @RequestBody Task userUpdateDto){
		return ResponseEntity.ok().body(taskService.update(taskId, userUpdateDto));
	}
	
	@DeleteMapping(value = "/{taskId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long taskId) {
		taskService.delete(taskId);
		return ResponseEntity.noContent().build();
	}
}
