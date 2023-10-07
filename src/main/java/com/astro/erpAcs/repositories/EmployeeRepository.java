package com.astro.erpAcs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astro.erpAcs.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
