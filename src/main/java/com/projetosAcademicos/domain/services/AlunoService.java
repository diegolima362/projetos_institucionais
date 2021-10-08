package com.projetosAcademicos.domain.services;

import com.projetosAcademicos.domain.dto.AlunoDTO;
import com.projetosAcademicos.domain.models.Aluno;
import com.projetosAcademicos.domain.repositories.AlunoRepository;
import com.projetosAcademicos.domain.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private EnderecoService enderecoService;

    public List<AlunoDTO> getAlunos() {
        return alunoRepository.findAll().stream().map(AlunoDTO::new).collect(Collectors.toList());
    }

    public List<Aluno> getAlunosById(List<Long> ids) {
        return alunoRepository.findAllById(ids);
    }

    public Optional<Aluno> getAlunoById(Long id) {
        return alunoRepository.findById(id);
    }

    public List<AlunoDTO> getAlunoByMatricula(String matricula) {
        return alunoRepository.findByMatricula(matricula).stream().map(AlunoDTO::new).collect(Collectors.toList());
    }

    public Aluno cadastrar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Aluno atualizar(Aluno aluno, Long id) {

        Optional<Aluno> optional = getAlunoById(id);
        if (optional.isPresent()) {
            Aluno alunoBD = optional.get();
            alunoBD.setMatricula(aluno.getMatricula());
            alunoBD.setNome(aluno.getNome());
            alunoBD.setCpf(aluno.getCpf());
            alunoBD.setCurso(aluno.getCurso());

            // Atualizar endereco
            enderecoService.atualizarEnderecoAluno(aluno.getEndereco(), id);
            alunoRepository.save(alunoBD);
            return alunoBD;
        } else {
            throw new RuntimeException("Não foi possível atualizar o aluno informado");
        }
    }

    public boolean remover(Long id) {
        Optional<Aluno> aluno = getAlunoById(id);
        if (aluno.isPresent()) {
            alunoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
