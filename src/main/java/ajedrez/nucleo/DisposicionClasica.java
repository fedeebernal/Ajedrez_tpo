package ajedrez.nucleo;

import java.util.List;
import java.util.function.Function;

/** La disposición estándar del ajedrez en 8x8. */
public class DisposicionClasica implements DisposicionInicial {

    @Override
    public Tablero crearTablero() {
        Tablero t = new Tablero(8, 8);
        List<Function<Color, Pieza>> filaTrasera = List.of(
                Piezas::torre, Piezas::caballo, Piezas::alfil, Piezas::reina,
                Piezas::rey, Piezas::alfil, Piezas::caballo, Piezas::torre);

        for (int col = 0; col < 8; col++) {
            t.colocar(filaTrasera.get(col).apply(Color.BLANCO), new Posicion(0, col));
            t.colocar(Piezas.peon(Color.BLANCO), new Posicion(1, col));
            t.colocar(Piezas.peon(Color.NEGRO), new Posicion(6, col));
            t.colocar(filaTrasera.get(col).apply(Color.NEGRO), new Posicion(7, col));
        }
        return t;
    }
}
