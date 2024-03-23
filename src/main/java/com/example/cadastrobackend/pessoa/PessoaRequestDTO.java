package com.example.cadastrobackend.pessoa;

import java.util.Date;

public record PessoaRequestDTO(String nm_pessoa, String cpf, Date data_nascimento) {
}
