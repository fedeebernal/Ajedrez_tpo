package ajedrez.nucleo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Cada pieza, probada sola en un tablero casi vacío. Ninguna UI involucrada. */
class MovimientoPiezasTest {

    private List<Posicion> destinos(Tablero t, Posicion p) {
        return t.piezaEn(p).destinosPosibles(p, t);
    }

    @Test
    void torreEnElCentroTiene14Destinos() {
        Tablero t = new Tablero(8, 8);
        Posicion d4 = new Posicion(3, 3);
        t.colocar(Piezas.torre(Color.BLANCO), d4);
        assertEquals(14, destinos(t, d4).size());
    }

    @Test
    void torreNoSaltaPiezasPropiasPeroCapturaRivales() {
        Tablero t = new Tablero(8, 8);
        Posicion a1 = new Posicion(0, 0);
        t.colocar(Piezas.torre(Color.BLANCO), a1);
        t.colocar(Piezas.peon(Color.BLANCO), new Posicion(2, 0)); // propia en a3
        t.colocar(Piezas.peon(Color.NEGRO), new Posicion(0, 3));  // rival en d1

        List<Posicion> d = destinos(t, a1);
        assertTrue(d.contains(new Posicion(1, 0)));   // a2 sí
        assertFalse(d.contains(new Posicion(2, 0)));  // a3 propia: no
        assertFalse(d.contains(new Posicion(3, 0)));  // a4 detrás: no
        assertTrue(d.contains(new Posicion(0, 3)));   // d1 captura: sí
        assertFalse(d.contains(new Posicion(0, 4)));  // e1 detrás del rival: no
    }

    @Test
    void alfilSoloSeMueveEnDiagonal() {
        Tablero t = new Tablero(8, 8);
        Posicion c1 = new Posicion(0, 2);
        t.colocar(Piezas.alfil(Color.BLANCO), c1);
        List<Posicion> d = destinos(t, c1);
        assertTrue(d.contains(new Posicion(5, 7)));   // h6
        assertFalse(d.contains(new Posicion(1, 2)));  // c2 (recto): no
        assertEquals(7, d.size());
    }

    @Test
    void reinaCombinaTorreYAlfil() {
        Tablero t = new Tablero(8, 8);
        Posicion d4 = new Posicion(3, 3);
        t.colocar(Piezas.reina(Color.BLANCO), d4);
        assertEquals(27, destinos(t, d4).size()); // 14 rectas + 13 diagonales
    }

    @Test
    void caballoSaltaPorEncimaDePiezas() {
        Tablero t = new DisposicionClasica().crearTablero();
        Posicion b1 = new Posicion(0, 1);
        List<Posicion> d = destinos(t, b1);
        assertEquals(2, d.size());
        assertTrue(d.contains(new Posicion(2, 0))); // a3
        assertTrue(d.contains(new Posicion(2, 2))); // c3
    }

    @Test
    void reySeMueveUnaCasilla() {
        Tablero t = new Tablero(8, 8);
        Posicion e1 = new Posicion(0, 4);
        t.colocar(Piezas.rey(Color.BLANCO), e1);
        assertEquals(5, destinos(t, e1).size()); // en el borde: 5 casillas
    }

    @Test
    void peonAvanzaUnoODosEnSuPrimerMovimiento() {
        Tablero t = new Tablero(8, 8);
        Posicion e2 = new Posicion(1, 4);
        t.colocar(Piezas.peon(Color.BLANCO), e2);
        List<Posicion> d = destinos(t, e2);
        assertTrue(d.contains(new Posicion(2, 4)));
        assertTrue(d.contains(new Posicion(3, 4)));
        assertEquals(2, d.size());
    }

    @Test
    void peonYaMovidoSoloAvanzaUno() {
        Tablero t = new Tablero(8, 8);
        t.colocar(Piezas.peon(Color.BLANCO), new Posicion(1, 4));
        t.mover(new Posicion(1, 4), new Posicion(2, 4));
        List<Posicion> d = destinos(t, new Posicion(2, 4));
        assertEquals(List.of(new Posicion(3, 4)), d);
    }

    @Test
    void peonNoCapturaDeFrenteYSiEnDiagonal() {
        Tablero t = new Tablero(8, 8);
        Posicion e4 = new Posicion(3, 4);
        t.colocar(Piezas.peon(Color.BLANCO).movida(), e4);
        t.colocar(Piezas.peon(Color.NEGRO), new Posicion(4, 4)); // bloquea de frente
        t.colocar(Piezas.peon(Color.NEGRO), new Posicion(4, 3)); // d5 capturable
        List<Posicion> d = destinos(t, e4);
        assertEquals(List.of(new Posicion(4, 3)), d);
    }

    @Test
    void peonNegroAvanzaHaciaAbajo() {
        Tablero t = new Tablero(8, 8);
        Posicion e7 = new Posicion(6, 4);
        t.colocar(Piezas.peon(Color.NEGRO), e7);
        assertTrue(destinos(t, e7).contains(new Posicion(4, 4))); // e5
    }
}
