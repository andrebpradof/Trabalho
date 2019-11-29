package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * tipagem do cliente do servidor
 */
public class Client {

    private FileServer fileServer; //arquivo que o cliente esta editando
    private String ip; //identificacao do cliente
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    /**
     * construtora de cliente, inicializa os atributos do objeto do tipo cliente, importando os dados obtidos na classe Server
     * @param socket //socket para estabelecer a comunicacao entre cliente e servidor
     * @param objectOutputStream //objeto para envio de informacoes do servidor ao cliente
     * @param objectInputStream  //objeto de recepcao de informacos do cliente no servidor
     */
    public Client(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream){
        this.socket = socket;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        //associa as variaveis do objeto da classe cliente os dados importados por parametro da classe Server
    }

    public void setInputForward(ObjectInputStream inputForward) {
        this.objectInputStream = inputForward;
    }

    public void setOutputForward(ObjectOutputStream outputForward) {
        this.objectOutputStream = outputForward;
    }

    /**
     * pega o socket de estabelecimento da conexao entre o cliente e o servidor
     * @return retorna o socket pego
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * setta o socket passado por parametro
     * @param socket socket a ser settado
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * pega o objectInputStream do servidor
     * @return ObjectIntputStream pego
     */
    public ObjectInputStream getInputForward() {
        return objectInputStream;
    }

    /**
     * pega o objectOutputStream do servidor
     * @return ObjectOutputStream pego
     */
    public ObjectOutputStream getOutputForward() {
        return objectOutputStream;
    }

    /**
     * pega o arquivo do servidor a ser editado
     * @return retorna o FileServer do arquivo em questao
     */
    public FileServer getFileServer() {
        return fileServer;
    }

    /**
     * pega ip do cliente
     * @return retorna o ip pego
     */
    public String getIp() {
        return ip;
    }

    /**
     * setta o arquivo tipo FileServer a ser editado
     * @param fileServer arquivo a ser settado
     */
    public void setFileServer(FileServer fileServer) {
        this.fileServer = fileServer;
    }

    /**
     * setta o ip do cliente
     * @param ip ip do cliente a ser settado
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
}
