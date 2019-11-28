package Editor;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TelaInicial extends JFrame{

    private JPanel painel = new JPanel();
    private JButton novo = new JButton("Novo");
    private JButton upload = new JButton("Upload");
    private JButton abrir = new JButton("Abrir arquivo");
    private JButton sair = new JButton("Sair");



    public TelaInicial(){
        super("Editor de texto");
        this.setLayout(new GridLayout(1,4));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(500, 100);
        this.setLocationRelativeTo(null);

        this.add(novo);
        this.add(upload);
        this.add(abrir);
        this.add(sair);

        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { System.exit(0); }
        });

        // Novo Arquivo
        novo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    ClientConect.getObjectOutputStream().writeUTF("novo");
                    String resposta = JOptionPane.showInputDialog(TelaInicial.getFrames()[0],"Nome do Arquivo:");

                    if(resposta!=null) {

                        ClientConect.getObjectOutputStream().writeUTF(resposta);
                        ClientConect.getObjectOutputStream().flush();


                        switch (ClientConect.getObjectInputStream().readInt()){
                            case -1:
                                JOptionPane.showMessageDialog(TelaInicial.getFrames()[0],"Erro ao criar o arquivo!","Erro", JOptionPane.ERROR_MESSAGE);
                            break;
                            case -2:
                                JOptionPane.showMessageDialog(TelaInicial.getFrames()[0],"Arquivo já existente no servidor","Erro", JOptionPane.ERROR_MESSAGE);
                            break;
                            default:
                                InterfaceGrafica interfaceGrafica = new InterfaceGrafica();
                                TelaInicial.getFrames()[0].setVisible(false);
                            break;
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(TelaInicial.getFrames()[0],ex.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // Upload de arquivo para o servidor
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ClientConect.getObjectOutputStream().writeUTF("upload");
                    ClientConect.getObjectOutputStream().flush();
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
                        ClientConect.getObjectOutputStream().writeUTF(nome);
                        ClientConect.getObjectOutputStream().flush();
                        resposta = ClientConect.getObjectInputStream().readInt();
                        if( resposta == -1){
                            JOptionPane.showMessageDialog(TelaInicial.getFrames()[0],"Arquivo já existente no servidor!","Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        String aux = "", texto = ""; //strings para auxiliar a passagem do texto do arquivo para a area de texto da interface
                        FileReader fileReader = new FileReader(fileChooser.getSelectedFile());

                        BufferedReader bufferedReader = new BufferedReader(fileReader);

                        texto = bufferedReader.readLine(); //a string sl recebe o conteudo da primeira linha do buffer

                        while ((aux = bufferedReader.readLine()) != null) { //s1 le uma linha do buffer por vez
                            texto = texto + "\n" + aux; //a linha lida por s1 é acrescentada à string sl com uma quebra de linha
                        }

                        ClientConect.getObjectOutputStream().writeUTF(texto);
                        ClientConect.getObjectOutputStream().flush();

                        if(ClientConect.getObjectInputStream().read() == -1) {
                            JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], "Erro no upload!", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        InterfaceGrafica interfaceGrafica = new InterfaceGrafica();
                        TelaInicial.getFrames()[0].setVisible(false);
                        interfaceGrafica.setTextArea(texto);
                        TelaInicial.getFrames()[0].setVisible(false);

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Abrir arquivo do servidor
        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ClientConect.getObjectOutputStream().writeUTF("abrir");
                    ClientConect.getObjectOutputStream().flush();

                    String mensagem = ClientConect.getObjectInputStream().readUTF();

                    String resposta = JOptionPane.showInputDialog(TelaInicial.getFrames()[0],"Digite o índice do arquivo: \n"+mensagem);

                    if(resposta == null)
                        return;

                    ClientConect.getObjectOutputStream().write(Integer.parseInt(resposta));
                    ClientConect.getObjectOutputStream().flush();

                    String texto = ClientConect.getObjectInputStream().readUTF();

                    if(texto.equals("-1")){
                        JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], "Erro na abertura do arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    InterfaceGrafica interfaceGrafica = new InterfaceGrafica();
                    TelaInicial.getFrames()[0].setVisible(false);
                    interfaceGrafica.setTextArea(texto);
                    TelaInicial.getFrames()[0].setVisible(false);


                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(TelaInicial.getFrames()[0], ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
}
