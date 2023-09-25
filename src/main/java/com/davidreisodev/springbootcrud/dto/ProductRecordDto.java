package com.davidreisodev.springbootcrud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;   

import java.math.BigDecimal;

public record ProductRecordDto(@NotBlank String productName, @NotNull BigDecimal productValue, String producDescription) {
    
}
