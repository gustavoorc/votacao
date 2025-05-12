package com.br.votacao.feature.vote.record;

import com.br.votacao.shared.persistence.enums.VoteEnum;

public record VoteRecord(
         String  associateId,
         VoteEnum voteValue
) {
}
