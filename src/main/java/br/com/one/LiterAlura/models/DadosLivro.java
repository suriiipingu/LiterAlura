package br.com.one.LiterAlura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
public record DadosLivro (@JsonAlias ("title") String titulo,
                         @JsonAlias ("authors") List<Autor> autor,
                         @JsonAlias ("languages")List<String> idioma) {

}
