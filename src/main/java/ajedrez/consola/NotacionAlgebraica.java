package ajedrez.consola;

import ajedrez.nucleo.Posicion;

/**
 * Traduce "e2" ↔ Posicion. Vive en el adaptador porque es un detalle de cómo
 * el humano escribe las casillas, no una regla del ajedrez.
 */
public final class NotacionAlgebraica {

    private NotacionAlgebraica() {}

    public static Posicion aPosicion(String texto) {
        if (texto == null || texto.length() < 2) {
            throw new IllegalArgumentException("Casilla inválida: " + texto);
        }
        char letra = Character.toLowerCase(texto.charAt(0));
        if (letra < 'a' || letra > 'z') {
            throw new IllegalArgumentException("Casilla inválida: " + texto);
        }
        try {
            int numero = Integer.parseInt(texto.substring(1));
            return new Posicion(numero - 1, letra - 'a');
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Casilla inválida: " + texto);
        }
    }

    public static String aTexto(Posicion p) {
        return "" + (char) ('a' + p.columna()) + (p.fila() + 1);
    }
}
