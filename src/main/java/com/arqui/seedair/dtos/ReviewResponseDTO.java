package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {
    private Double rating;
    private String comment;
    private Boolean isVisible;
    private LocalDate createdAt;
    private Long reservationId;
}
