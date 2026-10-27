package ajedrez.nucleo;

import java.util.ArrayList;
import java.util.List;

/**
 * Avanza en cada dirección casilla por casilla hasta salirse del tablero o chocar una pieza.
 * Si choca una pieza rival, la puede capturar. Si es propia, se detiene antes.
 * Lo usan la torre (rectas), el alfil (diagonales) y la reina (ambas).
 */
public class MovimientoDeslizante implements ReglaMovimiento {

    private final List<Direccion> direcciones;

    public MovimientoDeslizante(List<Direccion> direcciones) {
        this.direcciones = List.copyOf(direcciones);
    }

    @Override
    public List<Posicion> destinos(Posicion desde, Tablero tablero, Color color) {
        List<Posicion> resultado = new ArrayList<>();
        for (Direccion d : direcciones) {
            Posicion actual = desde.desplazar(d);
            while (tablero.estaDentro(actual)) {
                if (tablero.estaVacia(actual)) {
                    resultado.add(actual);
                } else {
                    if (tablero.hayRival(actual, color)) {
                        resultado.add(actual); // captura
                    }
                    break; // hay una pieza en el medio: no se sigue
                }
                actual = actual.desplazar(d);
            }
        }
        return resultado;
    }
}
