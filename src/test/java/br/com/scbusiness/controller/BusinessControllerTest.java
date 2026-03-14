package br.com.scbusiness.controller;

import br.com.scbusiness.dto.request.BusinessRequestDTO;
import br.com.scbusiness.dto.response.BusinessResponseDTO;
import br.com.scbusiness.enums.BusinessStatus;
import br.com.scbusiness.enums.Segment;
import br.com.scbusiness.exception.ResourceNotFoundException;
import br.com.scbusiness.service.BusinessService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BusinessController.class)
@DisplayName("BusinessController Tests")
class BusinessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BusinessService businessService;

    private BusinessResponseDTO responseDTO;
    private BusinessRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = BusinessResponseDTO.builder()
                .id(1L)
                .name("TechNova")
                .ownerName("Matheus Perger")
                .city("Florianópolis")
                .segment(Segment.TECHNOLOGY)
                .contact("matheus@technova.com.br")
                .status(BusinessStatus.ACTIVE)
                .description("Empresa focada em soluções com IA")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        requestDTO = BusinessRequestDTO.builder()
                .name("TechNova")
                .ownerName("Matheus Perger")
                .city("Florianópolis")
                .segment(Segment.TECHNOLOGY)
                .contact("matheus@technova.com.br")
                .status(BusinessStatus.ACTIVE)
                .description("Empresa focada em soluções com IA")
                .build();
    }

    @Test
    @DisplayName("POST /api/v1/businesses - Should create business and return 201")
    void shouldCreateBusinessAndReturn201() throws Exception {
        when(businessService.create(any(BusinessRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/businesses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("TechNova"))
                .andExpect(jsonPath("$.segment").value("TECHNOLOGY"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("POST /api/v1/businesses - Should return 400 when name is blank")
    void shouldReturn400WhenNameIsBlank() throws Exception {
        requestDTO.setName("");

        mockMvc.perform(post("/api/v1/businesses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/businesses - Should list all businesses and return 200")
    void shouldListAllBusinessesAndReturn200() throws Exception {
        when(businessService.findAll(null, null)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/v1/businesses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("TechNova"));
    }

    @Test
    @DisplayName("GET /api/v1/businesses/{id} - Should return business by ID with 200")
    void shouldReturnBusinessByIdWith200() throws Exception {
        when(businessService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/businesses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("TechNova"));
    }

    @Test
    @DisplayName("GET /api/v1/businesses/{id} - Should return 404 when business not found")
    void shouldReturn404WhenBusinessNotFound() throws Exception {
        when(businessService.findById(99L))
                .thenThrow(new ResourceNotFoundException("Business not found with id: 99"));

        mockMvc.perform(get("/api/v1/businesses/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    @DisplayName("PUT /api/v1/businesses/{id} - Should update business and return 200")
    void shouldUpdateBusinessAndReturn200() throws Exception {
        when(businessService.update(eq(1L), any(BusinessRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/businesses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("DELETE /api/v1/businesses/{id} - Should delete business and return 204")
    void shouldDeleteBusinessAndReturn204() throws Exception {
        doNothing().when(businessService).delete(1L);

        mockMvc.perform(delete("/api/v1/businesses/1"))
                .andExpect(status().isNoContent());

        verify(businessService, times(1)).delete(1L);
    }
}
