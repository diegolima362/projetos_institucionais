package com.projetosAcademicos.api;

import com.projetosAcademicos.domain.dto.ProjetoDTO;
import com.projetosAcademicos.domain.models.Projeto;
import com.projetosAcademicos.domain.services.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projetos")
public class ProjetosController {
    @Autowired
    private ProjetoService service;

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
    public String cadastrarProjeto(@RequestBody Projeto projeto) {
        Projeto c = service.cadastrar(projeto);
        return "Projeto salvo com sucesso: " + c.getId();
    }

    @PutMapping("/{id}")
    public String atualizarProjeto(@PathVariable("id") Long id, @RequestBody Projeto projeto) {
        Projeto c = service.atualizar(projeto, id);
        return "Projeto atualizado com sucesso: " + c.getId();
    }

    @DeleteMapping("/{id}")
    public String removerProjeto(@PathVariable("id") Long id) {
        service.remover(id);
        return "Projeto removido com sucesso. ";
    }

}
