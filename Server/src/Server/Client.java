package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    private FileServer fileServer;
    private String ip;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public Client(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream){
        this.socket = socket;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }


    public FileServer getFileServer() {
        return fileServer;
    }

    public String getIp() {
        return ip;
    }

    public void setFileServer(FileServer fileServer) {
        this.fileServer = fileServer;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
