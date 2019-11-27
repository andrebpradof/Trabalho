package Editor;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class TelaInicial extends JFrame{

    private JPanel painel = new JPanel();
    private JButton novo = new JButton("Novo");
    private JButton abrir = new JButton("Abrir arquivo");
    private JButton sair = new JButton("Sair");



    public TelaInicial(){
        super("Editor de texto");
        this.setLayout(new GridLayout(1,3));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(400, 100);
        this.setLocationRelativeTo(null);

        this.add(novo);
        this.add(abrir);
        this.add(sair);

        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { System.exit(0); }
        });

        novo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    ClientConect.getObjectOutputStream().writeUTF("novo arquivo");
                    String resposta = JOptionPane.showInputDialog(null,"Nome do Arquivo:");
                    if(resposta!=null) {
                        ClientConect.getObjectOutputStream().writeUTF(resposta);
                        if (ClientConect.getObjectInputStream().read()!=-1) {
                            InterfaceGrafica interfaceGrafica = new InterfaceGrafica();
                            TelaInicial.getFrames()[0].setVisible(false);
                        }else{
                            JOptionPane.showMessageDialog(null,"Erro na criação do arquivo!","Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser = new JFileChooser("f:");
                int resposta = fileChooser.showOpenDialog(null);
                if (resposta == JFileChooser.APPROVE_OPTION) {
                    try {
                        ClientConect.getObjectOutputStream().writeUTF(fileChooser.getSelectedFile().getAbsolutePath());
                        String texto = ClientConect.getObjectInputStream().readUTF();
                        if (!texto.equals("-1")) {
                            InterfaceGrafica interfaceGrafica = new InterfaceGrafica();
                            TelaInicial.getFrames()[0].setVisible(false);
                            interfaceGrafica.setTextArea(texto);
                        }else{
                            JOptionPane.showMessageDialog(null,"Erro na abertura do arquivo!","Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
                TelaInicial.getFrames()[0].setVisible(false);
            }
        });

    }
}
