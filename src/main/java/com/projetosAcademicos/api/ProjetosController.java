package com.projetosAcademicos.api;

import com.projetosAcademicos.domain.dto.AlunosDTO;
import com.projetosAcademicos.domain.dto.ProjetoDTO;
import com.projetosAcademicos.domain.models.Aluno;
import com.projetosAcademicos.domain.models.Professor;
import com.projetosAcademicos.domain.models.Projeto;
import com.projetosAcademicos.domain.services.AlunoService;
import com.projetosAcademicos.domain.services.ProfessorService;
import com.projetosAcademicos.domain.services.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/projetos")
public class ProjetosController {
    @Autowired
    private ProjetoService service;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> get() {
        return ResponseEntity.ok(service.getProjetos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> get(@PathVariable("id") Long id) {
        Optional<Projeto> projeto = service.getProjetoById(id);
        return projeto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<ProjetoDTO>> getProjetoByTitulo(@PathVariable("titulo") String titulo) {
        List<ProjetoDTO> listaProjetos = service.getProjetoByTitulo(titulo);
        return listaProjetos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(listaProjetos);
    }


    @PostMapping
    public String cadastrarProjeto(@RequestBody ProjetoDTO projeto) {
        Optional<Professor> professor = professorService.getProfessorById(projeto.getProfessorId());
        List<Aluno> alunos = alunoService.getAlunosById(projeto.getAlunosId());

        if (professor.isPresent() && !alunos.isEmpty()) {
            Projeto p = new Projeto();
            p.setTitulo(projeto.getTitulo());
            p.setArea(projeto.getArea());
            p.setResumo(projeto.getResumo());
            p.setPalavraChave1(projeto.getPalavraChave1());
            p.setPalavraChave2(projeto.getPalavraChave2());
            p.setPalavraChave3(projeto.getPalavraChave3());
            p.setAlunos(alunos);
            p.setProfessor(professor.get());
            p.setUrlDocumento(projeto.getUrlDocumento());

            return "Projeto cadastrado com sucesso: " + service.cadastrar(p).getId();
        }

        return "Projeto não foi salvo!";
    }

    @PutMapping("/{id}/addAluno/{aluno_id}")
    public String adicionarAluno(@PathVariable("id") Long id, @PathVariable("aluno_id") Long alunoId) {
        Optional<Projeto> p = service.getProjetoById(id);
        if (!p.isPresent()) return "Projeto não encontrado!";

        Optional<Aluno> a = alunoService.getAlunoById(alunoId);
        if (!a.isPresent()) return "Aluno não encontrado!";

        p.get().getAlunos().add(a.get());
        List<Long> ids = service.atualizar(p.get(), id).getAlunos().stream().map(Aluno::getId).collect(Collectors.toList());
        return "Aluno adicionado ao projeto: " + ids;
    }

    @PostMapping("/{id}/addAluno")
    public String adicionarAlunos(@PathVariable("id") Long id, @RequestBody AlunosDTO alunos) {
        Optional<Projeto> p = service.getProjetoById(id);
        if (!p.isPresent()) return "Projeto não encontrado!";

        StringBuffer erros = new StringBuffer("Os seguintes alunos não foram adicionados: ");

        for (int i = 0; i < alunos.getIds().size(); i++) {
            Long alunoId = alunos.getIds().get(i);
            Optional<Aluno> a = alunoService.getAlunoById(alunoId);
            if (!a.isPresent()) {
                erros.append(alunoId);
            } else {
                p.get().getAlunos().add(a.get());
            }
        }

        service.atualizar(p.get(), id);

        if (erros.length() != 0)
            return erros.toString();

        return "Todos os alunos foram adicionados ao projeto";
    }

    @PostMapping("/{id}/removeAluno/{aluno_id}")
    public String removerAluno(@PathVariable("id") Long id, @PathVariable("aluno_id") Long alunoId) {
        Optional<Projeto> p = service.getProjetoById(id);
        if (!p.isPresent()) return "Projeto não encontrado!";

        Boolean removido = false;
        for (int i = 0; i < p.get().getAlunos().size(); i++) {
            Aluno aluno = p.get().getAlunos().get(i);
            if (aluno.getId().equals(alunoId)) {
                p.get().getAlunos().remove(i);
                removido = true;
                break;
            }
        }

        service.atualizar(p.get(), id);

        if (!removido) {
            return "Aluno não está no projeto";
        }

        return "Aluno foi removido do projeto";
    }

    @PutMapping("/{id}")
    public String atualizarProjeto(@PathVariable("id") Long id, @RequestBody Projeto projeto) {
        Projeto c = service.atualizar(projeto, id);
        return "Projeto atualizado com sucesso: " + c.getId();
    }

    @PutMapping("/{id}/professor/{professor_id}")
    public String atualizarProfessorProjeto(@PathVariable("id") Long id, @PathVariable("professor_id") Long professorId) {
        Projeto c = service.alterarProfessor(id, professorId);
        return "Projeto atualizado com sucesso: " + c.getId();
    }

    @DeleteMapping("/{id}")
    public String removerProjeto(@PathVariable("id") Long id) {
        service.remover(id);
        return "Projeto removido com sucesso. ";
    }
}
