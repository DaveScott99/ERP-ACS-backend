package com.astro.erpAcs.dto;

import java.io.Serializable;
import java.util.Objects;

import com.astro.erpAcs.entities.Office;

public class OfficeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String officeName;
	
	public OfficeDTO(Office office) {
		id = office.getId();
		officeName = office.getOfficeName();
	}

	public Long getId() {
		return id;
	}

	public String getOfficeName() {
		return officeName;
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
		OfficeDTO other = (OfficeDTO) obj;
		return Objects.equals(id, other.id);
	}
	
}
