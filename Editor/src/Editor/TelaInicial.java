package Editor;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class TelaInicial extends JFrame{

    JPanel painel = new JPanel();
    JButton novoArquivo = new JButton("Novo Arquivo");
    JButton abrirArquivo = new JButton("Abrir");
    JButton sair = new JButton("sair");


    public TelaInicial(){
        super("Editor de texto");
        this.setLayout(new GridLayout(1,3));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(500, 100);
        this.setLocationRelativeTo(null);

        this.add(novoArquivo);
        this.add(abrirArquivo);
        this.add(sair);

        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { System.exit(0); }
        });

        /*novoArquivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }*/
        });

    }
}
