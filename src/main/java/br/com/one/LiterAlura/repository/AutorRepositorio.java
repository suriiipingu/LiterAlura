package br.com.one.LiterAlura.repository;

import br.com.one.LiterAlura.models.Autor;
import br.com.one.LiterAlura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoMorte IS NULL OR a.anoMorte > :ano)")
    List<Autor> findAutoresVivosNoAno(@Param("ano") int ano);

}
