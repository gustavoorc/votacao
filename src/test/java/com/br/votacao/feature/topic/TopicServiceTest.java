package com.br.votacao.feature.topic;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.br.votacao.core.exception.DuplicatedTopicException;
import com.br.votacao.core.exception.VotacaoException;
import com.br.votacao.feature.topic.TopicService;
import com.br.votacao.feature.topic.mapper.TopicResponseMapper;
import com.br.votacao.feature.topic.record.TopicResponseRecord;
import com.br.votacao.shared.persistence.model.Topic;
import com.br.votacao.shared.persistence.enums.StatusEnum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Spy
    @InjectMocks
    private TopicResponseMapper topicResponseMapper = Mappers.getMapper(TopicResponseMapper.class);

    @InjectMocks
    private TopicService topicService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTopic_ShouldSaveAndReturnRecord() {
        Topic topic = new Topic();
        topic.setTitle("Nova Proposta");
        topic.setDescription("descricao proposta");
        topic.setCreator("jose do teste");
        topic.setStartVotation(LocalDateTime.now());
        topic.setEndVotation(LocalDateTime.now().plusMinutes(5));


        when(topicRepository.save(any(Topic.class))).thenReturn(topic);


        TopicResponseRecord response = topicService.createTopic(topic);

        verify(topicRepository).save(any());
        assertEquals(StatusEnum.PENDING, response.status());
        assertNotNull(response);
    }

    @Test
    void createTopic_ShouldThrow_WhenDuplicateProposalExists() {
        Topic topic = new Topic();
        topic.setTitle("Proposta Existente");

        Topic duplicateTopic = new Topic();
        duplicateTopic.setTitle("Proposta Existente");
        duplicateTopic.setResult("Some result");
        duplicateTopic.setStatus(StatusEnum.CLOSED);

        when(topicRepository.findByTitleIgnoreCaseAndStatus(anyString(), any()))
                .thenReturn(Arrays.asList(duplicateTopic));

        assertThrows(DuplicatedTopicException.class, () -> {
            topicService.createTopic(topic);
        });
    }

    @Test
    void openVotingSession_ShouldSetStatusAndTimes() {
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setStatus(StatusEnum.CLOSED);
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
        when(topicRepository.save(any())).thenReturn(topic);

        topicService.openVotingSession(1L, 5);

        verify(topicRepository).save(any());
        assertEquals(StatusEnum.OPEN, topic.getStatus());
        assertNotNull(topic.getStartVotation());
        assertNotNull(topic.getEndVotation());
    }

    @Test
    void closeVotingAndGetResult_ShouldSetStatusAndResultAproved() {
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setYesVotes(10);
        topic.setNoVotes(5);
        topic.setEndVotation(LocalDateTime.now().minusMinutes(1));
        topic.setStatus(StatusEnum.OPEN);
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
        when(topicRepository.save(any())).thenReturn(topic);

        TopicResponseRecord response = topicService.closeVotingAndGetResult(1L);

        verify(topicRepository).save(any());
        assertEquals(StatusEnum.CLOSED, topic.getStatus());
        assertEquals("APPROVED", response.result());
    }

    @Test
    void closeVotingAndGetResult_ShouldSetStatusAndResultReject() {
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setYesVotes(10);
        topic.setNoVotes(15);
        topic.setEndVotation(LocalDateTime.now().minusMinutes(1));
        topic.setStatus(StatusEnum.OPEN);
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
        when(topicRepository.save(any())).thenReturn(topic);

        TopicResponseRecord response = topicService.closeVotingAndGetResult(1L);

        verify(topicRepository).save(any());
        assertEquals(StatusEnum.CLOSED, topic.getStatus());
        assertEquals("REJECTED", response.result());
    }


}
