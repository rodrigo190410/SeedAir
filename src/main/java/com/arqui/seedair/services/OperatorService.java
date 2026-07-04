package com.arqui.seedair.services;

import com.arqui.seedair.dtos.DroneAvailableDTO;
import com.arqui.seedair.dtos.OperatorAvailableDTO;
import com.arqui.seedair.dtos.OperatorRegisterDTO;
import com.arqui.seedair.dtos.OperatorResponseDTO;
import com.arqui.seedair.entities.Operator;

import java.util.List;

public interface OperatorService {
    public Operator add(Operator operator);
    public Operator findById(Long id);
    public OperatorRegisterDTO register(OperatorRegisterDTO operatorRegister);
    public List<Operator> listAll();
    public List<OperatorResponseDTO> listOperators();
    public List<OperatorAvailableDTO> getAvailableOperators();
    public Operator update(Operator operator);
}
