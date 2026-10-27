package ajedrez.nucleo;

import java.util.ArrayList;
import java.util.List;

/**
 * El peón es especial: avanza derecho (sin capturar), puede avanzar dos en su primer
 * movimiento, y solo captura en diagonal hacia adelante.
 * "Adelante" depende del color: las blancas suben de fila, las negras bajan.
 */
public class MovimientoPeon implements ReglaMovimiento {

    @Override
    public List<Posicion> destinos(Posicion desde, Tablero tablero, Color color) {
        List<Posicion> resultado = new ArrayList<>();
        int adelante = (color == Color.BLANCO) ? 1 : -1;

        // Avance simple
        Posicion uno = desde.desplazar(new Direccion(adelante, 0));
        if (tablero.estaDentro(uno) && tablero.estaVacia(uno)) {
            resultado.add(uno);

            // Avance doble: solo si el peón nunca se movió y el camino está libre
            Pieza peon = tablero.piezaEn(desde);
            Posicion dos = uno.desplazar(new Direccion(adelante, 0));
            if (peon != null && !peon.yaSeMovio()
                    && tablero.estaDentro(dos) && tablero.estaVacia(dos)) {
                resultado.add(dos);
            }
        }

        // Capturas en diagonal
        for (int lado : new int[]{-1, 1}) {
            Posicion diagonal = desde.desplazar(new Direccion(adelante, lado));
            if (tablero.estaDentro(diagonal) && tablero.hayRival(diagonal, color)) {
                resultado.add(diagonal);
            }
        }
        return resultado;
    }
}
