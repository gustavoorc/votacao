package com.br.votacao.core.mapper;

import org.mapstruct.Mapper;
import org.springframework.util.ObjectUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public class LocalTimeStringMapper {

    public String asString(LocalTime hora) {
        return !ObjectUtils.isEmpty(hora) ? DateTimeFormatter.ofPattern("H:mm").format(hora) : null;
    }

    public LocalTime asDate(String hora) {
        return !ObjectUtils.isEmpty(hora) ? LocalTime.parse(hora, DateTimeFormatter.ofPattern("H:mm")) : null;
    }
}
