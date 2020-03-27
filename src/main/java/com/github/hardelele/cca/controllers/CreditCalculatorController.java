package com.github.hardelele.cca.controllers;

import com.github.hardelele.cca.forms.CreditForm;
import com.github.hardelele.cca.services.CreditCalculatorService;
import com.github.hardelele.cca.transfers.CreditPayout;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
public class CreditCalculatorController {


    private final CreditCalculatorService creditCalculatorService;

    public CreditCalculatorController(CreditCalculatorService creditCalculatorService) {
        this.creditCalculatorService = creditCalculatorService;
    }

    @PostMapping("/")
    public List<CreditPayout> getCreditPayouts(@RequestBody CreditForm creditForm) {
        return creditCalculatorService.getCreditPayouts(creditForm);
    }
}
