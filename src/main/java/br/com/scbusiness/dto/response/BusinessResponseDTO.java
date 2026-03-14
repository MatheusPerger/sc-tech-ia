package br.com.scbusiness.dto.response;

import br.com.scbusiness.enums.BusinessStatus;
import br.com.scbusiness.enums.Segment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessResponseDTO {

    private Long id;
    private String name;
    private String ownerName;
    private String city;
    private Segment segment;
    private String contact;
    private BusinessStatus status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
