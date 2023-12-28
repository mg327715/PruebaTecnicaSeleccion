package com.bbva.pruebaconvdivisas.rest.integration.rest.client.service.impl;

import com.bbva.pruebaconvdivisas.rest.dto.CurrencyExternalResponseDto;
import com.bbva.pruebaconvdivisas.rest.integration.rest.client.service.ExtCurrencyRestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class ExtCurrencyRestClientServiceImpl implements ExtCurrencyRestClientService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public CurrencyExternalResponseDto getExternalDivisa(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<CurrencyExternalResponseDto> responseEntity = restTemplate.exchange("http://api.exchangeratesapi.io/v1/latest?access_key=cb04a89f0eab8bb575f8290db749d965", HttpMethod.GET, entity, CurrencyExternalResponseDto.class);
        CurrencyExternalResponseDto res = responseEntity.getBody();
        System.out.println(res.toString());
        return res;
    }
}
