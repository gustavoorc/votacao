package com.br.votacao.integration.client;

import com.br.votacao.shared.enums.VotingResultEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/validationCpf")
@RequiredArgsConstructor
public class CpfValidatorController {

    private final CpfValidationServiceClient cpfValidationServiceClient;

    @GetMapping("/{cpf}")
    public ResponseEntity<Object> checkCpf(@PathVariable String cpf){
        Object result = cpfValidationServiceClient.validateCpf(cpf);

        if(Objects.nonNull(result)){
            if(result.equals(VotingResultEnum.UNABLE_TO_VOTE)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }

        }
        return ResponseEntity.ok(result);
    }

}
