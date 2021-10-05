package com.projetosAcademicos.domain.repositories;

import com.projetosAcademicos.domain.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    List<Professor> findByMatricula(String matricula);
}
