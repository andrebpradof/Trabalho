package Editor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConect {

    private static Cominication cominication;
    private static Socket socket;

    public static void conectar() throws IOException {

        try {
            Socket conexao = new Socket("127.0.0.1", 4000);
            socket = conexao;

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Cominication.startComunication(socket,objectOutputStream,objectInputStream);
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void setInterfaceGrafica(InterfaceGrafica tela) throws IOException {
        cominication = new Cominication();
        cominication.setInterfaceGrafica(tela);
        Thread thread = new Thread(cominication);
        thread.start();
    }
}