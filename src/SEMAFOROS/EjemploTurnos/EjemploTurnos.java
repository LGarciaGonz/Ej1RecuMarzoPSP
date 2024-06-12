package SEMAFOROS.EjemploTurnos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class EjemploTurnos {

    public static final int NUM_HILOS = 7;

    /**
     * Ejecutar 6 turnos por orden
     * Imprimir por pantalla su identificador Ej. Id 1
     * Banca: Imprimir "Hola soy la banca" y esperar 3 segundos.
     */

    public static void main(String[] args) {

        ExecutorService l_Ejecutor = (ExecutorService) Executors.newFixedThreadPool(NUM_HILOS);
        Buzon2 l_Buzon = new Buzon2();

        Banca2 l_Banca = new Banca2(l_Buzon);

        Proceso l_Proceso1 = new Proceso(l_Buzon, 1);
        Proceso l_Proceso2 = new Proceso(l_Buzon, 2);
        Proceso l_Proceso3 = new Proceso(l_Buzon, 3);
        Proceso l_Proceso4 = new Proceso(l_Buzon, 4);
        Proceso l_Proceso5 = new Proceso(l_Buzon, 5);
        Proceso l_Proceso6 = new Proceso(l_Buzon, 6);

        l_Ejecutor.submit(l_Banca);

        l_Ejecutor.submit(l_Proceso1);
        l_Ejecutor.submit(l_Proceso2);
        l_Ejecutor.submit(l_Proceso3);
        l_Ejecutor.submit(l_Proceso4);
        l_Ejecutor.submit(l_Proceso5);
        l_Ejecutor.submit(l_Proceso6);

        l_Ejecutor.shutdown();

    }

}

class Proceso implements Runnable {

    private Buzon2 a_Buzon;
    private int a_Identificador;

    public Proceso(Buzon2 l_Buzon, int a_Identificador) {
        this.a_Buzon = l_Buzon;
        this.a_Identificador = a_Identificador;
    }

    @Override
    public void run() {

        while (true) {

            try {
                a_Buzon.a_SemaforoProceso.acquire();
            } catch (InterruptedException e) {
                System.err.println("\n>>> ERROR: Se ha producido un error.");
            }

            if (a_Buzon.a_Turno == a_Identificador) {

                System.out.println("Identificador: " + a_Identificador);

                if (a_Buzon.a_Turno < 6) a_Buzon.a_Turno++;
                else {
                    System.out.println();
                    a_Buzon.a_Turno = 1;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.err.println("\n>>> ERROR: Se ha producido un error.");
                }
                a_Buzon.a_SemaforoProceso.release();
            } else {
                a_Buzon.a_SemaforoProceso.release();
            }

        }
    }
}

class Banca2 implements Runnable {

    private Buzon2 a_Buzon;

    public Banca2(Buzon2 a_Buzon) {
        this.a_Buzon = a_Buzon;
    }

    @Override
    public void run() {

        while (true) {
            try {
                a_Buzon.a_SemaforoBanca.acquire();

                Thread.sleep(3000);
                System.out.println("\n----[ Banca ]----\n");

                a_Buzon.a_SemaforoBanca.release();
//            a_Buzon.a_SemaforoBanca.acquire();


            } catch (InterruptedException e) {
                System.err.println("\n>>> ERROR: Se ha producido un error");
            }
        }
    }
}

class Buzon2 {

    public int a_Turno = 1;

    public Semaphore a_SemaforoBanca = new Semaphore(1, true);
    public Semaphore a_SemaforoProceso = new Semaphore(1, true);

}
