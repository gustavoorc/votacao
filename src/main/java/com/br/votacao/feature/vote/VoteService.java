package com.br.votacao.feature.vote;

import com.br.votacao.core.exception.VotacaoException;
import com.br.votacao.feature.topic.TopicRepository;
import com.br.votacao.integration.client.CpfClient;
import com.br.votacao.shared.persistence.enums.VoteEnum;
import com.br.votacao.shared.persistence.model.Topic;
import com.br.votacao.shared.persistence.model.Vote;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {
    private  final VoteRepository  voteRepository;

    private  final TopicRepository topicRepository;

    private final CpfClient client;





    @Transactional
    public void registerVote(Long idTopic, Vote voteEntity){

        client.validateCPF(voteEntity.getCpfAssociate());

        voteRepository.findByAssociateIdAndTopicId(voteEntity.getAssociateId(), idTopic)
                .ifPresent(e -> {throw  new VotacaoException("Associado já votou nesta pauta.");});

        Topic topic = topicRepository.findById(idTopic).orElseThrow(() -> new VotacaoException("Pauta não encontrada"));

        if (VoteEnum.YES.equals(voteEntity.getVoteValue())) {
            topic.setYesVotes(topic.getYesVotes() + 1);
        } else {
            topic.setNoVotes(topic.getNoVotes() + 1);
        }

        topic.setTotalVotes(topic.getTotalVotes() + 1);
        voteEntity.setTopic(topic);


        voteRepository.save(voteEntity);
        topicRepository.save(topic);
        log.info("voto registrado com sucesso, associado {}", voteEntity.getAssociateId());


    }


}


