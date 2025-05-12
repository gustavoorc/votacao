package com.br.votacao.feature.topic.record;

import com.br.votacao.shared.persistence.enums.StatusEnum;

import java.time.LocalDateTime;

public record TopicResponseRecord(
        Long id,
         String title,
         String description,
         String creator,
         LocalDateTime createdAt,
         LocalDateTime startVotation,
         LocalDateTime endVotation,
         StatusEnum status,
         Integer yesVotes,
         Integer noVotes,
         String result
) {
}
