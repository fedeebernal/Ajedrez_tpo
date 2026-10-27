package ajedrez.nucleo;

import java.util.List;

/**
 * Un desplazamiento en filas y columnas (ej: +1 fila, 0 columnas = "hacia arriba").
 * Se usa tanto para direcciones de deslizamiento como para saltos fijos.
 */
public record Direccion(int filas, int columnas) {

    public static final List<Direccion> RECTAS = List.of(
            new Direccion(1, 0), new Direccion(-1, 0),
            new Direccion(0, 1), new Direccion(0, -1));

    public static final List<Direccion> DIAGONALES = List.of(
            new Direccion(1, 1), new Direccion(1, -1),
            new Direccion(-1, 1), new Direccion(-1, -1));

    public static final List<Direccion> SALTOS_CABALLO = List.of(
            new Direccion(2, 1), new Direccion(2, -1),
            new Direccion(-2, 1), new Direccion(-2, -1),
            new Direccion(1, 2), new Direccion(1, -2),
            new Direccion(-1, 2), new Direccion(-1, -2));

    /** Las 8 casillas alrededor (lo que usa el rey). */
    public static final List<Direccion> ALREDEDOR = List.of(
            new Direccion(1, 0), new Direccion(-1, 0),
            new Direccion(0, 1), new Direccion(0, -1),
            new Direccion(1, 1), new Direccion(1, -1),
            new Direccion(-1, 1), new Direccion(-1, -1));
}
