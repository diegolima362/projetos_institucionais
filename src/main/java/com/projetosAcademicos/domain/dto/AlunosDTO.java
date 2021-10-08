package com.projetosAcademicos.domain.dto;

import java.util.Collection;
import java.util.List;

import lombok.Data;

@Data
public class AlunosDTO {
    private List<Long> ids;

    public AlunosDTO() {

    }

    public AlunosDTO(List<Long> ids) {
        this.ids = ids;
    }
}
