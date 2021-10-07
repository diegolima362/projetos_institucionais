package com.projetosAcademicos.domain.services;

import com.projetosAcademicos.domain.dto.ProjetoDTO;
import com.projetosAcademicos.domain.models.Projeto;
import com.projetosAcademicos.domain.repositories.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    public List<ProjetoDTO> getProjetos() {
        return projetoRepository.findAll().stream().map(ProjetoDTO::new).collect(Collectors.toList());
    }

    public Optional<Projeto> getProjetoById(Long id) {
        return projetoRepository.findById(id);
    }

    public List<ProjetoDTO> getProjetoByTitulo(String titulo) {
        return projetoRepository.findProjetoByTitulo(titulo).stream().map(ProjetoDTO::new).collect(Collectors.toList());
    }

    public Projeto cadastrar(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public Projeto atualizar(Projeto projeto, Long id) {

        Optional<Projeto> optional = getProjetoById(id);
        if (optional.isPresent()) {
            Projeto projetoDB = optional.get();

            projetoDB.setTitulo(projeto.getTitulo());
            projetoDB.setArea(projeto.getArea());
            projetoDB.setResumo(projeto.getResumo());
            projetoDB.setUrlDocumento(projeto.getUrlDocumento());


            projetoDB.setPalavraChave1(projeto.getPalavraChave1());
            projetoDB.setPalavraChave2(projeto.getPalavraChave2());
            projetoDB.setPalavraChave3(projeto.getPalavraChave3());

            projetoDB.setProfessor(projeto.getProfessor());
            projetoDB.setAlunos(projeto.getAlunos());


            projetoRepository.save(projetoDB);
            return projetoDB;
        } else {
            throw new RuntimeException("Não foi possível atualizar o projeto informado");
        }
    }

    public void remover(Long id) {
        Optional<Projeto> projeto = getProjetoById(id);
        if (projeto.isPresent()) {
            projetoRepository.deleteById(id);
        }
    }
}
