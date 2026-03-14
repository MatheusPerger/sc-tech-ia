package br.com.scbusiness.dto.request;

import br.com.scbusiness.enums.BusinessStatus;
import br.com.scbusiness.enums.Segment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessRequestDTO {

    @NotBlank(message = "Business name is required")
    @Size(min = 2, max = 250, message = "Business name must be between 2 and 250 characters")
    private String name;

    @NotBlank(message = "Owner name is required")
    @Size(min = 2, max = 250, message = "Owner name must be between 2 and 250 characters")
    private String ownerName;

    @NotBlank(message = "City is required")
    private String city;

    @NotNull(message = "Segment is required")
    private Segment segment;

    @NotBlank(message = "Contact is required")
    @Size(max = 250, message = "Description must not exceed 250 characters")
    private String contact;

    @NotNull(message = "Status is required")
    private BusinessStatus status;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}
