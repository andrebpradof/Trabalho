package Editor;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * clase responsavel por rodar a conexao do cliente com o servidor e settar a interface grafica do editor de texto
 */
public class ClientConect {

    private static Comunication comunication;
    private static Socket socket;

    /**
     * realiza a conexao do cliente com o servidor
     * @throws IOException
     */
    public static void conectar() throws IOException {

        try {
            Socket conexao = new Socket("127.0.0.1", 4000); //abre um socket ligado a porta preestabelecida 4000 do servidor local
            socket = conexao;

            //inicializa os objetos de output e input que estabeleceram a comunicacao do cliente com o servidor
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Comunication.startComunication(socket,objectOutputStream,objectInputStream);
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * funcao para settar uma nova interface grafica do esditor de texto e inicia uma thread que ficara escutando o servidor
     * @param tela - interface grafica a ser settada
     * @throws IOException
     */
    public static void setInterfaceGrafica(InterfaceGrafica tela) throws IOException {
        comunication = new Comunication();
        comunication.setInterfaceGrafica(tela);
        Thread thread = new Thread(comunication); //thread responsavel por ficar escutando o servidor
        thread.start(); //da-se inicio a thread
    }
}