package com.github.hardelele.cca.services;

import com.github.hardelele.cca.models.CreditParameters;
import com.github.hardelele.cca.repositories.CreditParametersRepository;
import com.github.hardelele.cca.transfers.CreditParametersTransfer;
import com.github.hardelele.cca.utils.validators.PercentValidator;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CreditParametersService {

    private final Mapper mapper;

    private final CreditParametersRepository creditParametersRepository;

    @Autowired
    public CreditParametersService(Mapper mapper, CreditParametersRepository creditParametersRepository) {
        this.mapper = mapper;
        this.creditParametersRepository = creditParametersRepository;
    }

    public CreditParameters updateCreditParameters(CreditParametersTransfer creditParametersTransfer) {
        PercentValidator.validate(creditParametersTransfer.getPercent());
        CreditParameters creditParameters = extractCreditParametersToSave(creditParametersTransfer);
        return creditParametersRepository.save(creditParameters);
    }

    private CreditParameters extractCreditParametersToSave(CreditParametersTransfer creditParametersTransfer) {
        CreditParameters creditParametersEntity = mapper.map(creditParametersTransfer, CreditParameters.class);
        List<CreditParameters> creditParametersList = creditParametersRepository.findAll();
        if (creditParametersList.size() > 0) {
            UUID id = creditParametersList.get(0).getId();
            creditParametersEntity.setId(id);
        }
        return creditParametersEntity;
    }
}
