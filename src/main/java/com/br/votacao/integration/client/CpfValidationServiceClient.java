package com.br.votacao.integration.client;

import com.br.votacao.shared.enums.VotingResultEnum;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CpfValidationServiceClient {

    private Random random = new Random();

    public Object validateCpf(String cpf) {

        boolean canVote = random.nextBoolean();
        return canVote ? VotingResultEnum.ABLE_TO_VOTE : VotingResultEnum.UNABLE_TO_VOTE;
    }
}
