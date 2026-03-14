package br.com.scbusiness.service.impl;

import br.com.scbusiness.dto.request.BusinessRequestDTO;
import br.com.scbusiness.dto.response.BusinessResponseDTO;
import br.com.scbusiness.enums.BusinessStatus;
import br.com.scbusiness.enums.Segment;
import br.com.scbusiness.exception.ResourceNotFoundException;
import br.com.scbusiness.model.Business;
import br.com.scbusiness.repository.BusinessRepository;
import br.com.scbusiness.repository.spec.BusinessSpecification;
import br.com.scbusiness.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;

    @Override
    public BusinessResponseDTO create(BusinessRequestDTO request) {
        Business business = Business.builder()
                .name(request.getName())
                .ownerName(request.getOwnerName())
                .city(request.getCity())
                .segment(request.getSegment())
                .contact(request.getContact())
                .status(request.getStatus())
                .description(request.getDescription())
                .build();

        return toResponseDTO(businessRepository.save(business));
    }

    @Override
    public List<BusinessResponseDTO> findAll(BusinessStatus status, Segment segment) {
        Specification<Business> spec = Specification
                .where(BusinessSpecification.hasStatus(status))
                .and(BusinessSpecification.hasSegment(segment));

        return businessRepository.findAll(spec)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public BusinessResponseDTO findById(Long id) {
        return toResponseDTO(getBusinessOrThrow(id));
    }

    @Override
    public BusinessResponseDTO update(Long id, BusinessRequestDTO request) {
        Business business = getBusinessOrThrow(id);

        business.setName(request.getName());
        business.setOwnerName(request.getOwnerName());
        business.setCity(request.getCity());
        business.setSegment(request.getSegment());
        business.setContact(request.getContact());
        business.setStatus(request.getStatus());
        business.setDescription(request.getDescription());

        return toResponseDTO(businessRepository.save(business));
    }

    @Override
    public void delete(Long id) {
        getBusinessOrThrow(id);
        businessRepository.deleteById(id);
    }

    private Business getBusinessOrThrow(Long id) {
        return businessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found with id: " + id));
    }

    private BusinessResponseDTO toResponseDTO(Business business) {
        return BusinessResponseDTO.builder()
                .id(business.getId())
                .name(business.getName())
                .ownerName(business.getOwnerName())
                .city(business.getCity())
                .segment(business.getSegment())
                .contact(business.getContact())
                .status(business.getStatus())
                .description(business.getDescription())
                .createdAt(business.getCreatedAt())
                .updatedAt(business.getUpdatedAt())
                .build();
    }
}
