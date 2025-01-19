package br.com.one.LiterAlura.main;

import br.com.one.LiterAlura.models.Autor;
import br.com.one.LiterAlura.models.DadosLivro;
import br.com.one.LiterAlura.models.Livro;
import br.com.one.LiterAlura.repository.AutorRepositorio;
import br.com.one.LiterAlura.repository.LivroRepositorio;
import br.com.one.LiterAlura.service.ConsultaApi;
import br.com.one.LiterAlura.service.ConverteDados;
import br.com.one.LiterAlura.service.RespostaApi;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner leitura = new Scanner(System.in);
    private ConsultaApi consumo = new ConsultaApi();
    ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books";

    private LivroRepositorio livroRepositorio;
    private AutorRepositorio autorRepositorio;

    @Autowired
    public Main(LivroRepositorio livroRepositorio, AutorRepositorio autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    Escolha uma opção:
                    
                    1 - Buscar pelo título do livro
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }


    private List<DadosLivro> getDadosLivros() {
        System.out.println("Digite o título do livro:");
        var tituloLivro = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + "/?search=" + tituloLivro.replace(" ", "%20"));

        RespostaApi resposta = conversor.obterDados(json, RespostaApi.class);

        if (resposta != null && resposta.getResults() != null) {
            return resposta.getResults();
        }

        return new ArrayList<>();
    }

    private void buscarLivro() {
        List<DadosLivro> dadosLivros = getDadosLivros();

        if (!dadosLivros.isEmpty()) {
            for (DadosLivro dados : dadosLivros) {

                Livro livro = new Livro(dados);
                livroRepositorio.save(livro);

                System.out.println("Título: " + dados.titulo());

                for (Autor autor : dados.autor()) {
                    System.out.println("Autor: " + autor.getNome());
                    System.out.println("Ano de nascimento do autor: " + autor.getAnoNascimento());
                    System.out.println("Ano de morte do autor: " + autor.getAnoMorte());
                }

                System.out.println("Idioma: " + dados.idioma());

                System.out.println("-------------------------");
            }
        } else {
            System.out.println("Nenhum livro encontrado com o título fornecido.");
        }
    }

    public void listarLivros() {
        var livros = livroRepositorio.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }

        System.out.println("---- Lista de livros registrados ----");

        livros.forEach(livro -> {
            System.out.println("Livro: " + livro.getTitulo());
            System.out.println("----------------------------");
        });
    }

    public void listarAutores() {
        var autores = autorRepositorio.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado.");
            return;
        }

        System.out.println("---- Lista de autores registrados ----");

        autores.forEach(autor -> {
            System.out.println("Autor: " + autor.getNome());
            System.out.println("----------------------------");
        });
    }

    public void listarAutoresVivos() {
        System.out.println("Digite um ano:");
        var ano = leitura.nextInt();

        // Busca autores que nasceram até o ano fornecido
        var autoresPorNascimento = autorRepositorio.findAutoresVivosNoAno(ano);

        // Filtra os autores que morreram após o ano fornecido ou ainda estão vivos
        var autoresVivos = autoresPorNascimento.stream()
                .filter(autor -> autor.getAnoMorte() == null || autor.getAnoMorte() > ano)
                .toList();

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado nesse ano.");
            return;
        }

        System.out.println("---- Lista de autores vivos no ano " + ano + " ----");

        autoresVivos.forEach(autor -> {
            System.out.println("Autor: " + autor.getNome());
            System.out.println("Ano de nascimento: " +
                    (autor.getAnoNascimento() != null ? autor.getAnoNascimento() : "Informação não disponível"));

            if (autor.getAnoMorte() != null) {
                System.out.println("Ano de falecimento: " + autor.getAnoMorte());
            } else {
                System.out.println("Falecimento: Informação não disponível ou ainda está vivo.");
            }
            System.out.println("----------------------------");
        });
    }



    public void listarLivrosIdioma() {
        System.out.println("Selecione um desses idiomas:");
        System.out.println("1 - Português \n" +
                "2 - Inglês \n" +
                "3 - Espanhol \n" +
                "4 - Francês");

        var opcaoIdioma = leitura.nextInt();

        String idioma = null;

        switch (opcaoIdioma) {
            case 1 -> idioma = "pt";
            case 2 -> idioma = "en";
            case 3 -> idioma = "es";
            case 4 -> idioma = "fr";
            default -> {
                System.out.println("Opção inválida!");
                return;
            }
        }

        listarLivrosPorIdioma(idioma);

    }

    private void listarLivrosPorIdioma(String idioma) {
        var livros = livroRepositorio.findByIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma selecionado.");
            return;
        }

        System.out.println("---- Lista de livros nesse idioma ----");
        livros.forEach(livro -> {
            System.out.println("Livro: " + livro.getTitulo());
            livro.getAutores().forEach(autor -> System.out.println("Autor: " + autor.getNome()));
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("----------------------------");
        });

    }
}

