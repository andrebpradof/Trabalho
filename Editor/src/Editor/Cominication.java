package Editor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cominication implements Runnable{

    private static Socket socket;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;

    public Cominication(Socket conexao, ObjectOutputStream output, ObjectInputStream input){
        socket = conexao;
        objectOutputStream = output;
        objectInputStream = input;
    }

    @Override
    public void run() {

    }





}
