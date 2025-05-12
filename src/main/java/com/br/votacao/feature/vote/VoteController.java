package com.br.votacao.feature.vote;


import com.br.votacao.feature.vote.mapper.VoteMapper;
import com.br.votacao.feature.vote.record.VoteRecord;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    private final VoteMapper voteMapper;

    @PostMapping("/register/{idTopic}")
    public ResponseEntity<String> registerVote(@PathVariable Long idTopic, @RequestBody VoteRecord voteRecord){
        voteService.registerVote(idTopic, voteMapper.toEntity(voteRecord));

        return ResponseEntity.ok("voto registrado com sucesso");

    }
}
