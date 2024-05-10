package com.example.dreamCaseApp.repository;

import com.example.dreamCaseApp.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Case, Long> {
}
