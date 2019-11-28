package Server;

import java.util.ArrayList;

/**
 * Classe modelo de arquivo do servidor.
 * Contem todos os atributos e metodos diretamente relacionados aos arquivos manipulados pelo servidor
 */
public class FileServer {
    private String name; //nome do arquivo
    private ArrayList<Client> clients = new ArrayList<Client>(); //lista de clientes conectados

    /**
     * seta a string com o nome do arquivo
     * @param name - string com nome do arquivo
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * seta a lista dos clientes conectados
     * @param clients ArrayList de clients
     */
    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    /**
     * pega o nome do arquivo
     * @return retorna a string name do nome do arquivo
     */
    public String getName() {
        return name;
    }


    public void addClient(Client client){
        this.clients.add(client);
    }

}
