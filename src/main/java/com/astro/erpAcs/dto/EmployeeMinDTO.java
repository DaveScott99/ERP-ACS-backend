package com.astro.erpAcs.dto;

import java.io.Serializable;
import java.util.Objects;

import com.astro.erpAcs.entities.Employee;
import com.astro.erpAcs.entities.Office;

public class EmployeeMinDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private Office office;

	public EmployeeMinDTO(Employee entity) {
		id = entity.getId();
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		office = entity.getOffice();
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

	public Office getOffice() {
		return office;
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
		EmployeeMinDTO other = (EmployeeMinDTO) obj;
		return Objects.equals(id, other.id);
	}
	
}
