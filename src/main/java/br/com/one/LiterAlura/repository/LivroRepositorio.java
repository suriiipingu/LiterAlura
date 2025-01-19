package br.com.one.LiterAlura.repository;

import br.com.one.LiterAlura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepositorio extends JpaRepository<Livro, Long> {

}
