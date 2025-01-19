package br.com.one.LiterAlura.repository;

import br.com.one.LiterAlura.models.Autor;
import br.com.one.LiterAlura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepositorio extends JpaRepository<Livro, Long> {
    List<Livro> findByIdioma (String idioma);
}
