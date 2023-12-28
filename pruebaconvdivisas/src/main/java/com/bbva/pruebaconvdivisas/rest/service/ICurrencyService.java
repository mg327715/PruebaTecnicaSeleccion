package com.bbva.pruebaconvdivisas.rest.service;

import com.bbva.pruebaconvdivisas.rest.dto.CurrencyRequestDto;
import com.bbva.pruebaconvdivisas.rest.dto.CurrencyResponseDto;

public interface ICurrencyService {
    CurrencyResponseDto getConversionDivisas(CurrencyRequestDto currencyRequestDto);
}
