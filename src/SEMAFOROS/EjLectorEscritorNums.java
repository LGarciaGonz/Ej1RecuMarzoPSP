package SEMAFOROS;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static SEMAFOROS.EjLectorEscritorNums.MAX_NUMS;
import static SEMAFOROS.EjLectorEscritorNums.RUTA;


/**
 *  PRIMERA VERSIÓN DEL PROGRAMA: no se utilizan semáforos ni clases Buzón.
 */

public class EjLectorEscritorNums {

    public static final int NUM_HILOS = 2;
    public static final int MAX_NUMS = 5;
    public static final String RUTA = "src/resourcesJava/ArchivoNums.txt";

    public static void main(String[] args) {

        ExecutorService l_Ejecutor = (ExecutorService) Executors.newFixedThreadPool(NUM_HILOS);

        Escritor l_Tarea1 = new Escritor();
        Lector l_Tarea2 = new Lector();

        l_Ejecutor.execute(l_Tarea1);
        l_Ejecutor.execute(l_Tarea2);

        l_Ejecutor.shutdown();

    }

}

class Escritor implements Runnable {


    @Override
    public void run() {

        int l_ContadorNums = 1;
        int l_CantidadNums = l_ContadorNums + MAX_NUMS;

        while (true) {

            try {
//                File l_File = new File("src/resourcesJava");
                BufferedWriter l_Writer = new BufferedWriter(new FileWriter(RUTA));

                StringBuilder l_StrBuilder = new StringBuilder();

                for (l_ContadorNums = l_CantidadNums - MAX_NUMS; l_ContadorNums < l_CantidadNums; l_ContadorNums++) {
                    l_StrBuilder.append(l_ContadorNums + ",");
                }

                System.out.println("\nESCRITOR: " + l_StrBuilder);

                l_CantidadNums += MAX_NUMS;

                l_Writer.write(l_StrBuilder.toString());
                l_Writer.flush();
                l_Writer.close();
//                System.out.println(l_StrBuilder);

                Thread.sleep(1000);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Lector implements Runnable {

    @Override
    public void run() {

        while (true) {

            String[] a_Numeros = new String[0];

            System.out.print("\nLECTOR: ");
            try (BufferedReader l_Reader = new BufferedReader(new FileReader(RUTA))) {

                String l_Linea;

                while ((l_Linea = l_Reader.readLine()) != null) {

                    // Usar split para eliminar las comas y obtener los números
                    a_Numeros = l_Linea.split(",");

                }

                for (String l_Numero : a_Numeros) {
                    System.out.print("[" + l_Numero + "]"); // Imprimir cada número
                }

                Thread.sleep(1000);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
