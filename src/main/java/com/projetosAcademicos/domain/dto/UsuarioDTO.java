package com.projetosAcademicos.domain.dto;

import com.projetosAcademicos.domain.models.Usuario;
import lombok.Data;

@Data
public class UsuarioDTO {
    private String login;
    private String senha;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario u) {
        this.login = u.getLogin();
        this.senha = u.getSenha();
    }
}
