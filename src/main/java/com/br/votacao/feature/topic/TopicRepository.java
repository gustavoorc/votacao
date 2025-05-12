package com.br.votacao.feature.topic;

import com.br.votacao.shared.persistence.enums.StatusEnum;
import com.br.votacao.shared.persistence.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByTitleIgnoreCaseAndStatus(String title, StatusEnum status);
}
