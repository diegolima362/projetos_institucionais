package com.projetosAcademicos.domain.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "aluno")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "curso")
    private String curso;

    @NotNull()
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_endereco_id", foreignKey = @ForeignKey(name = "fk_endereco_aluno"), referencedColumnName = "id")
    private Endereco endereco;
}
