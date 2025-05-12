package com.br.votacao.core.exception;

public class VotacaoException extends RuntimeException {

    private final String mensage;

    public VotacaoException(String mensage){
        super(mensage);
        this.mensage = mensage;
    }




}
