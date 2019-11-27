package Editor;

import java.io.*;
import java.util.LinkedList;

import javax.swing.*;

/**
 * Classe que responsavel pelas manipulacoes de arquivos para leitura de arquivos de texto e armazenamento
 * dos textos editados pelo usuario, bem como abertura de novos arquivos.
 * Essa classe será inteiramente incorporado no servidor na parte três
 */

public class Arquivos implements Serializable {
    private static JFileChooser fileChooser; //janela de seleção de arquivo ou pasta na maquina do usuario
    private static File file; //arquivo a ser manipulado
    private static FileWriter fileWriter; //ferramenta para salvar arquivo
    private static BufferedWriter bufferedWriter; //buffer em que o texto editado é temporariamente armazenado enquanto é salvo no arquivo.
    private static FileReader fileReader; //ferramenta de abertura de arquivo
    private static BufferedReader bufferedReader; //buffer em que o texto do arquivo é temporariamente armazenado enquanto é aberto no editor.

    /**
     * Exibe na tela uma mensagem que pergunta se o usuario tem a intencao de salvar o arquivo novo arquivo
     * aberto apos ler o clique no item "Novo" do menu "Arquivo".
     * Em caso de resposta afirmativa, abre-se a janela para que o usuario selecione onde deseja
     * salvar o arquivo, tendo a opcao de cancelar a operacao.
     * Ja para resposta negativa, a mensagem apenas deixara a tela o novo arquivo esta pronto para
     * ser editado, porem sem estar salvo na maquina do usuario.
     * Independentemente da resposta, a area de texto da interface grafica e limpa.
     * @param textArea Area de texto da interface grafica.
     */
    public static void saveQuestion(JTextArea textArea){
        if(textArea.getText().equals("")){ //se o texto está em branco, retorna-se à edição.
            textArea.setText("");
        }
        else {
            int resposta = JOptionPane.showConfirmDialog(InterfaceGrafica.getFrames()[0], "Gostaria de salvar o arquivo?", "Novo", JOptionPane.YES_NO_OPTION);
            if(resposta == 0) //caso o usuario confirme a intenção de salvar o texto:
                salvar(textArea); //chama a função salvar
            textArea.setText(""); //limpa a area de texto
        }
    }

    /**
     * Funcao que cria um novo projeto de texto.
     * Este metodo chama a funcao saveQuestion, passando como parametro a textArea que recebeu.
     * Sendo assim a area de texto sera limpa, permitindo o inicio do novo projeto.
     * @param textArea Area de texto da interface grafica, que sera onde se iniciara o novo texto.
     */
    public static void novo(JTextArea textArea){
        saveQuestion(textArea);
    }

    /**
     * Funcao que permite ao usuario salvar o texto editado em um arquivo em sua maquina.
     * A funcao abre uma janela com a estrutura de pastas e arquivos da maquina do usuario para que ele possa
     * escolher onde salvara o arquivo e digitar o nome que deseja dar ao mesmo.
     * Uma vez que isso esteja definido, quando o usuario clica em salvar o arquivo e salvo
     * de acordo com esses dados. Caso o usuario clique no botao "cancel" da janela a qualquer momento
     * aparece uma mensagem indicando que a ooperacao foi cancelada e nada e salvo, retornando a edicao do texto.
     * @param textArea Area de texto da interface contendo o texto que se deseja salvar.
     */
    public static void salvar(JTextArea textArea){
        fileChooser = new JFileChooser("f:"); //o usuario seleciona o arquivo a ser lido na janela JFileChooser

        int resposta = fileChooser.showSaveDialog(null);

        if (resposta == JFileChooser.APPROVE_OPTION) { //caso o usuario clique em "Save"

            file = new File(fileChooser.getSelectedFile().getAbsolutePath());//usuario seleciona onde deseja salvar o arqivo e que nome dá-lo

            try { //o texto é salvo no novo arquivo
                fileWriter = new FileWriter(file, true);

                bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(textArea.getText());

                bufferedWriter.flush();
                bufferedWriter.close();
            }
            catch (Exception evt) {//Exibe uma mensagem de erro caso algo dê errado no salvamento
                JOptionPane.showMessageDialog(InterfaceGrafica.getFrames()[0], evt.getMessage());
            }
        }
        //Caso o usuario cancele a operação uma mensagem é exibida
        else
            JOptionPane.showMessageDialog(InterfaceGrafica.getFrames()[0], "Operação cancelada");
    }

    /**
     * Funcao que possibilita a abertura no editor de um texto presente em um arquivo preexistente na maquina do usuario.
     * Abre-se a uma janela contendo as pastas e arquivos da maquina do usuario, sendo que quando selecionado um arqivo,
     * ao clicar-se no botao "Open", o texto do arquivo e transferido para a area de texto.
     * @param textArea Area de texto da interface, para onde deseja-se transferir o texto do arquivo aberto.
     */
    public static void abrir(JTextArea textArea){
            fileChooser = new JFileChooser("f:"); //o usuario seleciona o arquivo que deseja abrir na janela JFileChooser.
            int resposta = fileChooser.showOpenDialog(null);
            if (resposta == JFileChooser.APPROVE_OPTION) { //caso o usuario clique em "Open"
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                try {
                    String s1 = "", sl = ""; //strings para auxiliar a passagem do texto do arquivo para a area de texto da interface
                    fileReader = new FileReader(file);

                    bufferedReader = new BufferedReader(fileReader);

                    sl = bufferedReader.readLine(); //a string sl recebe o conteudo da primeira linha do buffer

                    while ((s1 = bufferedReader.readLine()) != null) { //s1 le uma linha do buffer por vez
                        sl = sl + "\n" + s1; //a linha lida por s1 é acrescentada à string sl com uma quebra de linha
                    }
                    //sl agora tem o texto completo do arquivo

                    textArea.setText(sl); //o texto de sl é adicionado à area de texto da interface.
                }
                catch (Exception evt) { //caso haja algum nerro na abertura do arquivo, uma mensagem de erro é exibida
                    JOptionPane.showMessageDialog(InterfaceGrafica.getFrames()[0], evt.getMessage());
                }
            }
            //caso o usuario cancele a operação, uma mensagem é exibida e retorna-se ao editor.
            else
                JOptionPane.showMessageDialog(InterfaceGrafica.getFrames()[0], "Operação cancelada");
    }
}
