package Editor;

import java.io.Serializable;

public class Caracteres implements Serializable {
    private char caracter;
    private Usuario usuario;

    public Caracteres(char caracter, int user){
        this.caracter = caracter;
        this.usuario = new Usuario(user);
    }

    /**
     * retorna o caracter
     * @return retorna o caracter em um char
     */

    public char getCaracter() {
        return caracter;
    }
}
