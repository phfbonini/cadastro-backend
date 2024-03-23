package com.example.cadastrobackend.controller;

import com.example.cadastrobackend.contato.ContatoRepository;
import com.example.cadastrobackend.pessoa.Pessoa;
import com.example.cadastrobackend.pessoa.PessoaRepository;
import com.example.cadastrobackend.pessoa.PessoaRequestDTO;
import com.example.cadastrobackend.pessoa.PessoaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("pessoa")
public class PessoaController {
    @Autowired
    private PessoaRepository repository;

    @Autowired
    private ContatoRepository contatoRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    // Método para salvar uma nova pessoa
    public ResponseEntity<?> savePessoa(@Valid @RequestBody PessoaRequestDTO data, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        Pessoa pessoaData = new Pessoa(data);
        repository.save(pessoaData);

        return new ResponseEntity<>("Pessoa salva com sucesso.", HttpStatus.OK);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    // Método para obter todas as pessoas
    public List<PessoaResponseDTO> getAll() {
        System.out.println(repository.findAllWithContatos());
        return repository.findAllWithContatos();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/paginado")
    // Método para obter pessoas paginadas
    public Page<Pessoa> getPessoasPaginado(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    // Método para obter uma pessoa por ID
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable Long id) {
        Optional<Pessoa> pessoaOptional = repository.findById(id);
        return pessoaOptional.map(pessoa -> new ResponseEntity<>(pessoa, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    // Método para excluir uma pessoa e seus contatos por ID
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    // Método para atualizar os dados de uma pessoa por ID
    public ResponseEntity<String> updatePessoaById(@PathVariable Long id, @RequestBody PessoaRequestDTO newData) {
        Optional<Pessoa> pessoaOptional = repository.findById(id);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            // Atualize os dados da pessoa com os novos dados fornecidos
            pessoa.setNm_pessoa(newData.nm_pessoa());
            pessoa.setCpf(newData.cpf());
            pessoa.setData_nascimento(newData.data_nascimento());

            // Salve a pessoa atualizada
            repository.save(pessoa);

            return new ResponseEntity<>("Dados da pessoa atualizados com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Pessoa não encontrada.", HttpStatus.NOT_FOUND);
        }
    }


}
