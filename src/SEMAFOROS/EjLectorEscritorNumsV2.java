package SEMAFOROS;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static SEMAFOROS.EjLectorEscritorNumsV2.MAX_NUMS2;
import static SEMAFOROS.EjLectorEscritorNumsV2.RUTA2;

public class EjLectorEscritorNumsV2 {

    public static final int NUM_HILOS2 = 2;
    public static final int MAX_NUMS2 = 5;
    public static final String RUTA2 = "src/resourcesJava/ArchivoNumsV2.txt";

    public static void main(String[] args) {

        ExecutorService l_Ejecutor2 = (ExecutorService) Executors.newFixedThreadPool(NUM_HILOS2);

        Buzon l_Buzon = new Buzon();

        Escritor2 l_Tarea1 = new Escritor2(l_Buzon);
        Lector2 l_Tarea2 = new Lector2(l_Buzon);

        l_Ejecutor2.submit(l_Tarea1);
        l_Ejecutor2.submit(l_Tarea2);

        l_Ejecutor2.shutdown();

    }

}

class Escritor2 implements Runnable {

    private Buzon a_Buzon;

    public Escritor2(Buzon a_Buzon) {
        this.a_Buzon = a_Buzon;
    }

    @Override
    public void run() {

        int l_ContadorFinal = 0;
        int l_ContadorNums = 1;
        int l_CantidadNums = l_ContadorNums + MAX_NUMS2;

        while (l_ContadorFinal <= 20) {

            try {
                BufferedWriter l_Writer = new BufferedWriter(new FileWriter(RUTA2));

                StringBuilder l_StrBuilder = new StringBuilder();

                for (l_ContadorNums = l_CantidadNums - MAX_NUMS2; l_ContadorNums < l_CantidadNums; l_ContadorNums++) {
                    l_StrBuilder.append(l_ContadorNums + ",");
                }

                System.out.println("\nESCRITOR: " + l_StrBuilder);

                l_CantidadNums += MAX_NUMS2;

                l_Writer.write(l_StrBuilder.toString());
                l_Writer.flush();
                l_Writer.close();

                a_Buzon.l_Semaforo.acquire(1);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            l_ContadorFinal++;
        }
    }
}


class Lector2 implements Runnable {

    private Buzon a_Buzon;

    public Lector2(Buzon a_Buzon) {
        this.a_Buzon = a_Buzon;
    }

    @Override
    public void run() {

        int l_ContadorFinal2 = 0;

        while (l_ContadorFinal2 <= 20) {

            String[] a_Numeros = new String[0];

            System.out.print("\nLECTOR: ");
            try (BufferedReader l_Reader = new BufferedReader(new FileReader(RUTA2))) {

                String l_Linea;

                while ((l_Linea = l_Reader.readLine()) != null) {

                    // Usar split para eliminar las comas y obtener los números
                    a_Numeros = l_Linea.split(",");

                }

                for (String l_Numero : a_Numeros) {
                    System.out.print("[" + l_Numero + "]"); // Imprimir cada número
                }

                a_Buzon.l_Semaforo.release(1);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            l_ContadorFinal2++;
        }
    }
}

class Buzon {

    public Semaphore l_Semaforo = new Semaphore(1);

}
