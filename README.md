# TPO Ajedrez — Ingeniería de Software (UADE)

Ajedrez en Java, con el núcleo (reglas) separado de la interfaz (consola).

## Cómo abrirlo y correrlo (paso a paso)

**Necesitás:** Java 17 o más nuevo, e IntelliJ IDEA (la versión Community es gratis).

1. Descomprimí el `.zip` en una carpeta.
2. Abrí IntelliJ → **Open** → elegí la carpeta `ajedrez-tpo` (la que tiene el archivo `pom.xml`).
3. IntelliJ lo detecta como proyecto Maven y baja JUnit solo. Esperá a que termine la barra de abajo.
4. **Para jugar:** abrí `src/main/java/ajedrez/consola/Main.java` y tocá el ▶ verde al lado de `main`.
   Escribí los movimientos así: `e2 e4`. Para salir: `salir`.
5. **Para correr los tests:** click derecho en la carpeta `src/test/java` → **Run 'All Tests'**.

Por consola (si tenés Maven instalado): `mvn test` corre los tests.

## Estructura

```
src/main/java/ajedrez/
├── nucleo/     ← reglas del ajedrez. No importa nada de afuera.
│   ├── Partida.java              (coordina turnos, validación y jaque)
│   ├── Tablero.java              (tamaño configurable, no asume 8x8)
│   ├── Pieza.java                (se compone de reglas, sin subclases)
│   ├── Piezas.java               (las 6 piezas clásicas)
│   ├── ReglaMovimiento.java      (interfaz Strategy)
│   ├── MovimientoDeslizante.java (torre, alfil, reina)
│   ├── MovimientoSalto.java      (caballo, rey)
│   ├── MovimientoPeon.java       (peón)
│   ├── DetectorJaque.java
│   ├── DisposicionInicial.java / DisposicionClasica.java
│   └── Posicion, Direccion, Color, EstadoPartida, MovimientoInvalidoException
└── consola/    ← adaptador: lee del teclado y dibuja el tablero.
    ├── Main.java
    ├── ConsolaUI.java
    └── NotacionAlgebraica.java   ("e2" ↔ Posicion)

src/test/java/ajedrez/nucleo/   ← tests del núcleo, sin UI
diagrama-clases.puml            ← diagrama UML (PlantUML)
```

## Alcance

**Obligatorio (todo cubierto):** tablero 8x8, las 6 piezas, turnos, captura, validación de movimientos inválidos, detección de jaque.

**Extra incluido:** no se permite dejar al propio rey en jaque, jaque mate, ahogado, tablero de cualquier tamaño.

**No incluido todavía:** enroque, captura al paso, coronación, deshacer.

## Cómo ver el diagrama UML

Copiá el contenido de `diagrama-clases.puml` en https://www.plantuml.com/plantuml
o instalá el plugin "PlantUML Integration" en IntelliJ.
