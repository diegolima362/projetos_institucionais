package com.projetosAcademicos.api;

import com.projetosAcademicos.config.JwtTokenUtil;
import com.projetosAcademicos.domain.dto.UsuarioDTO;
import com.projetosAcademicos.domain.models.JwtRequest;
import com.projetosAcademicos.domain.models.JwtResponse;
import com.projetosAcademicos.domain.models.Usuario;
import com.projetosAcademicos.domain.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsuarioService userDetailsService;

    @PostMapping(value = "/autenticar")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
        autenticar(request.getLogin(), request.getSenha());

        try {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (UsernameNotFoundException e) {
            throw new Exception("USERNAME_NOT_FOUND: Dados invalidos\n", e);
        }
    }


    @PostMapping(value = "/registrar")
    public ResponseEntity<?> saveUser(@RequestBody UsuarioDTO user) throws Exception {
        System.out.println(user);
        return ResponseEntity.ok(new UsuarioDTO(userDetailsService.cadastrar(user)));
    }

    private void autenticar(String login, String senha) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, senha));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED: Usuario desativado\n", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS: Dados invalidos\n", e);
        }
    }
}
