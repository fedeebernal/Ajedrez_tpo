package ajedrez.nucleo;

/**
 * Cómo se arma el tablero al empezar. Separarlo permite otras variantes
 * (otro tamaño, otras piezas) sin tocar Partida ni Tablero.
 */
public interface DisposicionInicial {
    Tablero crearTablero();
}
