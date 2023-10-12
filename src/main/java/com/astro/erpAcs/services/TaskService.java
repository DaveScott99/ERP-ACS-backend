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
import com.astro.erpAcs.util.MessageResponse;
import com.astro.erpAcs.util.StatusMessage;

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
	
	public MessageResponse completeTask(Long taskId, Long employeeId, String resultTask) {
		
		Employee employeeOfSector = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
		
		return taskRepository.findById(taskId)
		
			.map(taskFound -> {
				
				if (taskFound.getSector().equals(employeeOfSector.getSector()) && employeeOfSector.getLeaderSector() == null) {
					taskFound.setResultTask(resultTask);
					taskFound.setStatusTask(StatusTask.WAITING_CONFIRMATION);
					taskRepository.save(taskFound);
					return new MessageResponse("Resultado enviado com sucesso, aguardando confirmação.", StatusMessage.SUCCESSFUL);
				}
				else if (taskFound.getSector().equals(employeeOfSector.getSector()) && taskFound.getSector().getLeader().equals(employeeOfSector)) {
					taskFound.setStatusTask(StatusTask.COMPLETED);
					taskRepository.save(taskFound);
					return new MessageResponse("Tarefa concluida com sucesso!", StatusMessage.SUCCESSFUL);

				}
				
				return new MessageResponse("ERRO: Funcionário não é do mesmo setor", StatusMessage.ERROR);
			})
		
			.orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
		
	}
	
	@Transactional
	public MessageResponse addEmployeeOnTask(Long taskId, Long employeeId) {
		
		return taskRepository.findById(taskId)
				.map(taskFound -> {
					
					Employee employeeForAdd = employeeRepository.findById(employeeId)
								.orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
					
					if (taskFound.getSector().equals(employeeForAdd.getSector())) {
						taskFound.getEmployees().add(employeeForAdd);
						taskRepository.save(taskFound);
					
						return new MessageResponse("Funcionário adicionado com sucesso", StatusMessage.SUCCESSFUL);
					}
	
					return new MessageResponse("Funcionário não é do mesmo setor", StatusMessage.ERROR);
					
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
