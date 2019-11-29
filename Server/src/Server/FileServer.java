package Server;

import java.util.ArrayList;

/**
 * classe de tipagem dos arquivos do servidor, contendo dados do arquivo e a lista de
 * clientes que estao editando o arquivo
 */
public class FileServer {
    private String name; //nome do arquivo
    private ArrayList<Client> clients = new ArrayList<Client>(); //lista de clientes conectados

    /**
     * setta o nome do arquivo
     * @param name string contendo o nome do arquivo em questao
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setta a lista de clientes editando o arquivo
     * @param clients array list a ser settada, de tipos Clients
     */
    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    /**
     * pega o nome do arquivo
     * @return retorna uma string com o nome pego
     */
    public String getName() {
        return name;
    }
    /**
     * pega a arraylist com os clientes editando o arquivo
     * @return retorna a arraylist
     */
    public ArrayList<Client> getClients() {
        return clients;
    }

    /**
     * adiciona um cliente a lista de clientes que estao editando o arquivo
     * @param client cliente a ser adicionado a array list
     */
    public void addClient(Client client){
        this.clients.add(client);
    }

}
