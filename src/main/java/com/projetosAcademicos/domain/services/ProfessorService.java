package com.projetosAcademicos.domain.services;

import com.projetosAcademicos.domain.dto.ProfessorDTO;
import com.projetosAcademicos.domain.models.Professor;
import com.projetosAcademicos.domain.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public List<ProfessorDTO> getProfessores() {
        return professorRepository.findAll().stream().map(ProfessorDTO::new).collect(Collectors.toList());
    }

    public Optional<Professor> getProfessorById(Long id) {
        return professorRepository.findById(id);
    }

    public List<ProfessorDTO> getProfessorByMatricula(String matricula) {
        return professorRepository.findByMatricula(matricula).stream().map(ProfessorDTO::new).collect(Collectors.toList());
    }

    public Professor cadastrar(Professor professor) {
        return professorRepository.save(professor);
    }

    public Professor atualizar(Professor professor, Long id) {

        Optional<Professor> optional = getProfessorById(id);
        if (optional.isPresent()) {
            Professor professorDB = optional.get();
            professorDB.setMatricula(professor.getMatricula());
            professorDB.setNome(professor.getNome());
            professorDB.setCpf(professor.getCpf());
            professorDB.setCurso(professor.getCurso());
            professorDB.setEndereco(professor.getEndereco());

            professorRepository.save(professorDB);
            return professorDB;
        } else {
            throw new RuntimeException("Não foi possível atualizar o professor informado");
        }
    }

    public void remover(Long id) {
        Optional<Professor> professor = getProfessorById(id);
        if (professor.isPresent()) {
            professorRepository.deleteById(id);
        }
    }

}
