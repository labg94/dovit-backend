package com.dovit.backend.repository;

import com.dovit.backend.domain.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, Long> {
}
