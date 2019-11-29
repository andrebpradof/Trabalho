package Editor;

import java.io.IOException;

/**
 * Classe com o main.
 */
public class Principal {
    public static void main(String[] args) throws IOException {
        ClientConect.conectar(); // faz a conexão com o servidor
        Thread telas = new Thread(new InterfaceControl()); // Inicia a thread da interface
        telas.start(); // Start na thread
    }
}
