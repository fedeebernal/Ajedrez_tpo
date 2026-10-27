package ajedrez.consola;

import ajedrez.nucleo.DisposicionClasica;
import ajedrez.nucleo.Partida;

import java.util.Scanner;

/** Punto de entrada: arma las piezas del sistema (composición) y arranca el juego. */
public class Main {
    public static void main(String[] args) {
        Partida partida = new Partida(new DisposicionClasica());
        new ConsolaUI(partida, new Scanner(System.in), System.out).jugar();
    }
}
