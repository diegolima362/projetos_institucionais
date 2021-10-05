package com.projetosAcademicos.domain.repositories;

import com.projetosAcademicos.domain.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findByMatricula(String matricula);


}
