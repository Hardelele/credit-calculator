package com.github.hardelele.cca.controllers;

import com.github.hardelele.cca.models.entities.CreditParameters;
import com.github.hardelele.cca.services.CreditParametersService;
import com.github.hardelele.cca.models.transfers.CreditParametersTransfer;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parameters")
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

    @GetMapping("/")
    public List<CreditParametersTransfer> getCreditParameters() {
        return creditParametersService.getCreditParameters().stream()
                .map(creditParameters -> mapper.map(creditParameters, CreditParametersTransfer.class))
                .collect(Collectors.toList());
    }
}
