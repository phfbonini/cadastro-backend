package com.example.cadastrobackend.pessoa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "pessoa")
@Entity(name = "pessoa")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cd_pessoa")
public class Pessoa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cd_pessoa;
    private String nm_pessoa;
    private String cpf;
    private Date data_nascimento;


    public Pessoa(PessoaRequestDTO data){
        this.nm_pessoa = data.nm_pessoa();
        this.cpf = data.cpf();
        this.data_nascimento = data.data_nascimento();
    }
}

