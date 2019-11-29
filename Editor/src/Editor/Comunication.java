package Editor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.Socket;

public class Comunication implements Runnable{

    private static Socket socket; //mecanismo de comunicacao do servidor
    private InterfaceGrafica interfaceGrafica;
    private static ObjectInputStream inputForward; //objeto que recebe as informacoes do servidor
    private static ObjectOutputStream outputForward; //objeto que envia as informacoes para o servidor


    @Override
    /**
     * metodo que roda a thread
     */
    public void run() {
        while (true){
            String input = null;
            try {
                input = inputForward.readUTF();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

            switch (input){
                case "numEdit":
                    try {
                        int numEdit = inputForward.readInt();
                        String texto = "Usuários editando: " + numEdit;
                        this.interfaceGrafica.setNumEdit(texto);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                break;
                case "setText":
                    try {
                        String texto = inputForward.readUTF();
                        interfaceGrafica.setTextArea(texto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
            }

        }
    }

    /**
     * imoorta os dados necessarios para o estabelecimento da comunicacao entre cliente e servidor
     * da classe ClientConect (o Socket, o ObjectOutputStream e o ObjectInputStream)
     * @param conexao socket estabelecido em ClientConect
     * @param output objeto de envio de informacoes para o servidor
     * @param input objeto de leitura de informacoes enviadas pelo servidor
     */
    public static void startComunication(Socket conexao, ObjectOutputStream output, ObjectInputStream input){
        //atribui os dados recebidos as variaveis estaticas de mesmo tipo da classe comunication
        socket = conexao;
        outputForward = output;
        inputForward = input;
    }

    /**
     * setta uma interface grafica do editor de texto recebida por parametro
     * @param interfaceGrafica interface do editor de texto a ser settada
     */
    public void setInterfaceGrafica(InterfaceGrafica interfaceGrafica){
        this.interfaceGrafica = interfaceGrafica;
    }

    /**
     * metodo responsavel pela abertura de um novo arquivo no servidor
     * @return retorna um inteiro indicando se o processo ocorreu com sucesso (1) ou nao (-1)
     */
    public static int novo(){
        try {
            outputForward.writeUTF("novo"); //envia o comando para abertura de novo arquivo
            String resposta = JOptionPane.showInputDialog(TelaInicial.getFrames()[0],"Nome do Arquivo:");
            //abre uma janela momentanea para que o usuario digite o nome a ser dado ao arquivo

            if(resposta!=null) { //verifica se houve resposta

                outputForward.writeUTF(resposta); //envia o nome do arquivo para o servidor
                outputForward.flush(); //limpa o objectOutputStream

                switch (inputForward.readInt()){ //verifica se houve algum erro no processo de abertura do arquivo
                    case -1:
                        JOptionPane.showMessageDialog(TelaInicial.getFrames()[0],"Erro ao criar o arquivo!","Erro", JOptionPane.ERROR_MESSAGE);
                        return -1;
                    case -2:
                        JOptionPane.showMessageDialog(TelaInicial.getFrames()[0],"Arquivo já existente no servidor","Erro", JOptionPane.ERROR_MESSAGE);
                        return -1;
                    default:
                        return 1; //mostra que o processo foi concluido com sucesso
                }
            }

            return -1;

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(TelaInicial.getFrames()[0],ex.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    /**
     * metodo responsavel por fazer o upload de um arquivo de texto da maquina do usuario
     * para o servidor do editor de texto.
     * @return retorna uma string contendo o texto do arquivo submetido ao upload
     * ou com "-1", indicando que houve uma erro durante o processo
     */
    public static String upload() {
        try {
            outputForward.writeUTF("upload");//envia o comando de upload para o servidor
            outputForward.flush(); //limpa o objectoutputstream
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        //janela para escolha do arquivo a ser submetido ao upload
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);

        int resposta = fileChooser.showOpenDialog(null);
        if (resposta == JFileChooser.APPROVE_OPTION) { //verifica se algum arquivo foi selecionado
            try {
                String nome = fileChooser.getSelectedFile().getName(); //pega o nome do arquivo selecionado
                outputForward.writeUTF(nome); //envia o nome do arquivo para o servior
                outputForward.flush();
                resposta = inputForward.readInt();
                if (resposta == -1) { //verifica se houve algum erro no processo
                    JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], "Arquivo já existente no servidor!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return "-1";
                }

                String aux = "", texto = ""; //strings para auxiliar a passagem do texto do arquivo para a area de texto da interface
                FileReader fileReader = new FileReader(fileChooser.getSelectedFile());

                BufferedReader bufferedReader = new BufferedReader(fileReader);

                texto = bufferedReader.readLine(); //a string sl recebe o conteudo da primeira linha do buffer

                while ((aux = bufferedReader.readLine()) != null) { //s1 le uma linha do buffer por vez
                    texto = texto + "\n" + aux; //a linha lida por s1 é acrescentada à string sl com uma quebra de linha
                }

                if (texto == null)
                    texto = "";

                outputForward.writeUTF(texto); //envia o tecto lido para o servidor
                outputForward.flush();

                if (inputForward.read() == -1) {
                    JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], "Erro no upload!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return "-1";
                }
                return texto;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "-1";
            } catch (IOException e) {
                e.printStackTrace();
                return "-1";
            }
        }
        return "-1";
    }

    /**
     * metodo responsavel por abrir um arquivo de texto pré-existente no servidor
     * @return retorna uma string contendo o texto do arquivo aberto
     * ou com "-1", indicando que houve uma erro durante o processo
     */
    public static String abrir(){
        try {
            outputForward.writeUTF("abrir"); //envia para o servidor o comando de abertura
            outputForward.flush();

            String mensagem = inputForward.readUTF(); //le uma lista dos arquivos disponiveis no servidor
            //le do usuario o indice do arquivo do servidor que ele deseja abrir
            String resposta = JOptionPane.showInputDialog(TelaInicial.getFrames()[0],"Digite o índice do arquivo: \n"+mensagem);

            if(resposta == null) //verifica se houve resposta
                return "-1";

            outputForward.write(Integer.parseInt(resposta)); //envia para o servidor o indice do arquivo que se deseja abrir
            outputForward.flush();

            String texto = inputForward.readUTF(); //le o texto do arquivo em questao

            if(texto != null && texto.equals("-1")){
                JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], "Erro na abertura do arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
                return "-1";
            }

            return texto; //retorna o texto do arquivo

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return "-1";
        }
    }

    /**
     * metodo responsavel por enviar o texto que o cliente esta editando para o servidor
     * @param texto //conteudo de texto a ser enviado para o servidor
     * @return retorna um inteiro para indicar se o processo obteve sucesso(1) ou nao(-1)
     * @throws IOException
     */
    public static int enviarTexto(String texto) throws IOException {
        outputForward.writeUTF("texto"); //envia para o servidor o comando de envio de texto
        outputForward.flush();
        outputForward.writeUTF(texto); //envia o texto editado pelo cliente para o servidor
        outputForward.flush();
        int resposta = inputForward.readInt(); //verifica se houve lgum erro no processo
        if(resposta != 1){
            return -1;
        }
        return 1;
    }
}
