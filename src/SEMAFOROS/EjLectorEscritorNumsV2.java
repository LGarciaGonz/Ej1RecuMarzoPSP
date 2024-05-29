package SEMAFOROS;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static SEMAFOROS.EjLectorEscritorNumsV2.*;

public class EjLectorEscritorNumsV2 {

    public static final int NUM_HILOS2 = 2;
    public static final int MAX_NUMS2 = 5;
    public static final int PAUSA_LECTOR = 500;
    public static final String RUTA_FICHERO = "FicherosInOut//ArchivoNumsV2.txt";
    public static final int EOF = -1;
    public static final char CARACTER_SEPARADOR = ',';

    public static void main(String[] args) {

        ExecutorService l_Ejecutor2 = Executors.newFixedThreadPool(NUM_HILOS2);

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


        int l_ContadorNums = 1;
        int l_CantidadNums = l_ContadorNums + MAX_NUMS2;
        FileWriter l_Writer = null;

        try {
            l_Writer = new FileWriter(RUTA_FICHERO);
        } catch (IOException e) {
            System.out.println("ERROR: no se ha podido abrir el fichero para escrtitura.");
        }

        while (true) {

            try {
                a_Buzon.adquirirEscritor(1);

                StringBuilder l_StrBuilder = new StringBuilder();

                for (l_ContadorNums = l_CantidadNums - MAX_NUMS2; l_ContadorNums < l_CantidadNums; l_ContadorNums++) {
                    l_StrBuilder.append(l_ContadorNums).append(",");
                }

                System.out.println("\nESCRITOR: " + l_StrBuilder);

                //    Thread.sleep(1000);

                l_CantidadNums += MAX_NUMS2;

                l_Writer.write(String.valueOf(l_StrBuilder));
                l_Writer.flush();

                a_Buzon.liberarLector(6);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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
        FileReader l_Reader = null;
        String l_Linea = "";
        int l_CaracterLeido = 0;

        try {
            l_Reader = new FileReader(RUTA_FICHERO);
        } catch (IOException e) {
            System.out.println("ERROR: no se ha podido abrir el fichero para escrtitura.");
        }

        while (true) {

            l_Linea = "";

            a_Buzon.adquirirLector(1);

            l_CaracterLeido = 0;
            while ((l_CaracterLeido != CARACTER_SEPARADOR) && (l_CaracterLeido != EOF)) {
                if (l_CaracterLeido > 0) l_Linea += (char) l_CaracterLeido;
                try {
                    l_CaracterLeido = l_Reader.read();
                } catch (IOException e) {
                    System.out.println("ERROR: No se ha podido leer del fichero.");
                }

            }

            if (l_CaracterLeido != EOF) {
                System.out.println("[" + l_Linea + "]");
                l_Linea = "";
                try {
                    Thread.sleep(PAUSA_LECTOR);
                } catch (InterruptedException e) {
                    System.out.println("ERROR: Ha fallado el sleep() del lector.");
                }
            } else a_Buzon.liberarEscritor(1);

        }
    }
}

class Buzon {

    public Semaphore l_SemEscritor = new Semaphore(1);
    public Semaphore l_SemLector = new Semaphore(0);

    public void adquirirEscritor(int p_Numero) {

        try {
            this.l_SemEscritor.acquire(p_Numero);
        } catch (InterruptedException e) {
            System.err.println("\n>>>> ERROR al adquirir el semáforo del escritor");
        }

    }

    public void liberarEscritor(int p_Numero) {
        this.l_SemEscritor.release(p_Numero);
    }

    public void adquirirLector(int p_Numero) {

        try {
            this.l_SemLector.acquire(p_Numero);
        } catch (InterruptedException e) {
            System.err.println("\n>>>> ERROR al adquirir el semáforo del lector");
        }
    }

    public void liberarLector(int p_Numero) {
        this.l_SemLector.release(p_Numero);
    }

}