package com.projetosAcademicos.domain.repositories;

import com.projetosAcademicos.domain.models.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findProjetoByTitulo(String titulo);

}
