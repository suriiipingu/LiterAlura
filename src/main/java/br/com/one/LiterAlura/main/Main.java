package br.com.one.LiterAlura.main;

import br.com.one.LiterAlura.service.ConsultaApi;

import java.util.Scanner;

public class Main {
    private Scanner leitura = new Scanner(System.in);
    private ConsultaApi consumo = new ConsultaApi();

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
}
