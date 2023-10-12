package com.astro.erpAcs.dto;

import java.io.Serializable;
import java.util.Objects;

import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.entities.Sector;

public class EmployeeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private OfficeDTO office;
	private Sector sector;

	public EmployeeDTO(Employee entity) {
		id = entity.getId();
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		office = new OfficeDTO(entity.getOffice());
		sector = entity.getSector();
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public OfficeDTO getOffice() {
		return office;
	}

	public Sector getSector() {
		return sector;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeDTO other = (EmployeeDTO) obj;
		return Objects.equals(id, other.id);
	}
	
}
