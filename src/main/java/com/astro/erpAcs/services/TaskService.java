package com.astro.erpAcs.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.entities.Task;
import com.astro.erpAcs.entities.enums.StatusTask;
import com.astro.erpAcs.repositories.EmployeeRepository;
import com.astro.erpAcs.repositories.TaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskService {

	private final TaskRepository taskRepository;
	private final EmployeeRepository employeeRepository;

	public TaskService(TaskRepository TaskRepository, EmployeeRepository employeeRepository) {
		this.taskRepository = TaskRepository;
		this.employeeRepository = employeeRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Task> findAll() {
		return taskRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Task findById(Long id) {
		return taskRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Task não encontrada"));
	}
	
	@Transactional
	public Task register(Task task) {
						
		if (task.getEndAt().isAfter(LocalDateTime.now()) && task.getStartAt().getDayOfMonth() >= LocalDateTime.now().getDayOfMonth()) {
			
			if (task.getStartAt().getDayOfMonth() == LocalDateTime.now().getDayOfMonth()) {
				task.setStatusTask(StatusTask.IN_PROGRESS);
				return taskRepository.save(task);
			}
			
			task.setStatusTask(StatusTask.NOT_STARTED);
			return taskRepository.save(task);
		}
		
		throw new RuntimeException("ERRO: Datas inválidas");
	
	}
	
	public String employeeCompleteTask(Long taskId, Long employeeId, String resultTask) {
		
		Employee employeeOfSector = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
		
		return taskRepository.findById(taskId)
		
			.map(taskFound -> {
				
				if (taskFound.getSector().equals(employeeOfSector.getSector())) {
					taskFound.setResultTask(resultTask);
					taskFound.setStatusTask(StatusTask.WAITING_CONFIRMATION);
					
					taskRepository.save(taskFound);
					
					return "Resultado enviado com sucesso, aguardando confirmação";
				}
				
				return "ERRO: Funcionário não é do mesmo setor";
			})
		
			.orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
		
	}
	
	@Transactional
	public String addEmployeeOnTask(Long taskId, Long employeeId) {
		
		return taskRepository.findById(taskId)
				.map(taskFound -> {
					
					Employee employeeForAdd = employeeRepository.findById(employeeId)
								.orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
					
					if (taskFound.getSector().equals(employeeForAdd.getSector())) {
						taskFound.getEmployees().add(employeeForAdd);
						taskRepository.save(taskFound);
						return "Funcionário adicionado com sucesso";
					}
					return null;
						
				})
				.orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
		
	}
	
	@Transactional
	public Task update(Long taskId, Task taskUpdateData){
		return taskRepository.findById(taskId)
				 .map(taskFound -> {
					 
					 taskFound.setTitle(taskUpdateData.getTitle());
					 taskFound.setPriorityTask(taskUpdateData.getPriorityTask());
					 taskFound.setStatusTask(taskUpdateData.getStatusTask());
					 taskFound.setSector(taskUpdateData.getSector());
					 taskFound.setDescription(taskUpdateData.getDescription());
					 taskFound.setType(taskUpdateData.getType());
					 
					 return taskRepository.save(taskFound);
				 })
				 .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado " + taskId));
	}
	
	public void delete(Long taskId) {
		try {
			taskRepository.findById(taskId)
				  .map(post -> {
					  	taskRepository.deleteById(taskId);
					  	return "Usuário excluido com sucesso";
					  })
				  .orElseThrow(() -> new EntityNotFoundException("Post não encontrado"));
		}
		catch (DataIntegrityViolationException e) {
			throw new RuntimeException("Violação de integridade");
		}
	}
}
