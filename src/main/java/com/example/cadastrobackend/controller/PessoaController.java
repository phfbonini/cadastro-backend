package com.example.cadastrobackend.controller;

import com.example.cadastrobackend.contato.Contato;
import com.example.cadastrobackend.contato.ContatoResponseDTO;
import com.example.cadastrobackend.repositories.ContatoRepository;
import com.example.cadastrobackend.pessoa.Pessoa;
import com.example.cadastrobackend.repositories.PessoaRepository;
import com.example.cadastrobackend.pessoa.PessoaRequestDTO;
import com.example.cadastrobackend.pessoa.PessoaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("pessoa")
public class PessoaController {
    @Autowired
    private PessoaRepository repository;

    @Autowired
    private ContatoRepository contatoRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    // Método para obter todas as pessoas OK
    public List<PessoaResponseDTO> getAll() {
        System.out.println(repository.findAllWithContatos());
        return repository.findAllWithContatos();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    // Método para obter uma pessoa por ID OK
    public ResponseEntity<PessoaResponseDTO> getPessoaById(@PathVariable Long id) {
        Optional<Pessoa> pessoaOptional = repository.findById(id);
        return pessoaOptional.map(pessoa -> new ResponseEntity<>(new PessoaResponseDTO(pessoa), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/paginado")
    // Método para obter contatos de forma paginada OK
    public Page<PessoaResponseDTO> getContatosPaginado(Pageable pageable) {
        return repository.findAll(pageable).map(PessoaResponseDTO::new);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/contatos/{cd_pessoa}")
    // Obtém todos os contatos de uma pessoa específica Ok
    public List<ContatoResponseDTO> getContatosByPessoa(@PathVariable Long cd_pessoa) {
        Pessoa pessoa = repository.findById(cd_pessoa).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        return pessoa.getContatos().stream().map(ContatoResponseDTO::new).collect(Collectors.toList());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    // Método para salvar uma nova pessoa Ok
    public ResponseEntity<?> savePessoa(@Valid @RequestBody PessoaRequestDTO data, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        Pessoa pessoaData = new Pessoa(data);
        repository.save(pessoaData);

        return new ResponseEntity<>("Pessoa salva com sucesso.", HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    // Método para atualizar os dados de uma pessoa por ID Ok
    public ResponseEntity<String> updatePessoaById(@PathVariable Long id, @Valid @RequestBody PessoaRequestDTO newData, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        Optional<Pessoa> pessoaOptional = repository.findById(id);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            pessoa.setNm_pessoa(newData.nm_pessoa());
            pessoa.setCpf(newData.cpf());
            pessoa.setData_nascimento(newData.data_nascimento());
            repository.save(pessoa);
            return new ResponseEntity<>("Dados da pessoa atualizados com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Pessoa não encontrada.", HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Transactional
    @DeleteMapping("/contatos/{id}")
    // Método para excluir todos os contatos de uma pessoa pelo ID da pessoa Ok
    public ResponseEntity<String> deleteContatosByPessoa(@PathVariable Long id) {
        Optional<Pessoa> pessoaOptional = repository.findById(id);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            contatoRepository.deleteByCdPessoa(pessoa.getCd_pessoa());
            return new ResponseEntity<>("Contatos da pessoa foram apagados com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Pessoa não encontrada.", HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Transactional
    @DeleteMapping("/{id}")
    // Método para excluir uma pessoa e seus contatos por ID Ok
    public ResponseEntity<String> deletePessoaById(@PathVariable Long id) {
        Optional<Pessoa> pessoaOptional = repository.findById(id);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            contatoRepository.deleteByCdPessoa(pessoa.getCd_pessoa());
            repository.delete(pessoa);
            return new ResponseEntity<>("Pessoa e seus contatos foram apagados com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Pessoa não encontrada.", HttpStatus.NOT_FOUND);
        }
    }

}
