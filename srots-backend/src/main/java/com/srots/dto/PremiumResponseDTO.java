package com.srots.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PremiumResponseDTO {
    private String message;
    private LocalDate expiryDate;
}
