package com.github.hardelele.cca.controllers;

import com.github.hardelele.cca.models.CreditParameters;
import com.github.hardelele.cca.services.CreditParametersService;
import com.github.hardelele.cca.transfers.CreditParametersTransfer;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/params")
public class CreditParametersController {

    private final Mapper mapper;

    private final CreditParametersService creditParametersService;

    @Autowired
    public CreditParametersController(Mapper mapper, CreditParametersService creditParametersService) {
        this.mapper = mapper;
        this.creditParametersService = creditParametersService;
    }

    @PostMapping("/")
    public CreditParametersTransfer updateCreditParameters(@RequestBody CreditParametersTransfer creditParametersDto) {
        CreditParameters creditParameters = creditParametersService.updateCreditParameters(creditParametersDto);
        return mapper.map(creditParameters, CreditParametersTransfer.class);
    }
}
