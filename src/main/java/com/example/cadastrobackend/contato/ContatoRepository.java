package com.example.cadastrobackend.contato;

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
}
