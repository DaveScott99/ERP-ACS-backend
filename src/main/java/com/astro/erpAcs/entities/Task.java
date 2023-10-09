package com.astro.erpAcs.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.astro.erpAcs.entities.enums.PriorityTask;
import com.astro.erpAcs.entities.enums.StatusTask;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Column(name = "DESCRIPTION_TASK")
	private String description;
	
	@Column(name = "PRIORITY_TASK")
	private Integer priorityTask;
	
	@Column(name = "STATUS_TASK")
	private Integer statusTask;
	
	@Column(name = "TYPE_TASK")
	private String type;
	
	@Column(name = "CREATION_MOMEMT_TASK")
	@CreationTimestamp
	private Instant creationDate;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "TASK_EMPLOYEE",
        joinColumns = @JoinColumn(name = "ID_TASK"),
        inverseJoinColumns = @JoinColumn(name = "ID_EMPLOYEE")
	)
	@JsonIgnore
	private Set<Employee> employees = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "ID_SECTOR", nullable = true)
	private Sector sector;
	
	@Deprecated
	public Task() {}

	public Task(String title, String description, PriorityTask priorityTask, StatusTask statusTask, String type,
			Instant creationDate, Sector sector) {
		this.title = title;
		this.description = description;
		setPriorityTask(priorityTask);
		setStatusTask(statusTask);
		this.type = type;
		this.creationDate = creationDate;
		this.sector = sector;
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

	public Instant getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Instant creationDate) {
		this.creationDate = creationDate;
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
	
}
