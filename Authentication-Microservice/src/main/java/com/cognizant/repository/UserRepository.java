package com.cognizant.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.entities.AuthUsers;


@Repository
public interface UserRepository extends JpaRepository<AuthUsers, String> {
	public AuthUsers findByUserId(String userId);
}