package com.br.votacao.feature.topic;


import com.br.votacao.core.exception.DuplicatedTopicException;
import com.br.votacao.core.exception.VotacaoException;
import com.br.votacao.feature.topic.mapper.TopicResponseMapper;
import com.br.votacao.feature.topic.record.TopicResponseRecord;
import com.br.votacao.shared.persistence.enums.StatusEnum;
import com.br.votacao.shared.persistence.model.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TopicService {

    private final TopicRepository topicRepository;


    private final TopicResponseMapper topicResponseMapper;



    public TopicResponseRecord createTopic(Topic topicEntity){

        if (isDuplicateClosedProposal(topicEntity.getTitle()))
              throw  new DuplicatedTopicException("já existe uma proposta em andamento com o mesmo titulo - {}", topicEntity.getTitle());

        topicRepository.save(topicEntity);
        log.info("Pauta criada com sucesso!");
        return topicResponseMapper.toRecord(topicEntity);
    }

    public void openVotingSession(Long idTopic, Integer durationMinutes) {
        Topic topic = topicRepository.findById(idTopic).orElseThrow(() -> new VotacaoException("Pauta não encontrada"));
        topic.setStatus(StatusEnum.OPEN);
        topic.setStartVotation(LocalDateTime.now());
        topic.setEndVotation(LocalDateTime.now().plusMinutes(durationMinutes == null ? 1 : durationMinutes));
        topicRepository.save(topic);
        log.info("Sessao de votação aberta para a pauta {}", idTopic);
    }


    public TopicResponseRecord closeVotingAndGetResult(Long idTopic) {
        Topic topic = topicRepository.findById(idTopic).orElseThrow(() -> new VotacaoException("Pauta não encontrada"));

        if (LocalDateTime.now().isBefore(topic.getEndVotation())) {
            throw new IllegalStateException("Votacao em andamento");
        }

        topic.setStatus(StatusEnum.CLOSED);
        String result = topic.getYesVotes() > topic.getNoVotes() ? "APPROVED" : "REJECTED";
        topic.setResult(result);

        topicRepository.save(topic);
        log.info("Sessao de votaçao encerrada para a pauta {}. Resultado: {}", idTopic, result);
        return topicResponseMapper.toRecord(topic);
    }

    private boolean isDuplicateClosedProposal(String title) {
        return topicRepository
                .findByTitleIgnoreCaseAndStatus(title, StatusEnum.CLOSED)
                .stream()
                .anyMatch(p -> !"Pending".equalsIgnoreCase(p.getResult()));
    }
}
