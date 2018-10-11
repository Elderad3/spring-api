package com.radade.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.radade.api.model.Usuario;
import com.radade.api.security.jwt.JwtUserFactory;
import com.radade.api.service.UsuarioService;

@Service
public class JwtUserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    private UsuarioService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    		Usuario user = userService.pesquisarPorEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}