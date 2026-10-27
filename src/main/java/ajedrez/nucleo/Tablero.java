package ajedrez.nucleo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Guarda qué pieza hay en cada casilla. El tamaño se pasa por constructor:
 * nada en el núcleo asume que el tablero es de 8x8.
 */
public class Tablero {

    private final int filas;
    private final int columnas;
    private final Map<Posicion, Pieza> piezas = new HashMap<>();

    public Tablero(int filas, int columnas) {
        if (filas <= 0 || columnas <= 0) {
            throw new IllegalArgumentException("El tablero debe tener tamaño positivo");
        }
        this.filas = filas;
        this.columnas = columnas;
    }

    public boolean estaDentro(Posicion p) {
        return p.fila() >= 0 && p.fila() < filas
                && p.columna() >= 0 && p.columna() < columnas;
    }

    /** Devuelve la pieza en esa casilla, o null si está vacía. */
    public Pieza piezaEn(Posicion p) {
        return piezas.get(p);
    }

    public boolean estaVacia(Posicion p) {
        return !piezas.containsKey(p);
    }

    public boolean hayRival(Posicion p, Color miColor) {
        Pieza otra = piezas.get(p);
        return otra != null && otra.getColor() != miColor;
    }

    public void colocar(Pieza pieza, Posicion p) {
        if (!estaDentro(p)) {
            throw new IllegalArgumentException("Posición fuera del tablero: " + p);
        }
        piezas.put(p, pieza);
    }

    /**
     * Mueve una pieza sin validar reglas (validar es trabajo de Partida).
     * Si había una pieza en el destino, queda capturada. Devuelve la pieza capturada o null.
     */
    public Pieza mover(Posicion desde, Posicion hasta) {
        Pieza pieza = piezas.remove(desde);
        if (pieza == null) {
            throw new IllegalArgumentException("No hay pieza en " + desde);
        }
        return piezas.put(hasta, pieza.movida());
    }

    /** Todas las posiciones ocupadas por piezas de un color. */
    public List<Posicion> posicionesDe(Color color) {
        return piezas.entrySet().stream()
                .filter(e -> e.getValue().getColor() == color)
                .map(Map.Entry::getKey)
                .toList();
    }

    /** Copia independiente, para simular un movimiento sin tocar el tablero real. */
    public Tablero copia() {
        Tablero otro = new Tablero(filas, columnas);
        otro.piezas.putAll(this.piezas); // las piezas son inmutables, se pueden compartir
        return otro;
    }

    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
}
