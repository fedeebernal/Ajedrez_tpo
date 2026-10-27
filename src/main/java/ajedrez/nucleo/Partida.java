package ajedrez.nucleo;

import java.util.ArrayList;
import java.util.List;

/**
 * Puerto de entrada del núcleo: la UI (o un test) le pide movimientos a esta clase.
 * Coordina: turno correcto → regla de la pieza → no dejar el propio rey en jaque → aplicar → pasar turno.
 */
public class Partida {

    private final Tablero tablero;
    private final DetectorJaque detector;
    private Color turno = Color.BLANCO;

    public Partida(DisposicionInicial disposicion) {
        this(disposicion.crearTablero(), new DetectorJaque());
    }

    /** Las dependencias entran por constructor (inyección de dependencias). */
    public Partida(Tablero tablero, DetectorJaque detector) {
        this.tablero = tablero;
        this.detector = detector;
    }

    /**
     * Intenta mover. Si es inválido lanza MovimientoInvalidoException y no cambia nada.
     * Devuelve la pieza capturada, o null si no hubo captura.
     */
    public Pieza mover(Posicion desde, Posicion hasta) {
        Pieza pieza = tablero.piezaEn(desde);

        if (pieza == null) {
            throw new MovimientoInvalidoException("No hay ninguna pieza en el origen.");
        }
        if (pieza.getColor() != turno) {
            throw new MovimientoInvalidoException("Es el turno de las " + nombreColor(turno) + ".");
        }
        if (!pieza.destinosPosibles(desde, tablero).contains(hasta)) {
            throw new MovimientoInvalidoException("El " + pieza.getNombre() + " no puede moverse ahí.");
        }
        if (dejaReyEnJaque(desde, hasta, turno)) {
            throw new MovimientoInvalidoException("Ese movimiento deja a tu rey en jaque.");
        }

        Pieza capturada = tablero.mover(desde, hasta);
        turno = turno.opuesto();
        return capturada;
    }

    public boolean estaEnJaque(Color color) {
        return detector.estaEnJaque(tablero, color);
    }

    /** Estado del jugador al que le toca mover. */
    public EstadoPartida estado() {
        boolean enJaque = estaEnJaque(turno);
        boolean puedeMover = !movimientosLegales(turno).isEmpty();
        if (enJaque && !puedeMover) return EstadoPartida.JAQUE_MATE;
        if (!enJaque && !puedeMover) return EstadoPartida.AHOGADO;
        if (enJaque) return EstadoPartida.JAQUE;
        return EstadoPartida.EN_JUEGO;
    }

    /** Todos los movimientos legales de un color, como pares {desde, hasta}. */
    public List<Posicion[]> movimientosLegales(Color color) {
        List<Posicion[]> legales = new ArrayList<>();
        for (Posicion desde : tablero.posicionesDe(color)) {
            for (Posicion hasta : tablero.piezaEn(desde).destinosPosibles(desde, tablero)) {
                if (!dejaReyEnJaque(desde, hasta, color)) {
                    legales.add(new Posicion[]{desde, hasta});
                }
            }
        }
        return legales;
    }

    /** Simula el movimiento en una copia del tablero y se fija si el rey propio queda atacado. */
    private boolean dejaReyEnJaque(Posicion desde, Posicion hasta, Color color) {
        Tablero simulado = tablero.copia();
        simulado.mover(desde, hasta);
        return detector.estaEnJaque(simulado, color);
    }

    private String nombreColor(Color c) {
        return c == Color.BLANCO ? "blancas" : "negras";
    }

    public Color getTurno() { return turno; }
    public Tablero getTablero() { return tablero; }
}
