package Editor;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Classe responsável pela interface gráfica da tela inicial do programa e suas funções, isto é,
 * o menu inicial que aparece para o cliente quando o programa é rodado;
 */
public class TelaInicial extends JFrame{

    private JPanel painel = new JPanel(); //painel para se adicionar os botões
    private JButton novo = new JButton("Novo"); //botao para a abertura de um novo arquivo de texto no servidor
    private JButton upload = new JButton("Upload"); //botao para enviar um arquivo de texto da maquina do cliente para o servidor
    private JButton abrir = new JButton("Abrir arquivo"); //botao para abrir um arquivo de texto pre-existente no servidor
    private JButton sair = new JButton("Sair"); //fecha o programa


    /**
     * Contrutor da tela inicial. Organiza e monta a interface grafica da tela inicial e implementa as funcoes
     * de seus botoes de acordo com as interacoes do cliente.
     */
    public TelaInicial(){
        super("Editor de texto");
        this.setLayout(new GridLayout(1,4)); //ajusta o layout da janela
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //habilita o fechamento da janela
        this.setVisible(true); //permite que a janela apareça na tela
        this.setSize(500, 100); //ajusta o tamanho da janela
        this.setLocationRelativeTo(null); //centraliza a janela

        //adicionam-se os botoes a interface grafica
        this.add(novo);
        this.add(upload);
        this.add(abrir);
        this.add(sair);

        //habilita o botao sair a fechar o programa
        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { System.exit(0); }
        });

        // Novo Arquivo
        novo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Comunication.novo() != 1) //chama a funcao de abertura de arqiuvo no servidor e verifica se houve algum erro
                    return;

                InterfaceGrafica interfaceGrafica = new InterfaceGrafica(); //abre a janela da interface do editor de texto
                try {
                    ClientConect.setInterfaceGrafica(interfaceGrafica);
                } catch (IOException ex) { //verifica se foi gerada uma excessao
                    JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                }
                TelaInicial.getFrames()[0].setVisible(false); //tira da tela a janela de tela inicial
            }
        });

        // Upload de arquivo para o servidor
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = Comunication.upload(); //chama a funcao que fara o uplload do texto do arquivo de desejo

                if(texto.equals("-1"))
                    return;

                InterfaceGrafica interfaceGrafica = new InterfaceGrafica(); //abre a janela da interface do editor de texto
                try {
                    ClientConect.setInterfaceGrafica(interfaceGrafica); //setta a interface grafica
                } catch (IOException ex) { //verifica se foi gerada uma excessao
                    JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                }
                interfaceGrafica.setTextArea(texto); //passa o texto do arquivo do uplload para a area de texto da interface do editor
                TelaInicial.getFrames()[0].setVisible(false); //tira da tela a janela do menu inicial
            }
        });

        // Abrir arquivo do servidor
        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = Comunication.abrir(); //chama a funcao de abertura de arquivo

                if (texto.equals("-1")) //verifica se houve algum erro
                    return;

                InterfaceGrafica interfaceGrafica = new InterfaceGrafica(); //abre a janela da interface do editor de texto
                try{
                    ClientConect.setInterfaceGrafica(interfaceGrafica); //setta a interface
                } catch (IOException ex) { //verifica se foi gerada uma excessao
                    JOptionPane.showMessageDialog(null,e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                interfaceGrafica.setTextArea(texto); //passa o texto do arquivo aberto para a area de texto da interface do editor
                TelaInicial.getFrames()[0].setVisible(false); //tira a tela inicial da tela
            }
        });

    }
}
