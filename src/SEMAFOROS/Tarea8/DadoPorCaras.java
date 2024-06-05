package SEMAFOROS.Tarea8;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static SEMAFOROS.Tarea8.DadoPorCaras.ORDEN_PARADA;

public class DadoPorCaras {

    public static final int ORDEN_PARADA = 6000;
    public static final int NUM_HILOS = 7;

    public static void main(String[] args) {

        ExecutorService l_Ejecutor = (ExecutorService) Executors.newFixedThreadPool(NUM_HILOS);
        Buzon l_Buzon = new Buzon();

        Banca l_Banca = new Banca(l_Buzon);

        Dado l_Dado1 = new Dado(l_Buzon, 1);
        Dado l_Dado2 = new Dado(l_Buzon, 2);
        Dado l_Dado3 = new Dado(l_Buzon, 3);
        Dado l_Dado4 = new Dado(l_Buzon, 4);
        Dado l_Dado5 = new Dado(l_Buzon, 5);
        Dado l_Dado6 = new Dado(l_Buzon, 6);

        l_Ejecutor.submit(l_Banca);

        l_Ejecutor.submit(l_Dado1);
        l_Ejecutor.submit(l_Dado2);
        l_Ejecutor.submit(l_Dado3);
        l_Ejecutor.submit(l_Dado4);
        l_Ejecutor.submit(l_Dado5);
        l_Ejecutor.submit(l_Dado6);

//        l_Ejecutor.shutdown();

    }
}


class Dado implements Runnable {

    private final Buzon a_Buzon;
    private final int a_NumDado;

    public Dado(Buzon p_Buzon, int p_NumDado) {
        this.a_Buzon = p_Buzon;
        this.a_NumDado = p_NumDado;
    }

    @Override
    public void run() {

        // Declaración de los contadores de cada número.
        int l_ContCara1 = 0, l_ContCara2 = 0, l_ContCara3 = 0, l_ContCara4 = 0, l_ContCara5 = 0, l_ContCara6 = 0;
        int l_Incremento = 0;
        int l_Random = 0;


        while (a_Buzon.a_ContadorTiradas < ORDEN_PARADA) {

            try {
                a_Buzon.a_Semaforo2.acquire();
            } catch (InterruptedException e) {
                System.err.println("\n>>> ERROR: Se ha producido un error.");
            }

            if (a_Buzon.a_ContadorTiradas < ORDEN_PARADA) {

                // Generar número aleatorio del 1 al 6.
                l_Random = (int) (Math.random() * 6) + 1;

                if (l_Random == a_NumDado) l_Incremento = 1;
                else l_Incremento = -1;


                switch (l_Random) {
                    case 1 -> {
                        a_Buzon.a_D1 += l_Incremento;
                        l_ContCara1+= l_Incremento;
                    }
                    case 2 -> {
                        a_Buzon.a_D2 += l_Incremento;
                        l_ContCara2+= l_Incremento;
                    }
                    case 3 -> {
                        a_Buzon.a_D3 += l_Incremento;
                        l_ContCara3+= l_Incremento;
                    }
                    case 4 -> {
                        a_Buzon.a_D4 += l_Incremento;
                        l_ContCara4+= l_Incremento;
                    }
                    case 5 -> {
                        a_Buzon.a_D5 += l_Incremento;
                        l_ContCara5+= l_Incremento;
                    }
                    case 6 -> {
                        a_Buzon.a_D6 += l_Incremento;
                        l_ContCara6+= l_Incremento;
                    }

                }
         /*       } else {
                    switch (a_NumDado) {
                        case 1 -> {
                            a_Buzon.a_D1--;
                            l_ContCara1--;
                        }
                        case 2 -> {
                            a_Buzon.a_D2--;
                            l_ContCara2--;
                        }
                        case 3 -> {
                            a_Buzon.a_D3--;
                            l_ContCara3--;
                        }
                        case 4 -> {
                            a_Buzon.a_D4--;
                            l_ContCara4--;
                        }
                        case 5 -> {
                            a_Buzon.a_D5--;
                            l_ContCara5--;
                        }
                        case 6 -> {
                            a_Buzon.a_D6--;
                            l_ContCara6--;
                        }
                    } */
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            a_Buzon.a_ContadorTiradas++;
            a_Buzon.a_Semaforo2.release();

        }


        // Declaración de variables.
        LocalDateTime l_Hora = LocalDateTime.now();
        String l_HoraFormatted = l_Hora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        System.out.println(l_HoraFormatted + " - CARA" + a_NumDado + " - " + l_ContCara1 + ", " + l_ContCara2 + ", " + l_ContCara3 + ", " + l_ContCara4 + ", " + l_ContCara5 + ", " + l_ContCara6);
    }
}

class Banca implements Runnable {

    private Buzon a_Buzon;

    public Banca(Buzon a_Buzon) {
        this.a_Buzon = a_Buzon;
    }

    @Override
    public void run() {

        // Adquirir el token del semáforo.
        try {
            a_Buzon.a_Semaforo1.acquire();

            while (a_Buzon.a_ContadorTiradas < ORDEN_PARADA) {

                // Pausa de 5 segundos en cada ejecución.
                Thread.sleep(5000);

                // Declaración de variables.
                LocalDateTime l_Hora = LocalDateTime.now();
                String l_HoraFormatted = l_Hora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                if (a_Buzon.a_ContadorTiradas < ORDEN_PARADA) {

                    // Imprimir los contadores de cada número almacenados en el Buzon.
                    System.out.println("\n" + l_HoraFormatted + " - BANCA 1: " + a_Buzon.a_D1);
                    System.out.println(l_HoraFormatted + " - BANCA 2: " + a_Buzon.a_D2);
                    System.out.println(l_HoraFormatted + " - BANCA 3: " + a_Buzon.a_D3);
                    System.out.println(l_HoraFormatted + " - BANCA 4: " + a_Buzon.a_D4);
                    System.out.println(l_HoraFormatted + " - BANCA 5: " + a_Buzon.a_D5);
                    System.out.println(l_HoraFormatted + " - BANCA 6: " + a_Buzon.a_D6 + " -- TIRADAS " + a_Buzon.a_ContadorTiradas + "\n");

                }
            }
            // Liberar y adquirir el token del semáforo.
            a_Buzon.a_Semaforo1.release();
            a_Buzon.a_Semaforo1.acquire();

            // Imprimir los totales de cada contador.
            System.out.println("\t\tTOTALES: " + a_Buzon.a_D1 + ", " + a_Buzon.a_D2 + ", " + a_Buzon.a_D3 + ", " + a_Buzon.a_D4 + ", " + a_Buzon.a_D5 + ", " + a_Buzon.a_D6);

        } catch (InterruptedException e) {
            System.err.println("\n>>> ERROR: Se ha producido un error");
        }
    }
}

class Buzon {

    public int a_D1;
    public int a_D2;
    public int a_D3;
    public int a_D4;
    public int a_D5;
    public int a_D6;

    public int a_ContadorTiradas;

    public Semaphore a_Semaforo1 = new Semaphore(1, true);
    public Semaphore a_Semaforo2 = new Semaphore(1, true);

}