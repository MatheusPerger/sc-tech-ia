package br.com.scbusiness.controller;

import br.com.scbusiness.dto.request.BusinessRequestDTO;
import br.com.scbusiness.dto.response.BusinessResponseDTO;
import br.com.scbusiness.enums.BusinessStatus;
import br.com.scbusiness.enums.Segment;
import br.com.scbusiness.service.BusinessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/businesses")
@RequiredArgsConstructor
@Tag(name = "Businesses", description = "Endpoints for managing businesses in Santa Catarina")
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping
    @Operation(summary = "Register a new business")
    public ResponseEntity<BusinessResponseDTO> create(@Valid @RequestBody BusinessRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(businessService.create(request));
    }

    @GetMapping
    @Operation(summary = "List all businesses with optional filters by status and segment")
    public ResponseEntity<List<BusinessResponseDTO>> findAll(
            @RequestParam(required = false) BusinessStatus status,
            @RequestParam(required = false) Segment segment) {
        return ResponseEntity.ok(businessService.findAll(status, segment));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a business by ID")
    public ResponseEntity<BusinessResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(businessService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a business by ID")
    public ResponseEntity<BusinessResponseDTO> update(@PathVariable Long id, @Valid @RequestBody BusinessRequestDTO request) {
        return ResponseEntity.ok(businessService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a business by ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        businessService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
