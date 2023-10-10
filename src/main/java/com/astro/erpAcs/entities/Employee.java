package com.astro.erpAcs.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_EMPLOYEE")
	private Long id;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@OneToOne(mappedBy = "leader")
	private Sector leaderSector;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_SECTOR", nullable = true)
	private Sector sector;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_OFFICE", nullable = true)
	private Office office;
	
	@ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Task> tasks = new HashSet<>();
	
	@Deprecated
	public Employee() {}

	public Employee(String firstName, String lastName, Office office, Sector sector) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.office = office;
		this.sector = sector;
	}
	
	public Employee(String firstName, String lastName, Office office) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.office = office;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

}
