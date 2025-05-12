package com.br.votacao.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cpf-cliente", url = "http://localhost:8080")
public interface CpfClient {

    @GetMapping("/validationCpf/{cpf}")
    Object validateCPF(@PathVariable("cpf") String cpf);
}
