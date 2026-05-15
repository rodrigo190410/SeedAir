package com.arqui.seedair.services;

import com.arqui.seedair.dtos.OperatorRegisterDTO;
import com.arqui.seedair.entities.Operator;

public interface OperatorService {
    public Operator add(Operator operator);
    public Operator findById(Long id);
    public OperatorRegisterDTO register(OperatorRegisterDTO operatorRegister);
}
