package com.astro.erpAcs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astro.erpAcs.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
