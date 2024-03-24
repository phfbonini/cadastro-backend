package com.example.cadastrobackend.repositories;

import com.example.cadastrobackend.pessoa.Pessoa;
import com.example.cadastrobackend.pessoa.PessoaResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @Query("SELECT DISTINCT p FROM pessoa p LEFT JOIN FETCH p.contatos")
    List<PessoaResponseDTO> findAllWithContatos();
}
