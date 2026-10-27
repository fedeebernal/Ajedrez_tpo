package ajedrez.nucleo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetectorJaqueTest {

    private final DetectorJaque detector = new DetectorJaque();

    @Test
    void torreRivalEnLaMismaColumnaDaJaque() {
        Tablero t = new Tablero(8, 8);
        t.colocar(Piezas.rey(Color.BLANCO), new Posicion(0, 4));
        t.colocar(Piezas.torre(Color.NEGRO), new Posicion(7, 4));
        assertTrue(detector.estaEnJaque(t, Color.BLANCO));
    }

    @Test
    void unaPiezaEnElMedioBloqueaElJaque() {
        Tablero t = new Tablero(8, 8);
        t.colocar(Piezas.rey(Color.BLANCO), new Posicion(0, 4));
        t.colocar(Piezas.peon(Color.BLANCO), new Posicion(1, 4));
        t.colocar(Piezas.torre(Color.NEGRO), new Posicion(7, 4));
        assertFalse(detector.estaEnJaque(t, Color.BLANCO));
    }

    @Test
    void caballoDaJaqueSaltando() {
        Tablero t = new Tablero(8, 8);
        t.colocar(Piezas.rey(Color.BLANCO), new Posicion(0, 4));
        t.colocar(Piezas.caballo(Color.NEGRO), new Posicion(2, 3)); // d3
        assertTrue(detector.estaEnJaque(t, Color.BLANCO));
    }

    @Test
    void peonDaJaqueEnDiagonalPeroNoDeFrente() {
        Tablero t = new Tablero(8, 8);
        t.colocar(Piezas.rey(Color.BLANCO), new Posicion(3, 4));       // e4
        t.colocar(Piezas.peon(Color.NEGRO), new Posicion(4, 4));       // e5 (de frente)
        assertFalse(detector.estaEnJaque(t, Color.BLANCO));
        t.colocar(Piezas.peon(Color.NEGRO), new Posicion(4, 3));       // d5 (diagonal)
        assertTrue(detector.estaEnJaque(t, Color.BLANCO));
    }

    @Test
    void posicionInicialSinJaque() {
        Tablero t = new DisposicionClasica().crearTablero();
        assertFalse(detector.estaEnJaque(t, Color.BLANCO));
        assertFalse(detector.estaEnJaque(t, Color.NEGRO));
    }
}
