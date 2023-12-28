package com.bbva.pruebaconvdivisas.rest.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
public class CurrencyRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "El monto no puede ser null")
    @Min(value = 1, message = "El monto no puede ser un valor menor a 1")
    private Long monto;

    @NotBlank(message = "La moneda origen no puede ser null ni vacio")
    private String monedaOrigen;

    @NotBlank(message = "La moneda destino no puede ser null ni vacio")
    private String monedaDestino;
}
