package ajedrez.nucleo;

import java.util.ArrayList;
import java.util.List;

/**
 * Salta directamente a casillas fijas, sin importar lo que haya en el medio.
 * Lo usan el caballo (saltos en L) y el rey (una casilla alrededor).
 */
public class MovimientoSalto implements ReglaMovimiento {

    private final List<Direccion> saltos;

    public MovimientoSalto(List<Direccion> saltos) {
        this.saltos = List.copyOf(saltos);
    }

    @Override
    public List<Posicion> destinos(Posicion desde, Tablero tablero, Color color) {
        List<Posicion> resultado = new ArrayList<>();
        for (Direccion salto : saltos) {
            Posicion destino = desde.desplazar(salto);
            if (tablero.estaDentro(destino)
                    && (tablero.estaVacia(destino) || tablero.hayRival(destino, color))) {
                resultado.add(destino);
            }
        }
        return resultado;
    }
}
