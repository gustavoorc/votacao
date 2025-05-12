package com.br.votacao.feature.vote;

import com.br.votacao.shared.persistence.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByAssociateIdAndTopicId(String associateId, Long proposalId);
}
