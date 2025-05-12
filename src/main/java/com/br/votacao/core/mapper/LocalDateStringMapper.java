package com.br.votacao.core.mapper;

import org.mapstruct.Mapper;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public class LocalDateStringMapper {

    public String asString(LocalDate data) {
        return !ObjectUtils.isEmpty(data) ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(data) : null;
    }

    public LocalDate asDate(String data) {
        return !ObjectUtils.isEmpty(data) ? LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }
}
