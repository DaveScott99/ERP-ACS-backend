package com.astro.erpAcs.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "OFFICE")
public class Office {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_OFFICE")
	private Long id;
	
	@Column(name = "NAME_OFFICE", nullable = false)
	private String officeName;

	@OneToMany(mappedBy = "office")
	@JsonIgnore
	private Set<Employee> employees = new HashSet<>();

	@Deprecated
	public Office() {}

	public Office(String officeName) {
		this.officeName = officeName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public Long getId() {
		return id;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}
	
}
