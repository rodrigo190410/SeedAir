package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.OperatorRegisterDTO;
import com.arqui.seedair.entities.Operator;
import com.arqui.seedair.exceptions.IncompleteDataException;
import com.arqui.seedair.exceptions.InvalidDataRangeException;
import com.arqui.seedair.exceptions.KeyRepeatedDataExeception;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.OperatorRepository;
import com.arqui.seedair.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return operatorRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("El operator con ID " + id + " no existe."));
    }

    @Override
    public OperatorRegisterDTO register(OperatorRegisterDTO operatorRegister) {
        List<Operator> operatorList = listAll();
        if(operatorRegister.getLicenseCode().isBlank()){
            throw new IncompleteDataException("El código de la licencia del operador es obligatorio");
        }
        for (Operator o: operatorList){
            if (operatorRegister.getLicenseCode().equals(o.getLicenseCode())){
                throw new KeyRepeatedDataExeception("El código de la licencia: "+operatorRegister.getLicenseCode()+" ya está registrado");
            }
        }
        if (operatorRegister.getCertificationLevel().isBlank()){
            throw new IncompleteDataException("El nivel de certificación es obligatorio");
        }
        if (operatorRegister.getExperienceYears() < 0){
            throw new InvalidDataRangeException("Los años de experiencia no pueden ser negativos");
        }

        Operator newOperator =  new Operator(
                null, operatorRegister.getLicenseCode(),
                operatorRegister.getCertificationLevel(),
                operatorRegister.getExperienceYears(), true,
                null
        );
        operatorRepository.save(newOperator);
        return operatorRegister;
    }

    @Override
    public List<Operator> listAll() {
        return operatorRepository.findAll();
    }
}
