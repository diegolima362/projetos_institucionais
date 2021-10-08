package com.projetosAcademicos.domain.services;

import java.util.ArrayList;

import com.projetosAcademicos.domain.dto.UsuarioDTO;
import com.projetosAcademicos.domain.models.Usuario;
import com.projetosAcademicos.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario não encontrado: " + login);
        }
        return new org.springframework.security.core.userdetails.User(usuario.getLogin(), usuario.getSenha(),
                new ArrayList<>());
    }

    public Usuario cadastrar(UsuarioDTO usuario) {
        Usuario u = new Usuario();
        u.setLogin(usuario.getLogin());
        u.setSenha(bcryptEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(u);
    }
}
