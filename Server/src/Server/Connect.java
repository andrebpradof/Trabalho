package Server;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connect implements Runnable{
    private Client client;
    private ObjectOutputStream outputListen;
    private ObjectInputStream inputListen;
    private Socket socket;


    public Connect(Client client, Socket socket, ObjectInputStream input, ObjectOutputStream output) {
        this.client = client;
        this.socket = socket;
        this.inputListen = input;
        this.outputListen = output;
    }


    @Override
    public void run() {
        while (true){
            String input = null;
            try {
                input = inputListen.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (input){
                case "novo":
                    try {
                        String nome = inputListen.readUTF();
                        outputListen.writeInt(ServerControl.newFile(nome,client));
                        outputListen.flush();

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                    }
                break;
                case "upload":
                    try {
                            String nome = inputListen.readUTF();
                            if(ServerControl.verificaArquivo(nome) == -1) {
                                outputListen.writeInt(-1);
                                outputListen.flush();
                                break;
                            }
                            outputListen.writeInt(1);
                            outputListen.flush();
                            String texto = inputListen.readUTF();
                            outputListen.write(ServerControl.upload(nome,texto,client));
                            outputListen.flush();

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                    }
                break;
                case "abrir":
                    try {
                        outputListen.writeUTF(ServerControl.listaArquivos());
                        outputListen.flush();
                        outputListen.writeUTF(ServerControl.abrir(inputListen.read(),client));
                        outputListen.flush();

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                    }
                break;
                case "texto":
                    try {
                        String texto = inputListen.readUTF();
                        int resposta = ServerControl.recebeTexto(texto,client);
                        outputListen.writeInt(resposta);
                        outputListen.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
            }
        }
    }
}
