package com.example.cadastrobackend.pessoa;

import com.example.cadastrobackend.contato.Contato;
import com.example.cadastrobackend.contato.ContatoResponseDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record PessoaResponseDTO(
        Long cd_pessoa,
        String nm_pessoa,
        String cpf,
        Date data_nascimento,
        List<ContatoResponseDTO> contatos
) {
    public PessoaResponseDTO(Pessoa pessoa) {
        this(pessoa.getCd_pessoa(), pessoa.getNm_pessoa(), pessoa.getCpf(), pessoa.getData_nascimento(),
                pessoa.getContatos().stream().map(ContatoResponseDTO::new).collect(Collectors.toList()));
    }
}