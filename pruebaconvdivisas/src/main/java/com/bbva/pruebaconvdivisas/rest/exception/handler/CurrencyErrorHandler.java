package com.bbva.pruebaconvdivisas.rest.exception.handler;

import com.bbva.pruebaconvdivisas.rest.dto.ErrorTypeDto;
import com.bbva.pruebaconvdivisas.rest.exception.BadRequestException;
import com.bbva.pruebaconvdivisas.rest.exception.CurrencyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

//@ControllerAdvice
@RestControllerAdvice
public class CurrencyErrorHandler {
    /**
     * @param nfe
     * @return ResponseEntity
     */
    //@ResponseBody
    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<ErrorTypeDto> handleNotFound(CurrencyNotFoundException nfe){
        return new ResponseEntity<ErrorTypeDto>(
                new ErrorTypeDto(
                        new Date(System.currentTimeMillis()).toString(),
                        "404 - NOT FOUND",
                        nfe.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    /**
     * @param bre
     * @return ResponseEntity
     */
    //@ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorTypeDto> handleBadRequest(BadRequestException bre){
        return new ResponseEntity<ErrorTypeDto>(
                new ErrorTypeDto(
                        new Date(System.currentTimeMillis()).toString(),
                        "400 - BAD REQUEST",
                        bre.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
