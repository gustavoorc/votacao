package com.br.votacao.shared.persistence.model;

import com.br.votacao.shared.persistence.enums.VoteEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String associateId;

    @Enumerated(EnumType.STRING)
    private VoteEnum voteValue;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
