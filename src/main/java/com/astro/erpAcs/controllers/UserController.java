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

import com.astro.erpAcs.entities.User;
import com.astro.erpAcs.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<User>> findAllPaged(){
		List<User> users = userService.findUsers();
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<User> findById(@PathVariable Long userId){
		return ResponseEntity.ok().body(userService.findById(userId));
	}
	
	@PostMapping
	public ResponseEntity<User> register(@RequestBody User RegisterDTO) {
		User user = userService.register(RegisterDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}
	
	@PutMapping(value = "/{userId}")
	public ResponseEntity<User> update(@PathVariable Long userId, @RequestBody User userUpdateDto){
		return ResponseEntity.ok().body(userService.update(userId, userUpdateDto));
	}
	
	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long userId) {
		userService.delete(userId);
		return ResponseEntity.noContent().build();
	}
}
