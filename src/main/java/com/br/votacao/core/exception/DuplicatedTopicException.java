package com.br.votacao.core.exception;


public class DuplicatedTopicException extends VotacaoException{

    public DuplicatedTopicException(String mensage, String title){
        super(mensage);
    }


}
