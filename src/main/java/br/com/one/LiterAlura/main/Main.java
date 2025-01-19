package br.com.one.LiterAlura.main;

import br.com.one.LiterAlura.models.Autor;
import br.com.one.LiterAlura.models.DadosLivro;
import br.com.one.LiterAlura.models.Livro;
import br.com.one.LiterAlura.repository.LivroRepositorio;
import br.com.one.LiterAlura.service.ConsultaApi;
import br.com.one.LiterAlura.service.ConverteDados;
import br.com.one.LiterAlura.service.RespostaApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner leitura = new Scanner(System.in);
    private ConsultaApi consumo = new ConsultaApi();
    ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books";

    private LivroRepositorio repositorio;

    public Main(LivroRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu(){
        var opcao = -1;
        while(opcao != 0){
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
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 0:
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
                repositorio.save(livro);

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

}
