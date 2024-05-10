package com.example.dreamCaseApp.controller;

import com.example.dreamCaseApp.model.Case;
import com.example.dreamCaseApp.repository.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/cases")
public class CaseController {

    @Autowired
    private CaseRepository caseRepository;

    // CREATE
    @PostMapping("/")
    public Case createCase(@RequestBody Case newCase) {
        return caseRepository.save(newCase);
    }

    // READ
    @GetMapping("/{id}")
    public Case getCaseById(@PathVariable Long id) {
        return caseRepository.findById(id).orElseThrow(() -> new RuntimeException("Case not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Case> updateCase(@PathVariable Long id, @RequestBody Case updatedCase) {
        Case updated = caseRepository.findById(id)
                .map(existingCase -> {
                    existingCase.setTitle(updatedCase.getTitle());
                    existingCase.setDescription(updatedCase.getDescription());
                    existingCase.setLastUpdateDate(LocalDateTime.now());
                    return caseRepository.save(existingCase);
                }).orElseThrow(() -> new RuntimeException("Case not found"));
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Case> deleteCase(@PathVariable Long id) {
        Case existingCase = caseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Case not found"));

        caseRepository.delete(existingCase);

        return ResponseEntity.ok().build();
    }

}