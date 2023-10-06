package com.astro.erpAcs.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astro.erpAcs.entities.User;
import com.astro.erpAcs.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional(readOnly = true)
	public List<User> findUsers() {
		return userRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
	}
	
	@Transactional
	public User register(User user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public User update(Long userId, User userUpdateData){
		return userRepository.findById(userId)
				 .map(userFound -> {
					 userFound.setFirstName(userUpdateData.getFirstName());
					 userFound.setLastName(userUpdateData.getLastName());
					 return userRepository.save(userFound);
				 })
				 .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado " + userId));
	}
	
	public void delete(Long userId) {
		try {
			userRepository.findById(userId)
				  .map(post -> {
					  	userRepository.deleteById(userId);
					  	return "Usuário excluido com sucesso";
					  })
				  .orElseThrow(() -> new EntityNotFoundException("Post não encontrado"));
		}
		catch (DataIntegrityViolationException e) {
			throw new RuntimeException("Violação de integridade");
		}
	}
}
