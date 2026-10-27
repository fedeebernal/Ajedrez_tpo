package ajedrez.nucleo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    @Test
    void posicionDentroYFueraDelTablero() {
        Tablero t = new Tablero(8, 8);
        assertTrue(t.estaDentro(new Posicion(0, 0)));
        assertTrue(t.estaDentro(new Posicion(7, 7)));
        assertFalse(t.estaDentro(new Posicion(8, 0)));
        assertFalse(t.estaDentro(new Posicion(0, -1)));
    }

    @Test
    void moverDejaLaCasillaOrigenVaciaYDevuelveLaCapturada() {
        // Arrange
        Tablero t = new Tablero(8, 8);
        t.colocar(Piezas.torre(Color.BLANCO), new Posicion(0, 0));
        t.colocar(Piezas.peon(Color.NEGRO), new Posicion(5, 0));
        // Act
        Pieza capturada = t.mover(new Posicion(0, 0), new Posicion(5, 0));
        // Assert
        assertTrue(t.estaVacia(new Posicion(0, 0)));
        assertEquals("Torre", t.piezaEn(new Posicion(5, 0)).getNombre());
        assertEquals("Peón", capturada.getNombre());
    }

    @Test
    void laCopiaEsIndependienteDelOriginal() {
        Tablero t = new Tablero(8, 8);
        t.colocar(Piezas.torre(Color.BLANCO), new Posicion(0, 0));
        Tablero copia = t.copia();
        copia.mover(new Posicion(0, 0), new Posicion(3, 0));
        assertNotNull(t.piezaEn(new Posicion(0, 0)), "El original no debe cambiar");
    }

    @Test
    void disposicionClasicaTiene32Piezas() {
        Tablero t = new DisposicionClasica().crearTablero();
        assertEquals(16, t.posicionesDe(Color.BLANCO).size());
        assertEquals(16, t.posicionesDe(Color.NEGRO).size());
        assertTrue(t.piezaEn(new Posicion(0, 4)).esRey());
        assertTrue(t.piezaEn(new Posicion(7, 4)).esRey());
    }
}
