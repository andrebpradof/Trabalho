package Server;

import java.util.ArrayList;


public class FileServer {
    private String name; //nome do arquivo
    private ArrayList<Client> clients = new ArrayList<Client>(); //lista de clientes conectados


    public void setName(String name) {
        this.name = name;
    }


    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void addClient(Client client){
        this.clients.add(client);
    }

}
