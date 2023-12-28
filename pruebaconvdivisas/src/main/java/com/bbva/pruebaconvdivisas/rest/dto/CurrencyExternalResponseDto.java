package com.bbva.pruebaconvdivisas.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExternalResponseDto {
    private boolean success;
    private Timestamp timestamp;
    private String base;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    private RatesDto rates;
}
