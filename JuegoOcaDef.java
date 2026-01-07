import java.util.Scanner;
import java.util.Random;

public class JuegoOcaDef {

    static Scanner sc = new Scanner(System.in);
    static Random r = new Random();

    public static void main(String[] args) {

        int numJugadores = pedirNumJugadores();

        String[] nombres = new String[numJugadores];
        int[] posicion = new int[numJugadores];
        int[] penalizacion = new int[numJugadores];

        pedirNombres(nombres);

        boolean hayGanador = false;
        int turno = 0;

        while (!hayGanador) {

            System.out.println("\nEs el turno del jugador " + (turno + 1) + ", " + nombres[turno]);

            if (penalizacion[turno] > 0) {
                System.out.println("Pierdes turno. Turnos restantes: " + penalizacion[turno]);
                penalizacion[turno]--;
            } else {
                hayGanador = jugarTurno(turno, nombres, posicion, penalizacion);
            }

            turno++; 
            if (turno == numJugadores) turno = 0;
        }
    }

    static int pedirNumJugadores() {
        int n = 0;
        boolean correcto = false;

        while (!correcto) {
            try {
                System.out.print("Introduce número de jugadores (2-4): ");
                n = sc.nextInt();

                if (n >= 2 && n <= 4) correcto = true;
                else System.out.println("Debe ser entre 2 y 4.");
            } catch (Exception ex) {
                System.out.println("Valor incorrecto.");
                sc.next();
            }
        }
        return n;
    }

    static void pedirNombres(String[] nombres) {
        for (int i = 0; i < nombres.length; i++) {
            System.out.print("Nombre del jugador " + (i + 1) + ": ");
            nombres[i] = sc.next();
        }
    }

    static boolean jugarTurno(int j, String[] nombres, int[] pos, int[] pen) {

        System.out.print(">> Escribe 'tiro': ");
        sc.next();

        int dado1 = r.nextInt(6) + 1;
        int dado2 = (pos[j] < 60) ? r.nextInt(6) + 1 : 0;

        int avance = dado1 + dado2;

        System.out.println("Has obtenido " + dado1 + " y " + dado2 + " = " + avance);

        pos[j] += avance;

        if (pos[j] > 63) pos[j] = 63 - (pos[j] - 63);

        // ----- CASILLAS ESPECIALES -----

        if (pos[j] == 5 || pos[j] == 9 || pos[j] == 14 || pos[j] == 18 || pos[j] == 23 ||
            pos[j] == 27 || pos[j] == 32 || pos[j] == 36 || pos[j] == 41 ||
            pos[j] == 45 || pos[j] == 50 || pos[j] == 54 || pos[j] == 59) {

            System.out.println("OCA: De oca en oca y tiro porque me toca.");

            if      (pos[j] == 5)  pos[j] = 9;
            else if (pos[j] == 9)  pos[j] = 14;
            else if (pos[j] == 14) pos[j] = 18;
            else if (pos[j] == 18) pos[j] = 23;
            else if (pos[j] == 23) pos[j] = 27;
            else if (pos[j] == 27) pos[j] = 32;
            else if (pos[j] == 32) pos[j] = 36;
            else if (pos[j] == 36) pos[j] = 41;
            else if (pos[j] == 41) pos[j] = 45;
            else if (pos[j] == 45) pos[j] = 50;
            else if (pos[j] == 50) pos[j] = 54;
            else if (pos[j] == 54) pos[j] = 59;
            else if (pos[j] == 59) pos[j] = 63;

            System.out.println("Avanzas a la casilla " + pos[j]);
            return jugarTurno(j, nombres, pos, pen);
        }
        if (pos[j] == 6) {
            System.out.println("PUENTE: De puente a puente...");
            pos[j] = 12;
            return jugarTurno(j, nombres, pos, pen);
        }
        if (pos[j] == 12) {
            System.out.println("PUENTE: De puente a puente...");
            pos[j] = 6;
            return jugarTurno(j, nombres, pos, pen);
        }
        if (pos[j] == 19) {
            System.out.println("FONDA: Pierdes un turno.");
            pen[j] = 1;
        }
        if (pos[j] == 26 && ((dado1 == 3 && dado2 == 6) || (dado1 == 6 && dado2 == 3))) {
            System.out.println("DADOS 3-6: De dado a dado y tiro porque me ha tocado.");
            return jugarTurno(j, nombres, pos, pen);
        }
        if (pos[j] == 31) {
            System.out.println("POZO: Pierdes dos turnos.");
            pen[j] = 2;
        }
        if (pos[j] == 42) {
            System.out.println("LABERINTO: Retrocedes a la 39.");
            pos[j] = 39;
        }
        if (pos[j] == 52) {
            System.out.println("PRISIÓN: Pierdes tres turnos.");
            pen[j] = 3;
        }
        if (pos[j] == 53 && ((dado1 == 4 && dado2 == 5) || (dado1 == 5 && dado2 == 4))) {
            System.out.println("DADOS 4-5: Avanzas hasta aquí y vuelves a tirar.");
            return jugarTurno(j, nombres, pos, pen);
        }
        if (pos[j] == 58) {
            System.out.println("MUERTE: Vuelves al inicio.");
            pos[j] = 0;
        }
        System.out.println("Casilla actual: " + pos[j]);
        if (pos[j] == 63) {
            System.out.println("\nFELICIDADES!! " + nombres[j] + " HA GANADO EL JUEGO!!");
            return true;
        }
        return false;
    }
}
