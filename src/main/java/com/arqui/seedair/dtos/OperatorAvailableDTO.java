package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorAvailableDTO {
    private Long id;
    private String licenseCode;
    private String certificationLevel;
    private Integer experienceYears;
}
