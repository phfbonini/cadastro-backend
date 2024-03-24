package com.example.cadastrobackend.controller;

import com.example.cadastrobackend.contato.*;
import com.example.cadastrobackend.pessoa.Pessoa;
import com.example.cadastrobackend.repositories.PessoaRepository;
import com.example.cadastrobackend.repositories.ContatoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("contato")
public class ContatoController {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/paginado")
    // Método para obter contatos de forma paginada Ok
    public Page<ContatoResponseDTO> getContatosPaginado(Pageable pageable) {
        return contatoRepository.findAll(pageable).map(ContatoResponseDTO::new);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/add/{cd_pessoa}")
    // Adiciona um novo contato para uma pessoa específica Ok
    public ResponseEntity<?> addContato(@PathVariable Long cd_pessoa, @Valid @RequestBody ContatoRequestDTO data, BindingResult result) {
        System.out.println("@TESTEEE" + cd_pessoa);
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError(), HttpStatus.BAD_REQUEST);
        }
        Pessoa pessoa = pessoaRepository.findById(cd_pessoa).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        Long novoCdContato = obterNovoCdContato(cd_pessoa); // Obtém o novo número de contato
        Contato novoContato = new Contato(new ContatoId(novoCdContato, cd_pessoa), data);
        contatoRepository.save(novoContato);

        return new ResponseEntity<>("Contato adicionado com sucesso.", HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{cd_pessoa}/{cd_contato}")
    // Atualiza um contato existente ok
    public void updateContato(@PathVariable Long cd_pessoa, @PathVariable Long cd_contato, @RequestBody ContatoRequestDTO data) {
        Contato contato = contatoRepository.findByCdPessoaAndCdContato(cd_pessoa, cd_contato)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));

        contato.setNm_contato(data.nm_contato());
        contato.setTelefone(data.telefone());
        contato.setEmail(data.email());
        contatoRepository.save(contato);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{cd_pessoa}/{cd_contato}")
    // Apaga um contato específico ok
    public void deleteContato(@PathVariable Long cd_pessoa, @PathVariable Long cd_contato) {
        Contato contato = contatoRepository.findByCdPessoaAndCdContato(cd_pessoa, cd_contato)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));
        contatoRepository.delete(contato);
    }

    // Método para obter o próximo número de contato
    private Long obterNovoCdContato(Long cd_pessoa) {
        Optional<Long> maxCdContato = contatoRepository.findMaxCdContatoByCdPessoa(cd_pessoa);
        return maxCdContato.orElse(0L) + 1; // Se não houver contatos, começa com 1
    }
}
