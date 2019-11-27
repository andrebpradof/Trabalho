package Server;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Connect implements Runnable{
    private final Client client;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;
    private ArrayList<Client> clientArrayList;
    private static ArrayList<FileServer> fileServerArrayList;


    public Connect(Client client, Socket socket, ObjectInputStream input, ObjectOutputStream output) {
        this.client = client;
        this.socket = socket;
        objectInputStream = input;
        objectOutputStream = output;
        clientArrayList = ServerControl.getClientArrayList();
        fileServerArrayList = ServerControl.getFileServerArrayList();
    }

    private String recebeComando(){
        String input = "";
        try {
            input = objectInputStream.readUTF();
            return input;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
            try {
                clientArrayList.remove(client);
                this.objectInputStream.close();
                this.objectOutputStream.close();
                return "";
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                return "";
            }
        }
    }


    @Override
    public void run() {
        while (true){
            String input = recebeComando();
            switch (input){
                case "novo arquivo":
                    String nome = recebeComando();
                    try {
                        ServerControl.newFile(nome,client);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                    }

                    break;
            }
        }
    }
}
