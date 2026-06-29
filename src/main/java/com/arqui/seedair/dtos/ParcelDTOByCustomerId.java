package com.arqui.seedair.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelDTOByCustomerId {
    private Long id;
    private String locationText;
    private Double totalHectares;
    private Double latitude;
    private Double longitude;
    private LocalDate createdAt;
    private Boolean isActive;
}
