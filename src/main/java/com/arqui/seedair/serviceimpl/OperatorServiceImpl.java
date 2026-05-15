package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.OperatorRegisterDTO;
import com.arqui.seedair.entities.Operator;
import com.arqui.seedair.repositories.OperatorRepository;
import com.arqui.seedair.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorServiceImpl implements OperatorService {
    @Autowired
    OperatorRepository operatorRepository;
    @Override
    public Operator add(Operator operator) {
        return operatorRepository.save(operator);
    }

    @Override
    public Operator findById(Long id) {
        return operatorRepository.findById(id).get();
    }

    @Override
    public OperatorRegisterDTO register(OperatorRegisterDTO operatorRegister) {

        Operator newOperator =  new Operator(
                null, operatorRegister.getLicenseCode(),
                operatorRegister.getCertificationLevel(),
                operatorRegister.getExperienceYears(), true,
                null
        );
        operatorRepository.save(newOperator);
        return operatorRegister;
    }
}
