package com.radade.api.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import com.radade.api.model.Usuario;
import com.radade.api.model.enums.Perfil;

public class JwtUserFactory {
	private JwtUserFactory() {
	}

	public static JwtUser create(Usuario user) {
		return new JwtUser(user.getId(), user.getEmail(), user.getPassword(),
				mapToGrantedAuthorities(user.getPerfil()));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(Perfil perfil) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(); 
		authorities.add(new SimpleGrantedAuthority(perfil.toString())); 
		return   authorities ;
	}
}