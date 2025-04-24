package com.project_lms.lms.repo;

import com.project_lms.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}