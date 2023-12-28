package com.bbva.pruebaconvdivisas.rest.service.impl;

import com.bbva.pruebaconvdivisas.rest.dto.CurrencyRequestDto;
import com.bbva.pruebaconvdivisas.rest.dto.RatesDto;
import com.bbva.pruebaconvdivisas.rest.dto.CurrencyResponseDto;
import com.bbva.pruebaconvdivisas.rest.dto.CurrencyExternalResponseDto;
import com.bbva.pruebaconvdivisas.rest.exception.CurrencyNotFoundException;
import com.bbva.pruebaconvdivisas.rest.integration.rest.client.service.ExtCurrencyRestClientService;
import com.bbva.pruebaconvdivisas.rest.service.ICurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements ICurrencyService {
    @Autowired
    ExtCurrencyRestClientService extCurrencyRestClientService;

    private String aMoneda(BigDecimal monto, String moneda){
        return "$ " + monto.setScale(2, BigDecimal.ROUND_HALF_EVEN) + " " + moneda;
    }

    @Override
    public CurrencyResponseDto getConversionDivisas(CurrencyRequestDto currencyRequestDto) {
        CurrencyExternalResponseDto currencyExternalResponseDto = extCurrencyRestClientService.getExternalDivisa();
        RatesDto rate = currencyExternalResponseDto.getRates();
        CurrencyResponseDto currencyResponseDto = new CurrencyResponseDto();
        BigDecimal montoResultado = new BigDecimal("0");
        BigDecimal tasaConversion = new BigDecimal("0");
        Class<?> c = rate.getClass();
        Field[] fields = c.getDeclaredFields();
        Map<String, Object> mapRates = new HashMap<>();
        for( Field field : fields ){
            try {
                mapRates.put(field.getName().toString(), field.get(rate));
            } catch (IllegalArgumentException e1) {

            } catch (IllegalAccessException e1) { }
        }
        if (!mapRates.containsKey(currencyRequestDto.getMonedaOrigen())){
            throw new CurrencyNotFoundException(
                    new StringBuffer()
                            .append("Moneda Origen ")
                            .append(currencyRequestDto.getMonedaOrigen())
                            .append(" not exist")
                            .toString());
        }
        if (!mapRates.containsKey(currencyRequestDto.getMonedaDestino())){
            throw new CurrencyNotFoundException(
                    new StringBuffer()
                            .append("Moneda Destino ")
                            .append(currencyRequestDto.getMonedaDestino())
                            .append(" not exist")
                            .toString());
        }
        if (currencyRequestDto.getMonedaOrigen().equals("EUR")) {
            if (!currencyRequestDto.getMonedaDestino().equals("EUR")) {
                tasaConversion = new BigDecimal((String) mapRates.get(currencyRequestDto.getMonedaDestino()));
                montoResultado = BigDecimal.valueOf(currencyRequestDto.getMonto()).multiply(tasaConversion);
            } else {
                montoResultado = BigDecimal.valueOf(currencyRequestDto.getMonto());
            }
        }
        if (!currencyRequestDto.getMonedaOrigen().equals("EUR")){
            if (!currencyRequestDto.getMonedaDestino().equals("USD")) {
                tasaConversion = new BigDecimal((String) mapRates.get(currencyRequestDto.getMonedaOrigen()));
                montoResultado = BigDecimal.valueOf(currencyRequestDto.getMonto()).divide(tasaConversion);
            } else {
                montoResultado = BigDecimal.valueOf(currencyRequestDto.getMonto());
            }
        }

        currencyResponseDto.setFechaConversion(currencyExternalResponseDto.getDate());
        currencyResponseDto.setTasaConversion(tasaConversion);
        currencyResponseDto.setMontoOriginal(aMoneda(BigDecimal.valueOf(currencyRequestDto.getMonto()), currencyRequestDto.getMonedaOrigen()));
        currencyResponseDto.setMontoResultado(aMoneda(montoResultado, currencyRequestDto.getMonedaDestino()));
        return currencyResponseDto;
    }
}
