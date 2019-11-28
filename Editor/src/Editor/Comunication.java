package Editor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.Socket;

public class Comunication implements Runnable{

    private static Socket socket;
    private InterfaceGrafica interfaceGrafica;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;

    @Override
    public void run() {
        while (true){
            String input = null;
            try {
                input = objectInputStream.readUTF();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

            switch (input){
                case "numEdit":
                    try {
                        int numEdit = objectInputStream.readInt();
                        String texto = "Usuários editando: " + numEdit;
                        this.interfaceGrafica.setNumEdit(texto);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                break;
                case "setText":
                    try {
                        String texto = objectInputStream.readUTF();
                        interfaceGrafica.setTextArea(texto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
            }

        }
    }

    public static void startComunication(Socket conexao, ObjectOutputStream output, ObjectInputStream input){
        socket = conexao;
        objectOutputStream = output;
        objectInputStream = input;
    }

    public void setInterfaceGrafica(InterfaceGrafica interfaceGrafica) throws IOException {
        this.interfaceGrafica = interfaceGrafica;
    }

    public static int novo(){
        try {
            objectOutputStream.writeUTF("novo");
            String resposta = JOptionPane.showInputDialog(TelaInicial.getFrames()[0],"Nome do Arquivo:");

            if(resposta!=null) {

                objectOutputStream.writeUTF(resposta);
                objectOutputStream.flush();


                switch (objectInputStream.readInt()){
                    case -1:
                        JOptionPane.showMessageDialog(TelaInicial.getFrames()[0],"Erro ao criar o arquivo!","Erro", JOptionPane.ERROR_MESSAGE);
                        return -1;
                    case -2:
                        JOptionPane.showMessageDialog(TelaInicial.getFrames()[0],"Arquivo já existente no servidor","Erro", JOptionPane.ERROR_MESSAGE);
                        return -1;
                    default:
                        return 1;
                }
            }

            return -1;

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(TelaInicial.getFrames()[0],ex.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    public static String upload() {
        try {
            objectOutputStream.writeUTF("upload");
            objectOutputStream.flush();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);

        int resposta = fileChooser.showOpenDialog(null);
        if (resposta == JFileChooser.APPROVE_OPTION) {
            try {
                String nome = fileChooser.getSelectedFile().getName();
                objectOutputStream.writeUTF(nome);
                objectOutputStream.flush();
                resposta = objectInputStream.readInt();
                if (resposta == -1) {
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

                objectOutputStream.writeUTF(texto);
                objectOutputStream.flush();

                if (objectInputStream.read() == -1) {
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

    public static String abrir(){
        try {
            objectOutputStream.writeUTF("abrir");
            objectOutputStream.flush();

            String mensagem = objectInputStream.readUTF();

            String resposta = JOptionPane.showInputDialog(TelaInicial.getFrames()[0],"Digite o índice do arquivo: \n"+mensagem);

            if(resposta == null)
                return "-1";

            objectOutputStream.write(Integer.parseInt(resposta));
            objectOutputStream.flush();

            String texto = objectInputStream.readUTF();

            if(texto != null && texto.equals("-1")){
                JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], "Erro na abertura do arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
                return "-1";
            }

            return texto;

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return "-1";
        }
    }

    public static int enviarTexto(String texto) throws IOException {
        objectOutputStream.writeUTF("texto");
        objectOutputStream.flush();
        objectOutputStream.writeUTF(texto);
        objectOutputStream.flush();
        if(objectInputStream.readInt() != 1){
            return -1;
        }
        return 1;
    }
}
