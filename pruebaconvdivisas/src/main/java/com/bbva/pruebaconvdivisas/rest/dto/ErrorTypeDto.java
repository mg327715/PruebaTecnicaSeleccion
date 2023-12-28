package com.bbva.pruebaconvdivisas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorTypeDto {
    private String time;
    private String status;
    private String message;
}
