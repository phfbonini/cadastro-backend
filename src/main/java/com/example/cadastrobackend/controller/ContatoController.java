package com.example.cadastrobackend.controller;

import com.example.cadastrobackend.contato.*;
import com.example.cadastrobackend.pessoa.Pessoa;
import com.example.cadastrobackend.pessoa.PessoaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("/add/{cd_pessoa}")
    // Adiciona um novo contato para uma pessoa específica
    public ResponseEntity<?> addContato(@PathVariable Long cd_pessoa, @Valid @RequestBody ContatoRequestDTO data, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        Pessoa pessoa = pessoaRepository.findById(cd_pessoa).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        Long novoCdContato = obterNovoCdContato(cd_pessoa); // Obtém o novo número de contato
        Contato novoContato = new Contato(new ContatoId(novoCdContato, cd_pessoa), data);
        novoContato.setPessoa(pessoa);
        contatoRepository.save(novoContato);

        return new ResponseEntity<>("Contato adicionado com sucesso.", HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/pessoa/{cd_pessoa}")
    // Obtém todos os contatos de uma pessoa específica
    public List<ContatoResponseDTO> getContatosByPessoa(@PathVariable Long cd_pessoa) {
        Pessoa pessoa = pessoaRepository.findById(cd_pessoa).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        return pessoa.getContatos().stream().map(ContatoResponseDTO::new).collect(Collectors.toList());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{cd_contato}")
    // Atualiza um contato existente
    public void updateContato(@PathVariable Long cd_contato, @RequestBody ContatoRequestDTO data) {
        Contato contato = contatoRepository.findById(cd_contato).orElseThrow(() -> new RuntimeException("Contato não encontrado"));
        contato.setNm_contato(data.nm_contato());
        contato.setTelefone(Integer.valueOf(data.telefone()));
        contato.setEmail(data.email());
        contatoRepository.save(contato);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/pessoa/{cd_pessoa}")
    // Apaga todos os contatos de uma pessoa específico
    public void deleteContatosByPessoa(@PathVariable Long cd_pessoa) {
        Pessoa pessoa = pessoaRepository.findById(cd_pessoa).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        pessoa.getContatos().clear();
        pessoaRepository.save(pessoa);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{cd_contato}")
    // Apaga um contato específico
    public void deleteContato(@PathVariable Long cd_contato) {
        Contato contato = contatoRepository.findById(cd_contato).orElseThrow(() -> new RuntimeException("Contato não encontrado"));
        contatoRepository.delete(contato);
    }

    // Método para obter o próximo número de contato
    private Long obterNovoCdContato(Long cd_pessoa) {
        Optional<Long> maxCdContato = contatoRepository.findMaxCdContatoByCdPessoa(cd_pessoa);
        return maxCdContato.orElse(0L) + 1; // Se não houver contatos, começa com 1
    }
}
