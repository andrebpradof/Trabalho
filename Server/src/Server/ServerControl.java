package Server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ServerControl {
    private static ArrayList<Client> clientArrayList = new ArrayList<Client>();
    private static ArrayList<FileServer> fileServerArrayList = new ArrayList<FileServer>();

    public static ArrayList<Client> getClientArrayList() {
        return clientArrayList;
    }

    public static ArrayList<FileServer> getFileServerArrayList() {
        return fileServerArrayList;
    }

    public static void addClient(Client client){
        clientArrayList.add(client);
    }

    public static void addFile(FileServer fileServer){
        fileServerArrayList.add(fileServer);
    }

    public static int newFile(String name, Client client) throws IOException {
        File file = new File(name+".txt");
        if(!file.exists()){
            file.createNewFile();
            FileServer fileServer = new FileServer();

            fileServer.setName(name);
            fileServerArrayList.add(fileServer);

            return fileServerArrayList.lastIndexOf(fileServer);
        }
        else{
            System.out.println("Esse arquivo já existe nesse diretório");
            return -1;
        }
    }
}
