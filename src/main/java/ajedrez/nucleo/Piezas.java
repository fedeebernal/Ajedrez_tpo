package ajedrez.nucleo;

import java.util.List;

/**
 * Las 6 piezas clásicas, armadas combinando reglas.
 * Para una pieza nueva NO hace falta tocar esta clase: se puede crear un
 * new Pieza(...) en cualquier lado con las reglas que se quiera (ver ExtensibilidadTest).
 */
public final class Piezas {

    private Piezas() {}

    public static Pieza rey(Color c) {
        return new Pieza("Rey", 'R', c,
                List.of(new MovimientoSalto(Direccion.ALREDEDOR)), true);
    }

    public static Pieza reina(Color c) {
        return new Pieza("Reina", 'D', c, List.of(
                new MovimientoDeslizante(Direccion.RECTAS),
                new MovimientoDeslizante(Direccion.DIAGONALES)), false);
    }

    public static Pieza torre(Color c) {
        return new Pieza("Torre", 'T', c,
                List.of(new MovimientoDeslizante(Direccion.RECTAS)), false);
    }

    public static Pieza alfil(Color c) {
        return new Pieza("Alfil", 'A', c,
                List.of(new MovimientoDeslizante(Direccion.DIAGONALES)), false);
    }

    public static Pieza caballo(Color c) {
        return new Pieza("Caballo", 'C', c,
                List.of(new MovimientoSalto(Direccion.SALTOS_CABALLO)), false);
    }

    public static Pieza peon(Color c) {
        return new Pieza("Peón", 'P', c,
                List.of(new MovimientoPeon()), false);
    }
}
