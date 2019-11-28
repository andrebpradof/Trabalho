/*
    André Baconelo Prado Furlanetti - 10748305
    Marcelo Magalhães Coelho        - 10716633

*/

package Editor;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * Classe com a interface grafica, suas funcionalidades de edicao de texto e
 * comando de fechamento e as chamadas para as funcionalidades de manipulaçãod e arquivos.
 */

public class InterfaceGrafica extends JFrame{

    private JMenu menuFile = new JMenu("Arquivo"); //menu de manipulação de arquivos
    private JMenu menuEdit = new JMenu("Editar"); //menu de edição de texto
    private JMenu menuOpcao = new JMenu("Opções"); //menu de opções
    private JMenu menuNumEdit = new JMenu("Usuários editando: 1");
    private JMenuItem menuItemSair = new JMenuItem("Sair"); //botão para sair do editor
    private JMenuBar barra = new JMenuBar(); //barra de menus
    private JMenuItem menuItemNovo = new JMenuItem("Novo"); //botao para iniciar uma nova edição
    private JMenuItem menuItemUpload = new JMenuItem("Upload"); // botao para upar um arquivo no servidor
    private JMenuItem menuItemColar = new JMenuItem("Colar"); //botao para colar texto copiado ou recortado
    private JMenuItem menuItemAbrir = new JMenuItem("Abrir"); //botao para abrir no editor o texto de um arquivo preexistente
    private JMenuItem menuItemDesfazer = new JMenuItem("Desfazer"); //botao para desfazer a ultima ação de edição nao desfeita
    private JMenuItem menuItemRefazer = new JMenuItem("Refazer"); //botao para refazer a ultima ação de edição desfeita e nao refeita
    private JMenuItem menuItemCopiar = new JMenuItem("Copiar"); //botao para copiar um trecho texto selecionado
    private JMenuItem menuItemRecortar = new JMenuItem("Recortar"); //botao que copia o trecho selecionado e o apaga do texto
    private JMenuItem menuItemSelecionar = new JMenuItem("Selecionar"); //botao para selecionar o texto inteiro
    private JScrollPane scrollPane; //painel com barra de rolagem
    private JTextArea textArea = new JTextArea(); //area de texto da interface
    private UndoManager undoManager = new UndoManager(); //ferramenta necessaria para a realizção das rotinas de "Desfazer" e "Refazer

    /** Construtor da classe InterfaceGrafica.
     * Inicializa a interface, estrutura a janela com a area de texto e a barra de ferramentas, com cada um dos
     * menus nela presentes, nos quais sao organizados seus respectivos itens.
     * Implementa as funcionalidades dos itens de edicao de texto e do item "Sair",
     * alem de chamar os metodos da classe "Arqivos" que implementam as funcionalidades
     * dos itens de manipulacao de arquivo.
     */

    public InterfaceGrafica(){
        super("Editor de texto"); //nome que aparece na janela do editor

        this.setLayout(new BorderLayout());

        //adiciona-se os menuItens aos seus respectivos menus
        menuFile.add(menuItemNovo);
        menuFile.add(menuItemUpload);
        menuFile.add(menuItemAbrir);


        menuOpcao.add(menuItemSair);

        //desabilita os botoes desfazer e refazer assim que se abre o editor
        menuItemDesfazer.setEnabled(false);
        menuItemRefazer.setEnabled(false);

        menuEdit.add(menuItemCopiar);
        menuEdit.add(menuItemColar);
        menuEdit.add(menuItemRecortar);
        menuEdit.add(menuItemSelecionar);
        menuEdit.add(menuItemDesfazer);
        menuEdit.add(menuItemRefazer);

        //adiciona-se os menus às suas respectivas barras
        barra.add(menuFile);
        barra.add(menuEdit);
        barra.add(menuOpcao);
        barra.add(menuNumEdit);

        textArea.setEditable(true);//habilita-se a interação do usuario com a area de texto
        textArea.setBorder(new EmptyBorder(5,5,5,5)); //acrescenta-se uma margem à area de texto

        //Organiza-se a estrutura da interface
        this.setJMenuBar(barra);
        scrollPane = new JScrollPane(textArea);
        this.getContentPane().add(scrollPane,BorderLayout.CENTER);

        this.setVisible(true); //habilita-se a visibilidade da interface
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //habilita-se o botao "X" a fechar a interface e encerrar o programa
        this.setSize(800, 700); //declaração do tamanho da janela do editor
        this.setLocationRelativeTo(null); //centraliza a janela do editor na tela

        // Novo arquivo
        menuItemNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Cominication.novo() != 1)
                    return;

                textArea.setText("");
            }
        });

        // Upload de um arquivo para o servidor
        menuItemUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = Cominication.upload();
                if(texto.equals("-1"))
                    return;
                textArea.setText(texto);
            }
        });
        
        // Abrir arquivo do servidor
        menuItemAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = Cominication.abrir();

                if(texto.equals("-1"))
                    return;

                textArea.setText(texto);
            }
        });

        menuItemSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //fecha a interface e encerra o programa
            }
        });

        menuItemCopiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
            } //copia o trecho de texto selecionado
        });

        menuItemColar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            } //cola o ultimo trecho que foi copiado
        });

        menuItemRecortar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.cut();
            } //copia o trecho selecionado e o apaga do texto
        });
/*
*/
        menuItemDesfazer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{ //tenta-se desfazer
                    undoManager.undo();
                }
                catch (CannotUndoException cre){
                    cre.printStackTrace();
                }
                updateMenuItens(); //chama updateMenuItens
            }
        });

        menuItemRefazer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    undoManager.redo(); //refaz a ultima ação de edição desfeita não refeita
                }
                catch (CannotRedoException cre){
                    cre.printStackTrace();
                }
                updateMenuItens(); //chama updateMenuItens
            }
        });

        menuItemSelecionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.selectAll(); //seleciona o texto inteiro da area de texto
            }
        });

        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit()); //adiciona o que foi editado na area de texto à ferramenta undoManager
                updateMenuItens(); //chama updateMenuItens
            }
        });

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    String entrada = textArea.getText();
                    if(Cominication.enviarTexto(entrada) != 1){
                        JOptionPane.showMessageDialog(InterfaceGrafica.getFrames()[0], "Erro no envio do texto!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void updateMenuItens(){ //habilita os botões "Desfazer" e "Refazer" conforme há ações para serem desfeitas ou refeitas
        this.menuItemDesfazer.setEnabled(undoManager.canUndo()); //habilita "Desfazer"
        this.menuItemRefazer.setEnabled(undoManager.canRedo()); //habilita o "Refazer"
    }

    public void setTextArea(String texto) {
        this.textArea.setText(texto);
    }

    public void setNumEdit(String numEdit){
        this.menuNumEdit.setText(numEdit);
    }
}
