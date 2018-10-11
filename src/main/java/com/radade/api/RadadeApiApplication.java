package com.radade.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.radade.api.model.Usuario;
import com.radade.api.model.enums.Perfil;
import com.radade.api.repository.UsuarioRepository;

@SpringBootApplication
public class RadadeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RadadeApiApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			initUsers(userRepository, passwordEncoder);
		};

	}

	private void initUsers(UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
		
		Usuario admin = new Usuario();
		admin.setNome("Elder");
		admin.setEmail("admin@radade.com");
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setPerfil(Perfil.ROLE_ADMIN);

		Usuario find = userRepository.findByEmail("admin@radade.com");
		if (find == null) {
			userRepository.save(admin);
		}
	}
}
