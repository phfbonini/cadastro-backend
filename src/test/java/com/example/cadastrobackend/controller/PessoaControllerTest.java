package com.example.cadastrobackend.controller;

import com.example.cadastrobackend.pessoa.Pessoa;
import com.example.cadastrobackend.pessoa.PessoaResponseDTO;
import com.example.cadastrobackend.repositories.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PessoaControllerTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaController pessoaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa(1L, "João", "12345678901", new Date(), new ArrayList<>()));
        when(pessoaRepository.findAllWithContatos()).thenReturn(pessoas.stream().map(PessoaResponseDTO::new).collect(Collectors.toList()));
        List<PessoaResponseDTO> result = pessoaController.getAll();

        assertEquals(1, result.size());
        assertEquals("João", result.get(0).nm_pessoa());
    }

    @Test
    public void testGetPessoaById() {
        Pessoa pessoa = new Pessoa(1L, "João", "12345678901", new Date(), new ArrayList<>());
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        ResponseEntity<PessoaResponseDTO> result = pessoaController.getPessoaById(1L);

        assertEquals("João", result.getBody().nm_pessoa());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}