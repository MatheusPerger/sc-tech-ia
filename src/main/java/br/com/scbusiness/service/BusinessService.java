package br.com.scbusiness.service;

import br.com.scbusiness.dto.request.BusinessRequestDTO;
import br.com.scbusiness.dto.response.BusinessResponseDTO;
import br.com.scbusiness.enums.BusinessStatus;
import br.com.scbusiness.enums.Segment;

import java.util.List;

public interface BusinessService {

    BusinessResponseDTO create(BusinessRequestDTO request);

    List<BusinessResponseDTO> findAll(BusinessStatus status, Segment segment);

    BusinessResponseDTO findById(Long id);

    BusinessResponseDTO update(Long id, BusinessRequestDTO request);

    void delete(Long id);
}
