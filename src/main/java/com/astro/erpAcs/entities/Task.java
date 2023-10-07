package com.astro.erpAcs.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TASK")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "TITLE_TASK", nullable = false)
	private String title;
	
	@Column(name = "DESCRIPTION_TASK")
	private String description;
	
	@Column(name = "PRIORITY_TASK")
	private Integer priority;
	
	@Column(name = "STATUS_TASK")
	private Integer status;
	
	@Column(name = "TYPE_TASK")
	private String type;
	
	@Column(name = "CREATION_MOMEMT_TASK")
	@CreationTimestamp
	private Instant creationDate;
	
	@ManyToMany
	private Set<Employee> employees = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "ID_SECTOR", nullable = true)
	private Sector sector;

	public Task(String title, String description, Integer priority, Integer status, String type,
			Instant creationDate, Sector sector) {
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.status = status;
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
