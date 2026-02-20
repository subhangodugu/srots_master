package com.srots.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PremiumSubscribeRequestDTO {
    @NotNull
    private Integer months;

    @NotBlank
    private String utr; // \u2b50 IMPORTANT
}
