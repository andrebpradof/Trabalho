package Server;

import java.util.ArrayList;

/**
 * Classe modelo de arquivo do servidor.
 * Contem todos os atributos e metodos diretamente relacionados aos arquivos manipulados pelo servidor
 */
public class FileServer {
    private String name; //nome do arquivo
    private ArrayList<Client> clients; //lista de clientes conectados
    private String creator; //nome do criador do arquivo

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
     * seta o nome do criador do arquivo
     * @param creator string do nome do criador do arquivo
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * pega o nome do arquivo
     * @return retorna a string name do nome do arquivo
     */
    public String getName() {
        return name;
    }

    /**
     * pega a lista de clientes conectados
     * @return retorna uma ArrayList dos clientes conectados
     */
    public ArrayList<Client> getClients() {
        return clients;
    }

    /**
     * pega o nome do criador do arquivo
     * @return retorna uma string com o nome do criador do arquivo
     */
    public String getCreator() {
        return creator;
    }
}
