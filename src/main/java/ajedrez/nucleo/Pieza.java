package ajedrez.nucleo;

import java.util.ArrayList;
import java.util.List;

/**
 * Una pieza NO sabe moverse por sí misma: delega en sus reglas de movimiento (composición).
 * No hay subclases Torre, Alfil, etc. Cada pieza es la combinación de sus reglas.
 * Es inmutable: cuando se mueve, el tablero guarda una copia marcada como "ya se movió".
 */
public class Pieza {

    private final String nombre;
    private final char simbolo;
    private final Color color;
    private final List<ReglaMovimiento> reglas;
    private final boolean esRey;
    private final boolean yaSeMovio;

    public Pieza(String nombre, char simbolo, Color color,
                 List<ReglaMovimiento> reglas, boolean esRey) {
        this(nombre, simbolo, color, reglas, esRey, false);
    }

    private Pieza(String nombre, char simbolo, Color color,
                  List<ReglaMovimiento> reglas, boolean esRey, boolean yaSeMovio) {
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.color = color;
        this.reglas = List.copyOf(reglas);
        this.esRey = esRey;
        this.yaSeMovio = yaSeMovio;
    }

    /** Junta los destinos de todas sus reglas. */
    public List<Posicion> destinosPosibles(Posicion desde, Tablero tablero) {
        List<Posicion> todos = new ArrayList<>();
        for (ReglaMovimiento regla : reglas) {
            todos.addAll(regla.destinos(desde, tablero, color));
        }
        return todos;
    }

    /** Devuelve la misma pieza, marcada como movida. */
    public Pieza movida() {
        return new Pieza(nombre, simbolo, color, reglas, esRey, true);
    }

    public String getNombre() { return nombre; }
    public char getSimbolo() { return simbolo; }
    public Color getColor() { return color; }
    public boolean esRey() { return esRey; }
    public boolean yaSeMovio() { return yaSeMovio; }

    @Override
    public String toString() {
        return nombre + " " + color;
    }
}
