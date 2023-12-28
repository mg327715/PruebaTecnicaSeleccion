package com.bbva.pruebaconvdivisas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponseDto {
    private LocalDate fechaConversion;
    private BigDecimal tasaConversion;
    private String montoOriginal;
    private String montoResultado;
}
