package com.aluracursos.challengeliteralura;

import com.aluracursos.challengeliteralura.principal.Principal;
import com.aluracursos.challengeliteralura.repository.AutorRepository;
import com.aluracursos.challengeliteralura.repository.LibroRepository;
import com.aluracursos.challengeliteralura.service.ConsumoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraluraApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private LibroRepository libroRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepository, libroRepository);
		principal.mostrarMenu();
	}
}
