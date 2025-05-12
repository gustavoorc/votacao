package com.br.votacao.feature.topic.mapper;

import com.br.votacao.core.BaseMapper;
import com.br.votacao.feature.topic.record.TopicResponseRecord;
import com.br.votacao.shared.persistence.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TopicResponseMapper extends BaseMapper<Topic, TopicResponseRecord> {
}
