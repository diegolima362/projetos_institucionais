package com.projetosAcademicos.api;

import com.projetosAcademicos.domain.models.Aluno;
import com.projetosAcademicos.domain.models.Endereco;
import com.projetosAcademicos.domain.models.Professor;
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

    @PutMapping("/aluno/{id}")
    public String atualizarEnderecoAluno(@PathVariable("id") Long id, @RequestBody Endereco endereco) {
        Aluno a = service.atualizarEnderecoAluno(endereco, id);
        return "Endereco do aluno atualizado com sucesso: " + a.getId();
    }

    @PutMapping("/professor/{id}")
    public String atualizarEnderecoProfessor(@PathVariable("id") Long id, @RequestBody Endereco endereco) {
        Professor p = service.atualizarEnderecoProfessor(endereco, id);
        return "Endereco do professor atualizado com sucesso: " + p.getId();
    }

}
