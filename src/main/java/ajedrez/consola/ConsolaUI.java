package ajedrez.consola;

import ajedrez.nucleo.*;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Adaptador de entrada: lee lo que escribe el jugador y se lo pide a Partida.
 * El núcleo no sabe que esta clase existe (las dependencias apuntan hacia adentro).
 */
public class ConsolaUI {

    private final Partida partida;
    private final Scanner entrada;
    private final PrintStream salida;

    public ConsolaUI(Partida partida, Scanner entrada, PrintStream salida) {
        this.partida = partida;
        this.entrada = entrada;
        this.salida = salida;
    }

    public void jugar() {
        salida.println("=== AJEDREZ ===");
        salida.println("Escribí movimientos como: e2 e4   (o 'salir' para terminar)");

        while (true) {
            dibujarTablero();
            EstadoPartida estado = partida.estado();

            if (estado == EstadoPartida.JAQUE_MATE) {
                salida.println("¡Jaque mate! Ganan las " + nombre(partida.getTurno().opuesto()) + ".");
                return;
            }
            if (estado == EstadoPartida.AHOGADO) {
                salida.println("Ahogado: tablas.");
                return;
            }
            if (estado == EstadoPartida.JAQUE) {
                salida.println("¡Jaque al rey " + nombre(partida.getTurno()) + "!");
            }

            salida.print("Juegan las " + nombre(partida.getTurno()) + " > ");
            if (!entrada.hasNextLine()) return;
            String linea = entrada.nextLine().trim();
            if (linea.equalsIgnoreCase("salir")) return;

            procesar(linea);
        }
    }

    private void procesar(String linea) {
        String[] partes = linea.split("\\s+");
        if (partes.length != 2) {
            salida.println("Formato: origen destino (ej: e2 e4)");
            return;
        }
        try {
            Posicion desde = NotacionAlgebraica.aPosicion(partes[0]);
            Posicion hasta = NotacionAlgebraica.aPosicion(partes[1]);
            Pieza capturada = partida.mover(desde, hasta);
            if (capturada != null) {
                salida.println("Capturaste: " + capturada.getNombre());
            }
        } catch (MovimientoInvalidoException | IllegalArgumentException e) {
            salida.println("Movimiento inválido: " + e.getMessage());
        }
    }

    private void dibujarTablero() {
        Tablero t = partida.getTablero();
        salida.println();
        for (int fila = t.getFilas() - 1; fila >= 0; fila--) {
            salida.printf("%2d ", fila + 1);
            for (int col = 0; col < t.getColumnas(); col++) {
                Pieza p = t.piezaEn(new Posicion(fila, col));
                salida.print(p == null ? " ." : " " + simbolo(p));
            }
            salida.println();
        }
        salida.print("   ");
        for (int col = 0; col < t.getColumnas(); col++) {
            salida.print(" " + (char) ('a' + col));
        }
        salida.println();
        salida.println("(mayúsculas = blancas, minúsculas = negras)");
    }

    private char simbolo(Pieza p) {
        return p.getColor() == Color.BLANCO
                ? Character.toUpperCase(p.getSimbolo())
                : Character.toLowerCase(p.getSimbolo());
    }

    private String nombre(Color c) {
        return c == Color.BLANCO ? "blancas" : "negras";
    }
}
