package br.com.one.LiterAlura;

import br.com.one.LiterAlura.main.Main;
import br.com.one.LiterAlura.repository.AutorRepositorio;
import br.com.one.LiterAlura.repository.LivroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private LivroRepositorio livroRepositorio;
	@Autowired
	private AutorRepositorio autorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
 		Main main = new Main(livroRepositorio, autorRepositorio);
		 main.exibeMenu();
	}
}
