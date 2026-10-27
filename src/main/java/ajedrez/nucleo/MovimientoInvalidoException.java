package ajedrez.nucleo;

/** Se lanza cuando se intenta un movimiento que las reglas no permiten. */
public class MovimientoInvalidoException extends RuntimeException {
    public MovimientoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
