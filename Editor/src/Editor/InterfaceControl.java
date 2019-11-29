package Editor;
/**
 * Classe que implementa a thread da tela inicial, permitindo que a janela e suas funcoes rodem
 * e que o servidor trabalhe em paralelo
 */
public class InterfaceControl implements Runnable{
    @Override
    /**
     * metodo que roda a thread da tela inicial
     */
    public void run() {
        TelaInicial telaInicial = new TelaInicial(); //abre a janela da interface da tela inicial
    }
}
