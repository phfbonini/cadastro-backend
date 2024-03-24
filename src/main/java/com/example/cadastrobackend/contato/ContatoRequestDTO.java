package com.example.cadastrobackend.contato;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public record ContatoRequestDTO(
        @NotNull(message = "O ID da pessoa é obrigatório")
        Long cd_pessoa,
        @NotBlank(message = "O nome do contato é obrigatório")
        String nm_contato,
        @NotNull(message = "O telefone é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "O telefone deve conter exatamente 11 dígitos")
        String telefone,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        String email) {
}