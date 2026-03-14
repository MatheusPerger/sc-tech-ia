package br.com.scbusiness.service;

import br.com.scbusiness.dto.request.BusinessRequestDTO;
import br.com.scbusiness.dto.response.BusinessResponseDTO;
import br.com.scbusiness.enums.BusinessStatus;
import br.com.scbusiness.enums.Segment;
import br.com.scbusiness.exception.ResourceNotFoundException;
import br.com.scbusiness.model.Business;
import br.com.scbusiness.repository.BusinessRepository;
import br.com.scbusiness.service.impl.BusinessServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BusinessServiceImpl Tests")
class BusinessServiceImplTest {

    @Mock
    private BusinessRepository businessRepository;

    @InjectMocks
    private BusinessServiceImpl businessService;

    private Business business;
    private BusinessRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        business = Business.builder()
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
    @DisplayName("Should create a business successfully")
    void shouldCreateBusinessSuccessfully() {
        when(businessRepository.save(any(Business.class))).thenReturn(business);

        BusinessResponseDTO response = businessService.create(requestDTO);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("TechNova");
        assertThat(response.getOwnerName()).isEqualTo("Matheus Perger");
        assertThat(response.getSegment()).isEqualTo(Segment.TECHNOLOGY);
        assertThat(response.getStatus()).isEqualTo(BusinessStatus.ACTIVE);

        verify(businessRepository, times(1)).save(any(Business.class));
    }

    @Test
    @DisplayName("Should find all businesses without filters")
    void shouldFindAllBusinessesWithoutFilters() {
        when(businessRepository.findAll(any(Specification.class))).thenReturn(List.of(business));

        List<BusinessResponseDTO> response = businessService.findAll(null, null);

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getName()).isEqualTo("TechNova");

        verify(businessRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should find businesses filtered by status")
    void shouldFindBusinessesByStatus() {
        when(businessRepository.findAll(any(Specification.class))).thenReturn(List.of(business));

        List<BusinessResponseDTO> response = businessService.findAll(BusinessStatus.ACTIVE, null);

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getStatus()).isEqualTo(BusinessStatus.ACTIVE);

        verify(businessRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should find businesses filtered by segment")
    void shouldFindBusinessesBySegment() {
        when(businessRepository.findAll(any(Specification.class))).thenReturn(List.of(business));

        List<BusinessResponseDTO> response = businessService.findAll(null, Segment.TECHNOLOGY);

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getSegment()).isEqualTo(Segment.TECHNOLOGY);

        verify(businessRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should find businesses filtered by status and segment")
    void shouldFindBusinessesByStatusAndSegment() {
        when(businessRepository.findAll(any(Specification.class))).thenReturn(List.of(business));

        List<BusinessResponseDTO> response = businessService.findAll(BusinessStatus.ACTIVE, Segment.TECHNOLOGY);

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getStatus()).isEqualTo(BusinessStatus.ACTIVE);
        assertThat(response.get(0).getSegment()).isEqualTo(Segment.TECHNOLOGY);

        verify(businessRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should find a business by ID successfully")
    void shouldFindBusinessByIdSuccessfully() {
        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));

        BusinessResponseDTO response = businessService.findById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("TechNova");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when business not found by ID")
    void shouldThrowExceptionWhenBusinessNotFoundById() {
        when(businessRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> businessService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Business not found with id: 99");
    }

    @Test
    @DisplayName("Should update a business successfully")
    void shouldUpdateBusinessSuccessfully() {
        BusinessRequestDTO updateRequest = BusinessRequestDTO.builder()
                .name("TechNova Updated")
                .ownerName("Matheus Perger")
                .city("Florianópolis")
                .segment(Segment.TECHNOLOGY)
                .contact("matheus@technova.com.br")
                .status(BusinessStatus.INACTIVE)
                .description("Updated description")
                .build();

        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));
        when(businessRepository.save(any(Business.class))).thenReturn(business);

        BusinessResponseDTO response = businessService.update(1L, updateRequest);

        assertThat(response).isNotNull();
        verify(businessRepository, times(1)).save(any(Business.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existent business")
    void shouldThrowExceptionWhenUpdatingNonExistentBusiness() {
        when(businessRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> businessService.update(99L, requestDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Business not found with id: 99");
    }

    @Test
    @DisplayName("Should delete a business successfully")
    void shouldDeleteBusinessSuccessfully() {
        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));
        doNothing().when(businessRepository).deleteById(1L);

        assertThatCode(() -> businessService.delete(1L)).doesNotThrowAnyException();

        verify(businessRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existent business")
    void shouldThrowExceptionWhenDeletingNonExistentBusiness() {
        when(businessRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> businessService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Business not found with id: 99");
    }
}
