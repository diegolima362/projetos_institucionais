package com.projetosAcademicos.domain.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "area")
    private String area;

    @Column(name = "resumo")
    private String resumo;

    @Column(name = "palavrachave1")
    private String palavraChave1;

    @Column(name = "palavrachave2")
    private String palavraChave2;

    @Column(name = "palavrachave3")
    private String palavraChave3;

    @Column(name = "url")
    private String curso;

}
