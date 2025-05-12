package com.br.votacao.core.mapper;

import org.mapstruct.Mapper;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public class LocalDateTimeStringMapper {

    public String asString(LocalDateTime data) {
        return !ObjectUtils.isEmpty(data) ? DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(data) : null;
    }

    public LocalDateTime asDate(String data) {
        boolean dataNotIsEmpty = !ObjectUtils.isEmpty(data);
        String horaAtual = String.valueOf(LocalDateTime.now().getHour());
        String minutosAtual = String.valueOf(LocalDateTime.now().getMinute());

        if (dataNotIsEmpty && data.length() == 10) {
            data += " " + (horaAtual.length() == 1 ? "0" + horaAtual : horaAtual) + ":" + (minutosAtual.length() == 1 ? "0" + minutosAtual : minutosAtual);
        }

        return dataNotIsEmpty ? LocalDateTime.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : null;
    }
}
