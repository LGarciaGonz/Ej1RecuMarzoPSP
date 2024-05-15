package EJERCICIO5SucesiónFibonacci;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Ej5FibonacciV3 extends RecursiveTask<Integer> {

    // Declaración de la constante LIMITE que define el límite de la secuencia Fibonacci
    private static final int LIMITE = 10;

    // Variables miembro para almacenar el número de Fibonacci y el tipo de impresión
    private int a_Numero = 0;
    private boolean a_Izquierdista = false;

    // Constructor sin argumentos
    public Ej5FibonacciV3() {

    }

    // Constructor con argumentos para inicializar el número de Fibonacci y el tipo de impresión
    public Ej5FibonacciV3(int p_Numero, boolean p_Izquierdista) {
        this.a_Numero = p_Numero;
        this.a_Izquierdista = p_Izquierdista;
    }

    // Método principal (main)
    public static void main(String[] args) {
        // Crear una instancia de Ej5FibonacciV3 con el límite y el tipo de impresión especificados
        Ej5FibonacciV3 l_Tarea = new Ej5FibonacciV3(LIMITE, true);

        // Crear un pool de ForkJoin
        ForkJoinPool l_Pool = new ForkJoinPool();

        // Invocar la tarea en el pool de ForkJoin
        l_Pool.invoke(l_Tarea);

        // Esperar a que la tarea se complete
        l_Tarea.join();
    }

    // Implementación del método compute() de RecursiveTask
    @Override
    protected Integer compute() {
        // Llamar al método para el cálculo recursivo de la secuencia Fibonacci
        return calculoRecursivo();
    }

    // Método para calcular la secuencia de Fibonacci recursivamente
    private int calculoRecursivo() {
        // Declaración de variables locales
        int l_Numero_ret = 0;
        int l_Tarea1Resultado = 0, l_Tarea2Resultado = 0;

        // Verificar si el número actual es 0 o 1
        if (a_Numero <= 1) {
            // Si es así, establecer el valor de retorno como el número actual
            l_Numero_ret = a_Numero;
        } else {

            // Si no es 0 o 1, crear dos nuevas instancias de Ej5FibonacciV3 para las sub-tareas
            Ej5FibonacciV3 l_Tarea1 = new Ej5FibonacciV3(a_Numero - 1, a_Izquierdista);
            Ej5FibonacciV3 l_Tarea2 = new Ej5FibonacciV3(a_Numero - 2, false);

            // Dividir las sub-tareas y realizarlas en paralelo
            l_Tarea1.fork();
            l_Tarea2.fork();

            // Obtener los resultados de las sub-tareas
            l_Tarea1Resultado = l_Tarea1.join();
            l_Tarea2Resultado = l_Tarea2.join();

            // Calcular el valor de retorno sumando los resultados de las sub-tareas
            l_Numero_ret = l_Tarea1Resultado + l_Tarea2Resultado;
        }

        // Si el tipo de impresión es izquierdista, imprimir el número actual
        if (a_Izquierdista) {
            System.out.print(l_Numero_ret + " ");
        }

        // Retornar el valor calculado
        return l_Numero_ret;
    }
}
