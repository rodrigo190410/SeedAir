package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorResponseDTO {
    private Long id;
    private String name;
    private String lastname;
    private String licenseCode;
    private String certificationLevel;
    private Integer experienceYears;
    private Boolean availabilityStatus;
    private Boolean isActive;
}
