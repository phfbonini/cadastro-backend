package com.example.cadastrobackend.contato;

import com.example.cadastrobackend.pessoa.Pessoa;
import com.example.cadastrobackend.pessoa.PessoaRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Table(name = "contato")
@Entity(name = "contato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Contato {

    @EmbeddedId
    private ContatoId id;

    @ManyToOne
    @JoinColumn(name = "cd_pessoa", insertable = false, updatable = false)
    private Pessoa pessoa;

    private String nm_contato;
    private String telefone;
    private String email;

    public Contato(ContatoId id, ContatoRequestDTO data) {
        this.id = id;
        this.nm_contato = data.nm_contato();
        this.telefone = data.telefone();
        this.email = data.email();
    }
}


