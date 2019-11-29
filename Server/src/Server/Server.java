package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Classe que controla os clientes que entram no servidor, como tambem a manipulacao dos arquivos
 */

public class Server {

    public static void main(String []args) throws IOException {
        ServerSocket server = new ServerSocket(4000);

        while (true){
            System.out.println("Aguardando conexões...");
            Socket socket = server.accept(); // recebe uma conexao com um cliente

            System.out.println("Conectado!");

            //inicializa os objetos output e input do servidor para estabelecer comunicacao com o cliente
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            //atrela o cliente conectado ao socket e aos objetos e input e output
            Client client = new Client(socket,output,input);
            ServerControl.addClient(client); //adiciona o cliente a array list de clientes dentro do servidor
            Connect connect = new Connect(client,socket,input,output); //estabelece a conexao entre o servidor e o cliente
            Thread thread = new Thread(connect);
            thread.start(); //inicia a thread de conexao
        }
    }// Fim do método main
}



