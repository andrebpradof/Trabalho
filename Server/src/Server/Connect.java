package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect implements Runnable{
    private final Client client;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;


    public Connect(Client client,ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.client = client;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }


    @Override
    public void run() {

        /*
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
        */
    }
}
