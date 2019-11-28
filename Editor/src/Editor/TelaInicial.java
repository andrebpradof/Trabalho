package Editor;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
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
                if(Comunication.novo() != 1)
                    return;
                InterfaceGrafica interfaceGrafica = new InterfaceGrafica();
                try {
                    ClientConect.setInterfaceGrafica(interfaceGrafica);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return;
                }
                TelaInicial.getFrames()[0].setVisible(false);
            }
        });

        // Upload de arquivo para o servidor
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = Comunication.upload();

                if(texto.equals("-1"))
                    return;

                InterfaceGrafica interfaceGrafica = new InterfaceGrafica();
                try {
                    ClientConect.setInterfaceGrafica(interfaceGrafica);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                TelaInicial.getFrames()[0].setVisible(false);
                interfaceGrafica.setTextArea(texto);
                TelaInicial.getFrames()[0].setVisible(false);

            }
        });

        // Abrir arquivo do servidor
        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String texto = Comunication.abrir();

                if (texto.equals("-1"))
                    return;

                InterfaceGrafica interfaceGrafica = new InterfaceGrafica();
                try{
                    ClientConect.setInterfaceGrafica(interfaceGrafica);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return;
                }

                TelaInicial.getFrames()[0].setVisible(false);
                interfaceGrafica.setTextArea(texto);
                TelaInicial.getFrames()[0].setVisible(false);
            }
        });

    }
}
