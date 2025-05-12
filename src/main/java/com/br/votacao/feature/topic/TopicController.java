package com.br.votacao.feature.topic;

import com.br.votacao.feature.topic.mapper.TopicRequestMapper;
import com.br.votacao.feature.topic.record.TopicRequestRecord;
import com.br.votacao.feature.topic.record.TopicResponseRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public class TopicController {


    private final TopicService topicService;

    private final TopicRequestMapper topicRequestMapper;

    public TopicController(TopicService topicService, TopicRequestMapper topicRequest) {
        this.topicService = topicService;
        this.topicRequestMapper = topicRequest;
    }


    @PostMapping
    public ResponseEntity<TopicResponseRecord> createProposal(@RequestBody TopicRequestRecord request) {
        TopicResponseRecord proposalResponse = topicService.createTopic(topicRequestMapper.toEntity(request));
        return new ResponseEntity<>(proposalResponse, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/open")
    public ResponseEntity<String> openSession(@PathVariable Long id, @RequestParam(required = false) Integer minutes) {
        topicService.openVotingSession(id, minutes);
        return ResponseEntity.ok("Sessao de votacao aberta");
    }



    @GetMapping("/{id}/result")
    public ResponseEntity<TopicResponseRecord> getResult(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.closeVotingAndGetResult(id));
    }

}
