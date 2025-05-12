package com.br.votacao.core;

import java.util.Collection;

public interface BaseMapper<E, D> {

    D toRecord(E entity);

    E toEntity(D dto);

    Collection<D> toRecord(Collection<E> entities);

    Collection<E> toEntity(Collection<D> dtos);

}
