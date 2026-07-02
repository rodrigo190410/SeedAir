package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.DroneAvailableDTO;
import com.arqui.seedair.dtos.OperatorAvailableDTO;
import com.arqui.seedair.dtos.OperatorRegisterDTO;
import com.arqui.seedair.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/seedair") // http://localhost:8080/seedair
public class OperatorController {
    @Autowired
    OperatorService operatorService;
    @PostMapping("/operators/register") // http://localhost:8080/seedair/operators/register
    public ResponseEntity<OperatorRegisterDTO> register(@RequestBody OperatorRegisterDTO operatorRegister){
        OperatorRegisterDTO newOperator = operatorService.register(operatorRegister);
        return new ResponseEntity<>(newOperator, HttpStatus.CREATED);
    }

    @GetMapping("/operators/available") // http://localhost:8080/seedair/operators/available
    public ResponseEntity<List<OperatorAvailableDTO>> getAvailableOperators() {
        List<OperatorAvailableDTO> operators = operatorService.getAvailableOperators();
        return new ResponseEntity<>(operators, HttpStatus.OK);
    }
}
