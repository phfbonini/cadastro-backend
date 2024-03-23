package com.example.cadastrobackend.pessoa;

import java.util.Date;

public record PessoaResponseDTO(Long cd_pessoa, String nm_pessoa, String cpf, Date data_nascimento) {
    public PessoaResponseDTO(Pessoa pessoa){
        this(pessoa.getCd_pessoa(), pessoa.getNm_pessoa(), pessoa.getCpf(), pessoa.getData_nascimento());

    }
}