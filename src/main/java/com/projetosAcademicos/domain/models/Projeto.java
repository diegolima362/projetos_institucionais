package com.projetosAcademicos.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "projeto")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "area_projeto")
    private String area;

    @Column(name = "resumo")
    private String resumo;

    @Column(name = "palavra_chave_1")
    private String palavraChave1;

    @Column(name = "palavra_chave_2")
    private String palavraChave2;

    @Column(name = "palavra_chave_3")
    private String palavraChave3;

    @Column(name = "url_documento")
    private String curso;

    @ManyToOne
    @JoinColumn(name="professor_id", nullable=false)
    private Professor professor;

    @ManyToMany
    @JoinTable(
            name = "alunos_projeto",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    Set<Aluno> alunos;
}
