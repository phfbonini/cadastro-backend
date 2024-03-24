package com.example.cadastrobackend.contato;

public record ContatoResponseDTO(
        Long cd_contato,
        String nm_contato,
        Integer telefone,
        String email
) {
    public ContatoResponseDTO(Contato contato) {
        this(contato.getId().getCd_contato(), contato.getNm_contato(), contato.getTelefone(), contato.getEmail());
    }
}
