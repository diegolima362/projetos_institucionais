package com.projetosAcademicos.api;

import com.projetosAcademicos.domain.models.Aluno;
import com.projetosAcademicos.domain.models.Endereco;
import com.projetosAcademicos.domain.models.Professor;
import com.projetosAcademicos.domain.services.EnderecoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @GetMapping("/professor/{id}")
    public ResponseEntity<Endereco> getEnderecoByProfessorId(@PathVariable("id") Long id, @RequestBody Endereco endereco) {
        Optional<Professor> p = service.getProfessorById(id);
        return p.isPresent() ?
                getEnderecoById(p.get().getId()) :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/aluno/{id}")
    public ResponseEntity<Endereco> getEnderecoByAlunoId(@PathVariable("id") Long id, @RequestBody Endereco endereco) {
        Optional<Aluno> a = service.getAlunoById(id);
        return a.isPresent() ?
                getEnderecoById(a.get().getId()) :
                ResponseEntity.notFound().build();
    }

    private ResponseEntity<Endereco> getEnderecoById(Long id) {
        Optional<Endereco> e = service.getEnderecoById(id);
        return e.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/aluno/{id}")
    public String atualizarEnderecoByAlunoId(@PathVariable("id") Long id, @RequestBody Endereco endereco) {
        Endereco e = service.atualizarEnderecoAluno(endereco, id);
        return "Endereco do aluno atualizado com sucesso: " + e.getId();
    }

    @PutMapping("/professor/{id}")
    public String atualizarEnderecoProfessor(@PathVariable("id") Long id, @RequestBody Endereco endereco) {
        Endereco e = service.atualizarEnderecoProfessor(endereco, id);
        return "Endereco do professor atualizado com sucesso: " + e.getId();
    }

}
