package com.example.dreamCaseApp.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.example.dreamCaseApp.controller.CaseController;
import com.example.dreamCaseApp.model.Case;
import com.example.dreamCaseApp.repository.CaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

public class CaseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CaseRepository caseRepository;

    @InjectMocks
    private CaseController caseController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(caseController).build();
    }

    @Test
    public void testCreateCase() throws Exception {
        Case newCase = new Case(1L, LocalDateTime.now(), LocalDateTime.now(), "New Case", "Description");
        given(caseRepository.save(any(Case.class))).willReturn(newCase);

        mockMvc.perform(post("/cases/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Case\",\"description\":\"Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Case"));
    }

    @Test
    public void testGetCaseById() throws Exception {
        given(caseRepository.findById(1L)).willReturn(java.util.Optional.of(new Case(1L, LocalDateTime.now(), LocalDateTime.now(), "Existing Case", "Description")));

        mockMvc.perform(get("/cases/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Existing Case"));
    }

    @Test
    public void testUpdateCase() throws Exception {
        Case updatedCase = new Case(1L, LocalDateTime.now(), LocalDateTime.now(), "Updated Title", "Updated Description");
        given(caseRepository.findById(1L)).willReturn(java.util.Optional.of(new Case(1L, LocalDateTime.now(), LocalDateTime.now(), "Old Title", "Old Description")));
        given(caseRepository.save(any(Case.class))).willReturn(updatedCase);

        mockMvc.perform(put("/cases/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\",\"description\":\"Updated Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }


}
