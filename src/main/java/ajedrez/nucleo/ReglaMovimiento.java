package ajedrez.nucleo;

import java.util.List;

/**
 * Estrategia de movimiento (patrón Strategy).
 * Dada una posición de origen y el tablero, devuelve las casillas a las que se puede llegar.
 * Una pieza combina una o más reglas: así se arman piezas nuevas sin tocar código existente.
 */
public interface ReglaMovimiento {
    List<Posicion> destinos(Posicion desde, Tablero tablero, Color color);
}
