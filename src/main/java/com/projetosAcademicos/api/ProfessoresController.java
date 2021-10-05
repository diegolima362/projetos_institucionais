package com.projetosAcademicos.api;

import com.projetosAcademicos.domain.dto.ProfessorDTO;
import com.projetosAcademicos.domain.models.Professor;
import com.projetosAcademicos.domain.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/professores")
public class ProfessoresController {

    @Autowired
    private ProfessorService service;

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> get() {
        return ResponseEntity.ok(service.getProfessores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> get(@PathVariable("id") Long id) {
        Optional<Professor> profe = service.getProfessorById(id);
        return profe.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<List<ProfessorDTO>> getCarrosByTipo(@PathVariable("matricula") String matricula) {
        List<ProfessorDTO> listaProfessores = service.getProfessorByMatricula(matricula);
        return listaProfessores.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(listaProfessores);
    }

    @PostMapping
    public String cadastrarProfessor(@RequestBody Professor professor) {
        Professor c = service.cadastrar(professor);
        return "Professor salvo com sucesso: " + c.getId();
    }

    @PutMapping("/{id}")
    public String atualizarProfessor(@PathVariable("id") Long id, @RequestBody Professor professor) {
        Professor c = service.atualizar(professor, id);
        return "Professor atualizado com sucesso: " + c.getId();
    }

    @DeleteMapping("/{id}")
    public String removerProfessor(@PathVariable("id") Long id) {
        service.remover(id);
        return "Professor removido com sucesso. ";
    }

}
