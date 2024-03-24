package com.example.cadastrobackend.repositories;

import com.example.cadastrobackend.contato.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
    @Query("SELECT MAX(c.id.cd_contato) FROM contato c WHERE c.id.cd_pessoa = :cd_pessoa")
    Optional<Long> findMaxCdContatoByCdPessoa(@Param("cd_pessoa") Long cd_pessoa);

    @Modifying
    @Query("DELETE FROM contato c WHERE c.id.cd_pessoa = :cdPessoa")
    void deleteByCdPessoa(@Param("cdPessoa") Long cdPessoa);

    @Query("SELECT c FROM contato c WHERE c.id.cd_pessoa = :cd_pessoa AND c.id.cd_contato = :cd_contato")
    Optional<Contato> findByCdPessoaAndCdContato(@Param("cd_pessoa") Long cd_pessoa, @Param("cd_contato") Long cd_contato);
}
