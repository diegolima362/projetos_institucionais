package com.projetosAcademicos.domain.services;

import com.projetosAcademicos.domain.dto.EnderecoDTO;
import com.projetosAcademicos.domain.models.Aluno;
import com.projetosAcademicos.domain.models.Endereco;
import com.projetosAcademicos.domain.models.Professor;
import com.projetosAcademicos.domain.repositories.AlunoRepository;
import com.projetosAcademicos.domain.repositories.EnderecoRepository;
import com.projetosAcademicos.domain.repositories.ProfessorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> getEnderecos() {
        return ResponseEntity.ok(enderecoRepository.findAll().stream()
                .map(EnderecoDTO::new).collect(Collectors.toList()));
    }

    @GetMapping
    public Optional<Endereco> getEnderecoById(Long id) {
        return enderecoRepository.findById(id);
    }

    public Endereco atualizarEnderecoAluno(Endereco endereco, Long id) {
        Optional<Aluno> optional = getAlunoById(id);
        if (optional.isPresent()) {
            System.out.println(endereco);
            Long enderecoId = optional.get().getEndereco().getId();
            return atualizar(endereco, enderecoId);
        } else {
            throw new RuntimeException("Não foi possível atualizar o aluno informado");
        }
    }


    public Endereco atualizarEnderecoProfessor(Endereco endereco, Long id) {
        Optional<Professor> optional = getProfessorById(id);
        if (optional.isPresent()) {
            System.out.println(endereco);
            Long enderecoId = optional.get().getEndereco().getId();
            return atualizar(endereco, enderecoId);
        } else {
            throw new RuntimeException("Não foi possível atualizar o professor informado");
        }
    }

    public Optional<Aluno> getAlunoById(Long id) {
        return alunoRepository.findById(id);
    }

    public Optional<Professor> getProfessorById(Long id) {
        return professorRepository.findById(id);
    }

    private Endereco atualizar(Endereco endereco, Long id) {
        Optional<Endereco> optional = enderecoRepository.findById(id);
        if (optional.isPresent()) {
            Endereco enderecoDB = optional.get();
            enderecoDB.setCidade(endereco.getCidade());
            enderecoDB.setCep(endereco.getCep());
            enderecoDB.setEstado(endereco.getEstado());
            enderecoDB.setNumero(endereco.getNumero());
            enderecoDB.setPais(endereco.getPais());
            enderecoDB.setRua(endereco.getRua());

            return enderecoRepository.save(enderecoDB);
        } else {
            throw new RuntimeException("Não foi possível atualizar o aluno informado");
        }
    }
}
