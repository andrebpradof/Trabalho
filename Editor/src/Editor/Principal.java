package Editor;

import java.io.IOException;

public class Principal {
    public static void main(String[] args) throws IOException {
        ClientConect.conectar();
        Thread telas = new Thread(new InterfaceControl());
        telas.start();
    }
}
