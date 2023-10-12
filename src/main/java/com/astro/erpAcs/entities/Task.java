package com.astro.erpAcs.entities;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.astro.erpAcs.entities.enums.PriorityTask;
import com.astro.erpAcs.entities.enums.StatusTask;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TASK")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TASK")
	private Long id;
	
	@Column(name = "TITLE_TASK", nullable = false)
	private String title;
	
	@Column(name = "DESCRIPTION_TASK", nullable = false)
	private String description;
	
	@Column(name = "PRIORITY_TASK", nullable = false)
	private Integer priorityTask;
	
	@Column(name = "STATUS_TASK", nullable = true)
	private Integer statusTask;
	
	@Column(name = "TYPE_TASK", nullable = false)
	private String type;
	
	@Column(name = "START_AT_TASK", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startAt;
	
	@Column(name = "END_AT_TASK", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endAt;
	
	@Column(name = "CREATION_MOMEMT_TASK")
	@CreationTimestamp
	private Instant createdAt;
	
	@Column(name = "RESULT_TASK")
	private String resultTask;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "TASK_EMPLOYEE",
        joinColumns = @JoinColumn(name = "ID_TASK"),
        inverseJoinColumns = @JoinColumn(name = "ID_EMPLOYEE")
	)
	@JsonIgnoreProperties({"tasks", "sector"})
	private Set<Employee> employees = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_SECTOR", nullable = false)
	@JsonIgnoreProperties({"leader","employees", "tasks"})
	private Sector sector;
	
	@Deprecated
	public Task() {}
	
	public Task(String title, String description, PriorityTask priorityTask, String type,
			 Sector sector, LocalDateTime startAt, LocalDateTime endAt) {
		this.title = title;
		this.description = description;
		setPriorityTask(priorityTask);
		this.type = type;
		this.sector = sector;
		this.startAt = startAt;
		this.endAt = endAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PriorityTask getPriorityTask() {
		return PriorityTask.valueOf(priorityTask);
	}

	public void setPriorityTask(PriorityTask priority) {
		if (priority != null) {
			this.priorityTask = priority.getCode();
		}
	}

	public StatusTask getStatusTask() {
		return StatusTask.valueOf(statusTask);
	}

	public void setStatusTask(StatusTask status) {
		if (status != null) {
			this.statusTask = status.getCode();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public LocalDateTime getStartAt() {
		return startAt;
	}

	public void setStartAt(LocalDateTime startAt) {
		this.startAt = startAt;
	}

	public LocalDateTime getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public Long getId() {
		return id;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public String getResultTask() {
		return resultTask;
	}

	public void setResultTask(String resultTask) {
		this.resultTask = resultTask;
	}
	
}
