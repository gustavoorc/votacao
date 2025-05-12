package com.br.votacao.feature.vote;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.br.votacao.core.exception.VotacaoException;
import com.br.votacao.feature.topic.TopicRepository;
import com.br.votacao.integration.client.CpfClient;
import com.br.votacao.shared.persistence.enums.VoteEnum;
import com.br.votacao.shared.persistence.model.Topic;
import com.br.votacao.shared.persistence.model.Vote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

public class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private CpfClient cpfClient;

    @InjectMocks
    private VoteService voteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerVote_ShouldSaveVoteAndUpdateTopicYesVotes() {
        Long topicId = 1L;
        Vote vote = new Vote();
        vote.setAssociateId("123L");
        vote.setCpfAssociate("12312312312");
        vote.setVoteValue(VoteEnum.YES);

        Topic topic = new Topic();
        topic.setId(topicId);
        topic.setYesVotes(5);
        topic.setNoVotes(2);
        topic.setTotalVotes(7);

        when(voteRepository.findByAssociateIdAndTopicId(any(), anyLong()))
                .thenReturn(Optional.empty());
        when(topicRepository.findById(anyLong()))
                .thenReturn(Optional.of(topic));
        when(voteRepository.save(any(Vote.class))).thenReturn(vote);
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        voteService.registerVote(topicId, vote);

        verify(voteRepository).save(any(Vote.class));
        verify(topicRepository).save(any(Topic.class));
        assertEquals(6, topic.getYesVotes());
    }

    @Test
    public void registerVote_ShouldThrowWhenAssociateAlreadyVoted() {
        Long topicId = 1L;
        Vote vote = new Vote();
        vote.setAssociateId("123");
        vote.setVoteValue(VoteEnum.YES);

        Vote existingVote = new Vote();
        existingVote.setAssociateId("123");

        when(voteRepository.findByAssociateIdAndTopicId(any(), anyLong()))
                .thenReturn(Optional.of(existingVote));

        assertThrows(VotacaoException.class, () -> {
            voteService.registerVote(topicId, vote);
        });
    }

    @Test
    public void registerVote_ShouldThrowWhenTopicNotFound() {
        Long topicId = 1L;
        Vote vote = new Vote();
        vote.setAssociateId("123");
        vote.setVoteValue(VoteEnum.YES);

        when(voteRepository.findByAssociateIdAndTopicId(any(), anyLong()))
                .thenReturn(Optional.empty());
        when(topicRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(VotacaoException.class, () -> {
            voteService.registerVote(topicId, vote);
        });
    }
}

