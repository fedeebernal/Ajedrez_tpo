package ajedrez.nucleo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartidaTest {

    private static Posicion p(int fila, int col) { return new Posicion(fila, col); }

    @Test
    void empiezanLasBlancasYLuegoSeAlterna() {
        Partida partida = new Partida(new DisposicionClasica());
        assertEquals(Color.BLANCO, partida.getTurno());
        partida.mover(p(1, 4), p(3, 4)); // e2 e4
        assertEquals(Color.NEGRO, partida.getTurno());
        partida.mover(p(6, 4), p(4, 4)); // e7 e5
        assertEquals(Color.BLANCO, partida.getTurno());
    }

    @Test
    void noSePuedeMoverUnaPiezaRivalEnTurnoAjeno() {
        Partida partida = new Partida(new DisposicionClasica());
        assertThrows(MovimientoInvalidoException.class,
                () -> partida.mover(p(6, 4), p(4, 4))); // negras mueven primero: no
    }

    @Test
    void movimientoProhibidoPorLaPiezaSeRechazaYNoCambiaNada() {
        Partida partida = new Partida(new DisposicionClasica());
        assertThrows(MovimientoInvalidoException.class,
                () -> partida.mover(p(0, 0), p(4, 0))); // torre a1 atraviesa su peón
        assertEquals(Color.BLANCO, partida.getTurno());
        assertNotNull(partida.getTablero().piezaEn(p(0, 0)));
    }

    @Test
    void moverDesdeCasillaVaciaEsInvalido() {
        Partida partida = new Partida(new DisposicionClasica());
        assertThrows(MovimientoInvalidoException.class,
                () -> partida.mover(p(3, 3), p(4, 3)));
    }

    @Test
    void capturaUnaPiezaRival() {
        Partida partida = new Partida(new DisposicionClasica());
        partida.mover(p(1, 4), p(3, 4)); // e4
        partida.mover(p(6, 3), p(4, 3)); // d5
        Pieza capturada = partida.mover(p(3, 4), p(4, 3)); // exd5
        assertEquals("Peón", capturada.getNombre());
        assertEquals(Color.NEGRO, capturada.getColor());
        assertEquals(15, partida.getTablero().posicionesDe(Color.NEGRO).size());
    }

    @Test
    void noSePuedeHacerUnMovimientoQueDejeAlPropioReyEnJaque() {
        // Rey blanco en e1, alfil blanco en e2 clavado por una torre negra en e8
        Tablero t = new Tablero(8, 8);
        t.colocar(Piezas.rey(Color.BLANCO), p(0, 4));
        t.colocar(Piezas.alfil(Color.BLANCO), p(1, 4));
        t.colocar(Piezas.torre(Color.NEGRO), p(7, 4));
        t.colocar(Piezas.rey(Color.NEGRO), p(7, 0));
        Partida partida = new Partida(t, new DetectorJaque());

        assertThrows(MovimientoInvalidoException.class,
                () -> partida.mover(p(1, 4), p(2, 5))); // el alfil está clavado
    }

    @Test
    void mateDelPastor() {
        Partida partida = new Partida(new DisposicionClasica());
        partida.mover(p(1, 4), p(3, 4)); // e4
        partida.mover(p(6, 4), p(4, 4)); // e5
        partida.mover(p(0, 5), p(3, 2)); // Ac4
        partida.mover(p(7, 1), p(5, 2)); // Cc6
        partida.mover(p(0, 3), p(4, 7)); // Dh5
        partida.mover(p(7, 6), p(5, 5)); // Cf6??
        partida.mover(p(4, 7), p(6, 5)); // Dxf7#
        assertEquals(EstadoPartida.JAQUE_MATE, partida.estado());
    }

    @Test
    void reyAhogado() {
        // Rey negro en a8, sin jaque pero sin movimientos: dama blanca en b6, rey blanco lejos
        Tablero t = new Tablero(8, 8);
        t.colocar(Piezas.rey(Color.NEGRO), p(7, 0));
        t.colocar(Piezas.reina(Color.BLANCO), p(5, 1));
        t.colocar(Piezas.rey(Color.BLANCO), p(0, 7));
        Partida partida = new Partida(t, new DetectorJaque());
        partida.mover(p(0, 7), p(0, 6)); // juegan blancas, ahora le toca a las negras
        assertEquals(EstadoPartida.AHOGADO, partida.estado());
    }
}
