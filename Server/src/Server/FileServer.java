package Server;

import java.util.ArrayList;

/**
 * Classe modelo de arquivo do servidor.
 * Contem todos os atributos e metodos diretamente relacionados aos arquivos manipulados pelo servidor
 */
public class FileServer {
    private String name; //nome do arquivo
    private String directory;  //diretorio do arquivo
    private ArrayList<Client> clients; //lista de clientes conectados
    private long size; //tamanho do arquivo
    private String modificationDate; //data de modificação
    private String creator; //nome do criador do arquivo

    /**
     * Inicializa um FileServer de tamanho 0
     */
    public FileServer(){
        this.size = 0;
    }

    /**
     * seta a string com o nome do arquivo
     * @param name - string com nome do arquivo
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * seta a string do diretorio do arquivo
     * @param directory - string do diretorio do arquivo
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * seta a lista dos clientes conectados
     * @param clients ArrayList de clients
     */
    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    /**
     * seta o tamanho do arquivo
     * @param size long do tamanho do arquivo
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * seta a data de modificacao do arquivo
     * @param modificationDate string de data de modificacao
     */
    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
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
     * pega o nome do diretorio
     * @return retorna a string directory do nome do diretorio
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * pega a lista de clientes conectados
     * @return retorna uma ArrayList dos clientes conectados
     */
    public ArrayList<Client> getClients() {
        return clients;
    }

    /**
     * pega o tamanho do arquivo
     * @return retorna um long com o tamanho do arquivo
     */
    public long getSize() {
        return size;
    }

    /**
     * pega data de modificacao do arquivo
     * @return retorna uma string com data de modificacao do arquivo
     */
    public String getModificationDate() {
        return modificationDate;
    }

    /**
     * pega o nome do criador do arquivo
     * @return retorna uma string com o nome do criador do arquivo
     */
    public String getCreator() {
        return creator;
    }
}
