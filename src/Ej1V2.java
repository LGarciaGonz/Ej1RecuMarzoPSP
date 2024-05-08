import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// EJERCICIO RECURSIVO CON FORK JOIN ---------------------------

public class Ej1V2 extends RecursiveTask<Boolean> {
    private static final int LONGITUD_ARRAY = 100;                          // Definición de la longitud del array
    private static final int UMBRAL = 5;                                    // Umbral para determinar la recursión
    private int[] a_Array;                                                  // Array de números
    private int a_Inicio, a_Fin;                                            // Índices de inicio y fin del subarray

    public Ej1V2() {
    }

    public Ej1V2(int[] p_Array, int p_Inicio, int p_Fin) {                  // Constructor con parámetros para inicializar el array y los índices
        this.a_Array = p_Array;
        this.a_Inicio = p_Inicio;
        this.a_Fin = p_Fin;
    }

    @Override
    protected Boolean compute() {                                           // Método que ejecuta la tarea

        if ((a_Fin - a_Inicio) <= UMBRAL) {                                 // Si el tamaño del subarray es menor o igual al umbral, se ejecuta un método iterativo
            getPrimoIterativo();
        } else {                                                            // De lo contrario, se ejecuta un método recursivo
            getPrimoRecursivo();
        }

        return true;                                                        // Devuelve verdadero al finalizar la tarea
    }

    public static String esPrimo(Integer p_Numero) {                        // Método estático que verifica si un número es primo

        String l_EsPrimo = "es primo";                                      // Inicializa la cadena con "es primo"
        int l_Contador = 0;

        if (p_Numero <= 1) {                                                // Si el número es menor o igual a 1, no es primo
            l_EsPrimo = "no es primo";
        }
        for (l_Contador = 2; l_Contador <= Math.sqrt(p_Numero); l_Contador++) { // Verifica si el número es divisible por algún número hasta su raíz cuadrada
            if (p_Numero % l_Contador == 0) {                               // Si es divisible, no es primo
                l_EsPrimo = "no es primo";
            }
        }

        return l_EsPrimo;                                                   // Devuelve si el número es primo o no
    }

    // Forma RECURSIVA ***************
    private void getPrimoRecursivo() {                                      // Método privado para calcular los números primos de forma recursiva

        int l_Medio = ((a_Inicio + a_Fin) / 2) + 1;                         // Calcula el punto medio del subarray

        // Crear tareas recursivas con cada mitad del array
        Ej1V2 l_Tarea1 = new Ej1V2(a_Array, a_Inicio, l_Medio);
        Ej1V2 l_Tarea2 = new Ej1V2(a_Array, l_Medio, a_Fin);

        // Iniciar la ejecución paralela de las dos tareas.
        l_Tarea1.fork();
        l_Tarea2.fork();
    }

    private String getPrimoIterativo() {                                    // Método privado para calcular los números primos de forma iterativa

        String l_Resultado = "";                                            // Inicializa la cadena de resultado

        for (int l_Contador = 0; l_Contador < LONGITUD_ARRAY; l_Contador++) { // Itera sobre el array
            System.out.println("> [" + a_Array[l_Contador] + "] - [" + String.valueOf(a_Array).length() + "] – [" + esPrimo(a_Array[l_Contador]) + "]"); // Imprime cada número del array y si es primo o no
        }

        return l_Resultado;                                                 // Devuelve el resultado
    }

    public static void main(String[] args) {                                // Método principal

        Ej1V2 l_Tarea = new Ej1V2();                                        // Crea una instancia de la tarea
        int[] l_ArrayNumeros = rellenarArray();                             // Crea un array de números
        int l_Inicio = 0, l_Fin = 0;                                        // Índices de inicio y fin del array
        ForkJoinPool l_Pool = new ForkJoinPool();                           // Pool de hilos para ejecutar las tareas de forma paralela

        l_Tarea = new Ej1V2(l_ArrayNumeros, l_Inicio, l_Fin);               // Inicializa la tarea con el array y los índices

        l_Pool.invoke(l_Tarea);                                             // Invoca la tarea en el pool de hilos
    }

    public static int[] rellenarArray() {                                   // Método estático para rellenar el array con los índices

        int[] l_ArrayNumeros = new int[Ej1V2.LONGITUD_ARRAY];               // Crea un array de números con la longitud especificada

        // Rellenar el array con los índices
        for (int l_Contador = 0; l_Contador < l_ArrayNumeros.length; l_Contador++) {
            l_ArrayNumeros[l_Contador] = l_Contador;
        }

        return l_ArrayNumeros;                                              // Devuelve el array rellenado
    }
}