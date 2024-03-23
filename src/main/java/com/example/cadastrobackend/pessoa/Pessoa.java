package com.example.cadastrobackend.pessoa;

import com.example.cadastrobackend.contato.Contato;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "pessoa")
@Entity(name = "pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cd_pessoa")
public class Pessoa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cd_pessoa;
    private String nm_pessoa;
    private String cpf;
    private Date data_nascimento;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Contato> contatos;

    public Pessoa(PessoaRequestDTO data) {
        this.nm_pessoa = data.nm_pessoa();
        this.cpf = data.cpf();
        this.data_nascimento = data.data_nascimento();
        this.contatos = data.contatos();
    }
}

