package com.astro.erpAcs.dto.mapper;

import org.springframework.stereotype.Component;

import com.astro.erpAcs.dto.OfficeDTO;
import com.astro.erpAcs.entities.Office;

@Component
public class OfficeMapper {

	public OfficeDTO toDTO(Office office) {
		if (office == null) {
			return null;
		}
		return new OfficeDTO(office);
	}
	
	public Office toEntity(OfficeDTO officeDTO) {
		
		if (officeDTO == null) {
			return null;
		}
		
		Office office = new Office();
		
		if (officeDTO.getId() != null) {
			office.setId(officeDTO.getId());
		}
		
		office.setOfficeName(officeDTO.getOfficeName());
		
		return office;
		
	}
	
}
