package Server;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe que controla os clientes que entram no servidor, como tambem a manipulacao dos arquivos
 */

public class Server {
    private final ArrayList<Client> clients = new ArrayList<Client>();
    private final ArrayList<FileServer> fileServers = new ArrayList<FileServer>();


    public static void main(String []args) throws IOException {
        ServerSocket server = new ServerSocket(4000);

        while (true){
            System.out.println("Aguardando conexões...");
            Socket socket = server.accept();

            System.out.println("Conectado!");

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());


            Client client = new Client();
            client.setSocket(socket);
            ServerControl.addClient(client);
            Connect connect = new Connect(client,socket,input,output);
            Thread thread = new Thread(connect);
            thread.start();
        }


    }// Fim do método main
}



