package com.projetosAcademicos.api;

import com.projetosAcademicos.domain.models.Aluno;
import com.projetosAcademicos.domain.models.Endereco;
import com.projetosAcademicos.domain.services.EnderecoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/endereco")
public class EnderecoController {
    
    @Autowired
    private EnderecoService service;

    @PutMapping("/{id}")
    public String atualizarEndereco(@PathVariable("id") Long id, @RequestBody Endereco endereco) {
        Aluno a = service.atualizarEndereco(endereco, id);
        return "Endereco do aluno atualizado com sucesso: " + a.getId();
    }

}
