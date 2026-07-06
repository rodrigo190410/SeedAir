package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.OperatorAvailableDTO;
import com.arqui.seedair.dtos.OperatorRegisterDTO;
import com.arqui.seedair.dtos.OperatorResponseDTO;
import com.arqui.seedair.entities.Operator;
import com.arqui.seedair.exceptions.IncompleteDataException;
import com.arqui.seedair.exceptions.InvalidDataRangeException;
import com.arqui.seedair.exceptions.KeyRepeatedDataExeception;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.OperatorRepository;
import com.arqui.seedair.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        for (Operator o : operatorList) {
            if (operatorRegister.getLicenseCode().equals(o.getLicenseCode())) {
                throw new KeyRepeatedDataExeception("El código de la licencia: " + operatorRegister.getLicenseCode() +
                        " ya está registrado");
            }
            String cleanNewName = sanitize(operatorRegister.getName());
            String cleanOldName = sanitize(o.getName());
            String cleanNewLastname = sanitize(operatorRegister.getLastname());
            String cleanOldLastname = sanitize(o.getLastname());
            if (cleanNewName.equals(cleanOldName) && cleanNewLastname.equals(cleanOldLastname)) {
                throw new KeyRepeatedDataExeception("El operador " + operatorRegister.getName() + " " + operatorRegister.getLastname() + " ya se encuentra registrado");
            }
        }
        if (operatorRegister.getCertificationLevel().isBlank()){
            throw new IncompleteDataException("El nivel de certificación es obligatorio");
        }
        if (operatorRegister.getName().isBlank()){
            throw new IncompleteDataException("El nombre es obligatorio");
        }
        if (operatorRegister.getLastname().isBlank()){
            throw new IncompleteDataException("El apellido es obligatorio");
        }
        if (operatorRegister.getExperienceYears() < 0){
            throw new InvalidDataRangeException("Los años de experiencia no pueden ser negativos");
        }

        Operator newOperator =  new Operator(
                null, operatorRegister.getLicenseCode(),
                operatorRegister.getCertificationLevel(),operatorRegister.getName(),
                operatorRegister.getLastname(),
                operatorRegister.getExperienceYears(), true,true,
                null
        );
        operatorRepository.save(newOperator);
        return operatorRegister;
    }

    @Override
    public List<Operator> listAll() {
        return operatorRepository.findAll();
    }

    @Override
    public List<OperatorResponseDTO> listOperators() {
        List<Operator> operators = listAll();
        List<OperatorResponseDTO> newList = new ArrayList<>();

        for (Operator o : operators){
            OperatorResponseDTO dto = new OperatorResponseDTO(
                    o.getId(),o.getName(), o.getLastname(),
                    o.getLicenseCode(),o.getCertificationLevel(),
                    o.getExperienceYears(),o.getAvailabilityStatus(),
                    o.getIsActive()
            );
            newList.add(dto);
        }

        return newList;
    }


    @Override
    public List<OperatorAvailableDTO> getAvailableOperators() {
        return operatorRepository.findAvailableDronesForClient();
    }

    @Override
    public OperatorResponseDTO update(OperatorResponseDTO operator) {
        if (operator.getId() == null){
            throw new ResourceNotFoundException("El id es obligatorio");
        }
        List<Operator> operatorList = listAll();
        for (Operator o : operatorList) {
            if (!o.getId().equals(operator.getId())) {
                if (operator.getLicenseCode().equals(o.getLicenseCode())) {
                    throw new KeyRepeatedDataExeception("El código de la licencia: " + operator.getLicenseCode()
                            + " ya pertenece a otro operador");
                }
                String cleanNewName = sanitize(operator.getName());
                String cleanOldName = sanitize(o.getName());
                String cleanNewLastname = sanitize(operator.getLastname());
                String cleanOldLastname = sanitize(o.getLastname());
                if (cleanNewName.equals(cleanOldName) && cleanNewLastname.equals(cleanOldLastname)) {
                    throw new KeyRepeatedDataExeception("El operador " + operator.getName() + " " + operator.getLastname()
                            + " ya se encuentra registrado en el sistema");
                }
            }
        }
        Operator foundOperator = findById(operator.getId());
        foundOperator.setName(operator.getName());
        foundOperator.setLastname(operator.getLastname());
        foundOperator.setLicenseCode(operator.getLicenseCode());
        foundOperator.setCertificationLevel(operator.getCertificationLevel());
        foundOperator.setExperienceYears(operator.getExperienceYears());
        foundOperator.setAvailabilityStatus(operator.getAvailabilityStatus());
        foundOperator.setIsActive(operator.getIsActive());

        operatorRepository.save(foundOperator);

        return new OperatorResponseDTO(
                foundOperator.getId(), foundOperator.getName(),
                foundOperator.getLastname(), foundOperator.getLicenseCode(),
                foundOperator.getCertificationLevel(), foundOperator.getExperienceYears(),
                foundOperator.getAvailabilityStatus(), foundOperator.getIsActive()
        );
    }

    @Override
    public void delete(Long id) {
        operatorRepository.deleteById(id);
    }

    private String sanitize(String text) {
        if (text == null) return "";
        return text.toLowerCase().trim()
                .replaceAll("[áàäâã]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöôõ]", "o")
                .replaceAll("[úùüû]", "u")
                .replaceAll("[ñ]", "n")
                .replaceAll("[ç]", "c")
                .replaceAll("[^a-z]", "");
    }
}
