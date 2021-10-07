package com.projetosAcademicos.domain.services;

import com.projetosAcademicos.domain.models.Aluno;
import com.projetosAcademicos.domain.models.Endereco;
import com.projetosAcademicos.domain.repositories.AlunoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    
    @Autowired
    private AlunoRepository alunoRepository;

    public Aluno atualizarEndereco(Endereco endereco , Long id) {
        Optional<Aluno> optional = getAlunoById(id);

        if (optional.isPresent()) {
            Aluno alunoBD = optional.get();
            alunoBD.setEndereco(endereco);

            alunoRepository.save(alunoBD);
            return alunoBD;
        } else {
            throw new RuntimeException("Não foi possível atualizar o aluno informado");
        }
    }

    public Optional<Aluno> getAlunoById(Long id) {
        return alunoRepository.findById(id);
    }
}
