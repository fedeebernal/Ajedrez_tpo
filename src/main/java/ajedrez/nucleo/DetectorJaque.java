package ajedrez.nucleo;

/**
 * Responde una sola pregunta: ¿el rey de este color está siendo atacado?
 * Busca el rey y revisa si alguna pieza rival puede llegar a su casilla.
 */
public class DetectorJaque {

    public boolean estaEnJaque(Tablero tablero, Color color) {
        Posicion rey = buscarRey(tablero, color);
        if (rey == null) {
            return false; // sin rey en el tablero (útil en algunos tests)
        }
        for (Posicion p : tablero.posicionesDe(color.opuesto())) {
            Pieza rival = tablero.piezaEn(p);
            if (rival.destinosPosibles(p, tablero).contains(rey)) {
                return true;
            }
        }
        return false;
    }

    private Posicion buscarRey(Tablero tablero, Color color) {
        for (Posicion p : tablero.posicionesDe(color)) {
            if (tablero.piezaEn(p).esRey()) {
                return p;
            }
        }
        return null;
    }
}
