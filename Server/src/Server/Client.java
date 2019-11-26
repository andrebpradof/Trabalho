package Server;

import java.io.FileReader;

/**
 * modelo de cliente do servidor
 */
public class Client {

    private FileServer fileServer;
    private String name; //nome do usuario

    /**
     * retorna um fileServer
     * @return retorna um fileServer
     */
    public FileServer getFileServer() {
        return fileServer;
    }

    /**
     * retorna uma string com o nome do usuario
     * @return retorna a string com o nome do usuario
     */
    public String getName() {
        return name;
    }

    /**
     * seta o fileserver
     * @param fileServer - usa como parametro o fileServer a ser setado
     */
    public void setFileServer(FileServer fileServer) {
        this.fileServer = fileServer;
    }

    /**
     * seta a string name
     * @param name - usa como parametro a string name a ser setada
     */
    public void setName(String name) {
        this.name = name;
    }


}
