package Server;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que simula a interacao do usuario com o servidor, contendo o metodo run da thread com as opcoes de manipulacao
 * que o servidor proporciona ao usuario
 */
public class Connect implements Runnable{
    private final Server server;
    private final Client client;
    private String input; //string que auxilia na leitura dos comandos do usuario
    private boolean conect; //indica se o usuario esta conectado

    /**
     * Construtor de Connect. O indicador de conexao passa a valer como conectado e sao inicializados o cliente e o servidor
     * @param client cliente se conectara ao servidor
     * @param server servidor utilizado na conexao
     */
    public Connect(Client client, Server server) {
        this.conect = true;
        this.client = client;
        this.server = server;
    }

    /**
     * Metodo que interage com o usuario, dando a ele suas opcoes,
     * lendo seus comandos e chamando os metodos do servidor correspondentes a tais comandos
     */
    @Override
    public void run() {
        int id = 0; //identificação de arquivo para abrir ou salvar
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("******** Opções do usuário ******** ");
            System.out.println("1 - Criar novo arquivo");
            System.out.println("2 - Salvar");
            System.out.println("3 - Abrir arquivo");
            System.out.println("4 - Sair");
            System.out.print("Opção: ");
            input = scanner.next();
            switch (input.charAt(0)) { //le o comando do usuario

                case '1':
                    System.out.print("Nome do arquivo: ");
                    input = scanner.next(); //le o nome do arquivo a se criar
                {
                    try {

                        id = server.newFile(input); //chama a funcao de criacao de um novo arquivo
                    } catch (IOException ex) {
                        Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

                case '2':
                    server.showFiles();
                    System.out.println("");
                    System.out.print("Id do arquivo para salvar: ");
                    input = scanner.next();
                    client.setFileServer(server.getFileServers().get(id));
                    try {
                        server.updateFile(id, ""); // Salva vazio por enquanto
                    } catch (IOException e) {
                        Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
                    }
                    break;
                case '3':
                    server.showFiles(); //chama o metodo de server que mostra os arquivos disponiveis para serem abertos
                    System.out.println("");
                    System.out.print("Id do arquivo para abrir: ");
                    input = scanner.next(); //lê o id do arqivo que o usuario deseja abrir
                    client.setFileServer(server.getFileServers().get(id)); //pega o arquivo do servidor que o cliente indicou
                    System.out.println("Digite o texto que viria do editor. Para deconectar digite 'sair':");

                    while (true) {

                        //Recebe texto do cliente para adicionar no arquivo
                        input = scanner.next();

                        //Encerra conexão
                        if (input.compareToIgnoreCase("sair") == 0) { //quando o usuario digita "sair", ele é desconectado
                            System.out.println("Conexão encerrada");
                            return;
                        }

                        //Atualiza arquivo
                        try {
                            server.updateFile(id, input); //o texto é salvo no arquivo
                        } catch (IOException e) {
                            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }


                case '4':
                    System.out.println("Conexão encerrada"); //encerra-se a conexão
                    conect = false;
                    break;
            }
        }
    }
}
