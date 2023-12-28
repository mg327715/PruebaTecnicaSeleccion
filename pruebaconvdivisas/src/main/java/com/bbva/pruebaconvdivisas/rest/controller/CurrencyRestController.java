package com.bbva.pruebaconvdivisas.rest.controller;

import com.bbva.pruebaconvdivisas.rest.dto.CurrencyRequestDto;
import com.bbva.pruebaconvdivisas.rest.dto.CurrencyResponseDto;
import com.bbva.pruebaconvdivisas.rest.exception.BadRequestException;
import com.bbva.pruebaconvdivisas.rest.exception.CurrencyNotFoundException;
import com.bbva.pruebaconvdivisas.rest.service.ICurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/divisa")
public class CurrencyRestController {

    @Autowired
    ICurrencyService iCurrencyService;

    @GetMapping("/conversion-divisas")

    public ResponseEntity<?> getConversionDivisas(@Valid @RequestBody CurrencyRequestDto currencyRequestDto,
                                                  BindingResult bindingResult) {
        ResponseEntity<?> resp = null;
        try{
            if (bindingResult.hasErrors()){
                List<String> messages = bindingResult.getFieldErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
                throw new BadRequestException(
                        new StringBuffer()
                                .append("Error: " + messages)
                                .toString());
            }

            CurrencyResponseDto currencyResponseDto = iCurrencyService.getConversionDivisas(currencyRequestDto);
            resp= new ResponseEntity<CurrencyResponseDto>(currencyResponseDto, HttpStatus.OK);
        }catch (BadRequestException bre) {
            throw bre;
        }catch (CurrencyNotFoundException nfe) {
            throw nfe;
        }catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>("No se pudo obtener la conversion de divisas", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }
}
