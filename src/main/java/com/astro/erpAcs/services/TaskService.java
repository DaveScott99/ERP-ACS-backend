package com.astro.erpAcs.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astro.erpAcs.entities.Task;
import com.astro.erpAcs.repositories.TaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskService {

	private final TaskRepository TaskRepository;

	public TaskService(TaskRepository TaskRepository) {
		this.TaskRepository = TaskRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Task> findAll() {
		return TaskRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Task findById(Long id) {
		return TaskRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Task não encontrada"));
	}
	
	@Transactional
	public Task register(Task Task) {
		return TaskRepository.save(Task);
	}
	
	@Transactional
	public Task update(Long taskId, Task taskUpdateData){
		return TaskRepository.findById(taskId)
				 .map(taskFound -> {
					 
					 taskFound.setTitle(taskUpdateData.getTitle());
					 taskFound.setPriorityTask(taskUpdateData.getPriorityTask());
					 taskFound.setStatusTask(taskUpdateData.getStatusTask());
					 taskFound.setSector(taskUpdateData.getSector());
					 taskFound.setDescription(taskUpdateData.getDescription());
					 taskFound.setType(taskUpdateData.getType());
					 
					 return TaskRepository.save(taskFound);
				 })
				 .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado " + taskId));
	}
	
	public void delete(Long taskId) {
		try {
			TaskRepository.findById(taskId)
				  .map(post -> {
					  	TaskRepository.deleteById(taskId);
					  	return "Usuário excluido com sucesso";
					  })
				  .orElseThrow(() -> new EntityNotFoundException("Post não encontrado"));
		}
		catch (DataIntegrityViolationException e) {
			throw new RuntimeException("Violação de integridade");
		}
	}
}
