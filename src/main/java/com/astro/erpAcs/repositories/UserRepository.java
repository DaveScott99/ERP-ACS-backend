package com.astro.erpAcs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astro.erpAcs.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
