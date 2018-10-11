package com.radade.api.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.radade.api.model.Usuario;
import com.radade.api.security.jwt.JwtAuthenticationRequest;
import com.radade.api.security.jwt.JwtTokenUtil;
import com.radade.api.security.model.CurrentUser;
import com.radade.api.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationRestController {

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private UsuarioService userService;

    @PostMapping(value="/radade/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Usuario user = userService.pesquisarPorEmail(authenticationRequest.getEmail());
        user.setPassword(null);
        return ResponseEntity.ok(new CurrentUser(token, user));
    }

    @PostMapping(value="/api/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String username = jwtTokenUtil.getUsernameFromToken(token);
        final Usuario user = userService.pesquisarPorEmail(username);
        
        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new CurrentUser(refreshedToken, user));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}