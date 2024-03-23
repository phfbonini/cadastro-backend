package com.example.cadastrobackend.controller;


import com.example.cadastrobackend.pessoa.Pessoa;
import com.example.cadastrobackend.pessoa.PessoaRepository;
import com.example.cadastrobackend.pessoa.PessoaRequestDTO;
import com.example.cadastrobackend.pessoa.PessoaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pessoa")
public class PessoaController {
    @Autowired
    private PessoaRepository repository;


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void savePessoa(@RequestBody PessoaRequestDTO data){
        Pessoa pessoaData = new Pessoa(data);
        repository.save(pessoaData);
        return;

    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<PessoaResponseDTO> getAll(){
        List<PessoaResponseDTO> pessoaList = repository.findAll().stream().map(PessoaResponseDTO::new).toList();
        return pessoaList;

    }


}
