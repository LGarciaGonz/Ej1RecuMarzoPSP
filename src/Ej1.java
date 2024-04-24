import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Ej1 {
    public static final int NUMERO_HILOS = 4;

    public static void main(String[] args) {

        Scanner l_Sc = new Scanner(System.in);
        String l_CadenaNums;
        List<Integer> l_ListaNumeros = new ArrayList<Integer>();
        List<Tarea> l_ListaTareas = new ArrayList<Tarea>();
        Tarea l_Tarea = null;
        ThreadPoolExecutor l_Executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMERO_HILOS);
        List <Future<String>> l_ListaResultados = null;


        System.out.println("Introduce una serie de números separados por espacios:");
        l_CadenaNums = l_Sc.nextLine();

        l_ListaNumeros = crearLista(l_CadenaNums);


        for (Integer l_Num : l_ListaNumeros) {
            l_Tarea = new Tarea(l_Num);
            l_ListaTareas.add(l_Tarea);
        }

        try {

            l_ListaResultados = l_Executor.invokeAll(l_ListaTareas);
            l_Executor.shutdown();

            for (Future<String> l_Linea : l_ListaResultados) {
                System.out.println(l_Linea.get());
            }

        } catch (InterruptedException l_Ex) {
            System.out.println("ERROR: Ha fallado el invoke() con error: " + l_Ex);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public static List crearLista(String p_CadenaNums) {

        List<String> l_ListaString = new ArrayList<String>();
        List<Integer> l_ListaNumeros = new ArrayList<Integer>();

        l_ListaString = List.of(p_CadenaNums.split(" "));

        if (!l_ListaString.isEmpty()) {
            for (int i = 0; i < l_ListaString.size(); i++) {
                if (!l_ListaString.get(i).equals("")) {
                    l_ListaNumeros.add(Integer.parseInt(l_ListaString.get(i)));
                }
            }
        }

        return l_ListaNumeros;
    }


}

// Clase para mostrar el texto ------------------------
class Tarea implements Callable<String> {

    private Integer a_Numero = 0;

    public Tarea(Integer p_Numero) {
        a_Numero = p_Numero;
    }

    @Override
    public String call() throws Exception {

//        > [<número>] - [<contador>] – [<(no) es primo>]
        return "> [" + a_Numero + "] - [" + String.valueOf(a_Numero).length() + "] – [" + esPrimo(a_Numero) + "]";
    }

    public static String esPrimo(Integer p_Numero) {

        String l_EsPrimo = "es primo";
        int l_Contador = 0;

        if (p_Numero <= 1) {
            l_EsPrimo = "no es primo";
        }
        for (l_Contador = 2; l_Contador <= Math.sqrt(p_Numero); l_Contador++) {
            if (p_Numero % l_Contador == 0) {
                l_EsPrimo = "no es primo";
            }
        }

        return l_EsPrimo;
    }
}