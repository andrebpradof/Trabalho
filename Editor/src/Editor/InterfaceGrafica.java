/*
    André Baconelo Prado Furlanetti - 10748305
    Marcelo Magalhães Coelho        - 10716633

*/

package Editor;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
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
    private JMenuItem menuItemSair = new JMenuItem("Sair"); //botão para sair do editor
    private JMenuBar barra = new JMenuBar(); //barra de menus
    private JMenuItem menuItemNovo = new JMenuItem("Novo"); //botao para iniciar uma nova edição
    private JMenuItem menuItemColar = new JMenuItem("Colar"); //botao para colar texto copiado ou recortado
    private JMenuItem menuItemAbrir = new JMenuItem("Abrir"); //botao para abrir no editor o texto de um arquivo preexistente
    private JMenuItem menuItemSalvar = new JMenuItem("Salvar"); //botao para salvar em um arquivo o texto editado
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
        menuFile.add(menuItemAbrir);
        menuFile.add(menuItemSalvar);

        menuOpcao.add(menuItemSair);

        //desabilita os botoes desfazer e refazer assim que se abre o editor
        menuItemDesfazer.setEnabled(false);
        menuItemRefazer.setEnabled(false);

        menuEdit.add(menuItemCopiar);
        menuEdit.add(menuItemColar);
        menuEdit.add(menuItemRecortar);
        menuEdit.add(menuItemSelecionar);
      //  menuEdit.add(menuItemRemover);
        menuEdit.add(menuItemDesfazer);
        menuEdit.add(menuItemRefazer);

        //adiciona-se os menus às suas respectivas barras
        barra.add(menuFile);
        barra.add(menuEdit);
        barra.add(menuOpcao);

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

        menuItemNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Arquivos.novo(textArea);
            } //chama-se o metodo Arquivos.novo quando o usuario clica em "Novo"
        });

        menuItemAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Arquivos.abrir(textArea);
            } //chama-se o metodo Arquivos.abrir quando o usuario clica em "Abrir"
        });

        menuItemSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Arquivos.salvar(textArea);
            } //chama-se o metodo Arquivos.salvar quando o usuario clica em "Salvar"
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
    }

    private void updateMenuItens(){ //habilita os botões "Desfazer" e "Refazer" conforme há ações para serem desfeitas ou refeitas
        //this.menuItemDesfazer.setText(undoManager.getUndoPresentationName());
        //this.menuItemRefazer.setText(undoManager.getRedoPresentationName());
        this.menuItemDesfazer.setEnabled(undoManager.canUndo()); //habilita "Desfazer"
        this.menuItemRefazer.setEnabled(undoManager.canRedo()); //habilita o "Refazer"
    }

    public static void main(String[] args){

        InterfaceGrafica interfaceGrafica = new InterfaceGrafica(); //Abre uma interfaceGrafica
    }
}
