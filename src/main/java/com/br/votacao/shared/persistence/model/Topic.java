package com.br.votacao.shared.persistence.model;

import com.br.votacao.shared.persistence.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 200)
    private String description;
    private String creator;
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.PENDING;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime startVotation;
    private LocalDateTime endVotation;
    private Integer yesVotes = 0;
    private Integer noVotes = 0;
    private Integer totalVotes = 0;
    private String result = "Pending";
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Vote> votes;
}
