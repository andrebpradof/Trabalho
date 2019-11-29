package Server;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * recebe os comandos do editor, chama os correspondentes metodos de ServerControl e envia o feedback para o cliente novamente
 */
public class Connect implements Runnable{
    private Client client;
    private ObjectOutputStream outputListen;
    private ObjectInputStream inputListen;
    private Socket socket;

    /**
     * construtor de connect, atribui as variaveis da classe os dados recebidos por parametro
     * @param client cliente a fazer a conexao com o servidor
     * @param socket socket da conexao
     * @param input objectInputStream para receber os comandos do cliente
     * @param output objectOutputStream para enviar as informacoes
     */
    public Connect(Client client, Socket socket, ObjectInputStream input, ObjectOutputStream output) {
        this.client = client;
        this.socket = socket;
        this.inputListen = input;
        this.outputListen = output;
        //atribui os dados recebidos por parametro as variaveis da classe connect
    }

    /**
     * roda a thread e fica escutando o editor, aguardando comandos para que possa realizar as chamadas
     */
    @Override
    public void run() {
        while (true){
            String input = null; //reseta a string
            try {
                input = inputListen.readUTF(); //le o comando do editor
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (input){ //analisa qual foi o comando
                case "novo":
                    try {
                        String nome = inputListen.readUTF();
                        //le o nome do novo arquivo que deseja-se abrir e chama a funcao de abertura em ServerControl
                        outputListen.writeInt(ServerControl.newFile(nome,client));
                        outputListen.flush();

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                    }
                break;
                case "upload":
                    try {
                            String nome = inputListen.readUTF(); //le o nome do arquivo a se fazer o upload
                            if(ServerControl.verificaArquivo(nome) == -1) { //verifica se o arquivo ja existe no servidor
                                outputListen.writeInt(-1);
                                outputListen.flush();
                                break;
                            }
                            outputListen.writeInt(1);
                            outputListen.flush();
                            String texto = inputListen.readUTF();
                            outputListen.write(ServerControl.upload(nome,texto,client));
                            outputListen.flush();

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                    }
                break;
                case "abrir":
                    try {
                        outputListen.writeUTF(ServerControl.listaArquivos()); //envia para o editor a lista de arquivos do servidor com indices
                        outputListen.flush();
                        outputListen.writeUTF(ServerControl.abrir(inputListen.read(),client)); //abre o arquivo do indice de indice enviado pelo editor
                        outputListen.flush();

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                    }
                break;
                case "texto":
                    try {
                        String texto = inputListen.readUTF(); //le o texto editado pelo cliente na area de texto
                        int resposta = ServerControl.recebeTexto(texto,client); //envia o texto para ser salvo
                        outputListen.writeInt(resposta); //envia para o editor um inteiro indicando se o processo deu certo ou se houve um erro
                        outputListen.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
            }
        }
    }
}
