package com.astro.erpAcs.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "SECTOR")
public class Sector {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SECTOR")
	private Long id;
	
	@Column(name = "NAME_SECTOR")
	private String nameSector;
	
	@OneToOne
	@JoinColumn(name = "ID_LEADER", referencedColumnName = "ID_EMPLOYEE")
	private Employee leader;
	
	public Sector() {
	}
	
	public Sector(Long id, String nameSector, Employee leader) {
		this.id = id;
		this.nameSector = nameSector;
		this.leader = leader;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
}
