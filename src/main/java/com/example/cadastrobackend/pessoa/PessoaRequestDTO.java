package com.example.cadastrobackend.pessoa;

import com.example.cadastrobackend.contato.Contato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
import java.util.List;

public record PessoaRequestDTO(
        @NotBlank(message = "O nome da pessoa é obrigatório")
        String nm_pessoa,
        @NotBlank(message = "O CPF é obrigatório")
        @Size(min = 14, max = 14, message = "O CPF deve ter exatamente 14 caracteres")
        String cpf,
        @NotNull
        @Past(message = "A data de nascimento deve ser no passado")
        Date data_nascimento,
        List<Contato> contatos) {
}
