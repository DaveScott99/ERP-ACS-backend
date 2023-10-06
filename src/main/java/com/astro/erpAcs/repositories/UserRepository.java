package com.astro.erpAcs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astro.erpAcs.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
