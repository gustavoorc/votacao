package com.br.votacao.feature.topic.record;

import java.time.LocalDateTime;

public record TopicRequestRecord(
         String title,
         String description,
         String creator,
         LocalDateTime votingStart,
         LocalDateTime votingEnd
) {
}
