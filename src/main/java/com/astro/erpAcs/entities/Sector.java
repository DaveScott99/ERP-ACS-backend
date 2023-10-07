package com.astro.erpAcs.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "SECTOR")
public class Sector {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SECTOR")
	private Long id;
	
	@Column(name = "NAME_SECTOR", nullable = false)
	private String nameSector;
	
	@OneToOne
	@JoinColumn(name = "ID_LEADER", referencedColumnName = "ID_EMPLOYEE")
	private Employee leader;
	
	@OneToMany(mappedBy = "sector")
	@JsonIgnore
	private Set<Employee> employees = new HashSet<>();
	
	@Deprecated
	public Sector() {}
	
	public Sector(String nameSector, Employee leader) {
		this.nameSector = nameSector;
		this.leader = leader;
	}

	public Long getId() {
		return id;
	}

	public String getNameSector() {
		return nameSector;
	}

	public void setNameSector(String nameSector) {
		this.nameSector = nameSector;
	}

	public Employee getLeader() {
		return leader;
	}

	public void setLeader(Employee leader) {
		this.leader = leader;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}
	
}
