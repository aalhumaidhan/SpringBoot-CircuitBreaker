package com.example.CircuitBreaker.controller;

import com.example.CircuitBreaker.bo.AccountResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
@CircuitBreaker(name = "accountMS", fallbackMethod = "fallbackMethod")
public class AccountController {
    public final RestTemplate restTemplate;

    private static final String STOCK_API = "http://localhost:8080/api/account";

    public AccountController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/account")
    public AccountResponse showStocks() {
        ResponseEntity<AccountResponse> responseEntity = restTemplate.getForEntity(STOCK_API, AccountResponse.class);
        return responseEntity.getBody();
    }

    public AccountResponse fallbackMethod(Throwable throwable) {
        AccountResponse response = new AccountResponse();
        response.setStatus("Created Fallback Method");
        return response;
    }


}
