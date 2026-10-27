package ajedrez.nucleo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simula lo que pueden pedir en la defensa individual: una pieza nueva y otro tamaño de tablero.
 * Ninguno de estos tests requiere modificar una sola clase del núcleo.
 */
class ExtensibilidadTest {

    @Test
    void piezaNuevaArzobispoSeArmaCombinandoReglasExistentes() {
        // Arzobispo: se mueve como alfil + caballo. No existe ninguna clase "Arzobispo".
        Pieza arzobispo = new Pieza("Arzobispo", 'Z', Color.BLANCO, List.of(
                new MovimientoDeslizante(Direccion.DIAGONALES),
                new MovimientoSalto(Direccion.SALTOS_CABALLO)), false);

        Tablero t = new Tablero(8, 8);
        Posicion d4 = new Posicion(3, 3);
        t.colocar(arzobispo, d4);

        assertEquals(13 + 8, arzobispo.destinosPosibles(d4, t).size());
    }

    @Test
    void piezaConReglaTotalmenteNueva() {
        // Una regla nueva es una clase nueva que implementa la interfaz. Nada más cambia.
        ReglaMovimiento saltoDeDos = (desde, tablero, color) ->
                Direccion.RECTAS.stream()
                        .map(d -> new Posicion(desde.fila() + 2 * d.filas(), desde.columna() + 2 * d.columnas()))
                        .filter(tablero::estaDentro)
                        .filter(p -> tablero.estaVacia(p) || tablero.hayRival(p, color))
                        .toList();
        Pieza saltarin = new Pieza("Saltarín", 'S', Color.BLANCO, List.of(saltoDeDos), false);

        Tablero t = new Tablero(8, 8);
        t.colocar(saltarin, new Posicion(3, 3));
        assertEquals(4, saltarin.destinosPosibles(new Posicion(3, 3), t).size());
    }

    @Test
    void tableroDe10x10FuncionaSinCambiarNada() {
        Tablero t = new Tablero(10, 10);
        Posicion e5 = new Posicion(4, 4);
        t.colocar(Piezas.torre(Color.BLANCO), e5);
        assertEquals(18, t.piezaEn(e5).destinosPosibles(e5, t).size()); // 9 + 9
        assertTrue(t.estaDentro(new Posicion(9, 9)));
    }

    @Test
    void partidaEnTableroGrandeConDisposicionPropia() {
        DisposicionInicial mini = () -> {
            Tablero t = new Tablero(10, 10);
            t.colocar(Piezas.rey(Color.BLANCO), new Posicion(0, 5));
            t.colocar(Piezas.rey(Color.NEGRO), new Posicion(9, 5));
            t.colocar(Piezas.torre(Color.BLANCO), new Posicion(0, 0));
            return t;
        };
        Partida partida = new Partida(mini);
        partida.mover(new Posicion(0, 0), new Posicion(9, 0)); // torre al fondo: jaque
        assertEquals(EstadoPartida.JAQUE, partida.estado());
    }
}
