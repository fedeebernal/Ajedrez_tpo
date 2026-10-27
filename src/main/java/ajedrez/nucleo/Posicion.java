package ajedrez.nucleo;

/**
 * Una casilla del tablero. Fila 0 es la fila de las blancas, columna 0 es la columna "a".
 * Es inmutable: moverse devuelve una posición nueva.
 */
public record Posicion(int fila, int columna) {

    public Posicion desplazar(Direccion d) {
        return new Posicion(fila + d.filas(), columna + d.columnas());
    }
}
