package EJERCICIO5SucesiónFibonacci;                                // Declaración del paquete

import java.util.concurrent.ForkJoinPool;                           // Importar la clase ForkJoinPool para trabajar con tareas en paralelo
import java.util.concurrent.RecursiveTask;                          // Importar la clase RecursiveTask para definir tareas recursivas

public class EJ5FibonacciV2 extends RecursiveTask<Boolean> {        // Declaración de la clase EJ5FibonacciV2, que extiende RecursiveTask

// FORMA RECURSIVA ***********************

    private static final int LIMITE = 1000;                         // Declaración de una constante LIMITE con valor 1000
    private int a_Num1 = 0, a_Num2 = 1, a_Suma = 0;                 // Declaración de variables enteras a_Num1, a_Num2 y a_Suma, inicializadas a 0, 1 y 0 respectivamente

    public EJ5FibonacciV2() {                                       // Constructor sin argumentos

    }

    public EJ5FibonacciV2(int p_Num1, int p_Num2, int p_Suma) {     // Constructor con tres argumentos
        this.a_Num1 = p_Num1;                                       // Inicializar a_Num1 con el valor del primer argumento
        this.a_Num2 = p_Num2;                                       // Inicializar a_Num2 con el valor del segundo argumento
        this.a_Suma = p_Suma;                                       // Inicializar a_Suma con el valor del tercer argumento
    }

    public static void main(String[] args) {                        // Método principal (main)
        EJ5FibonacciV2 l_Tarea = new EJ5FibonacciV2();              // Crear una nueva instancia de EJ5FibonacciV2

        int l_Num1 = 0, l_Num2 = 1, l_Suma = 0;                     // Declaración de variables enteras l_Num1, l_Num2 y l_Suma, inicializadas a 0, 1 y 0 respectivamente
        ForkJoinPool l_Pool = new ForkJoinPool();                   // Crear una nueva instancia de ForkJoinPool

        l_Tarea = new EJ5FibonacciV2(l_Num1, l_Num2, l_Suma);       // Inicializar l_Tarea con una nueva instancia de EJ5FibonacciV2 con los valores de l_Num1, l_Num2 y l_Suma

        l_Pool.invoke(l_Tarea);                                     // Invocar la tarea en el pool de ForkJoin
    }

    @Override
    protected Boolean compute() {                                   // Implementación del método compute() de RecursiveTask
        if (a_Num1 < LIMITE) {                                      // Comprobar si a_Num1 es menor que LIMITE
            calculoRecursivo();                                     // Llamar al método calculoRecursivo()
        }

        return true;                                                // Devolver true
    }

    private void calculoRecursivo() {                               // Método para calcular la secuencia de Fibonacci recursivamente

        // Declaración de variables.
        int l_SumaTemp = a_Suma;                                    // Declarar una variable entera l_SumaTemp e inicializarla con el valor de a_Suma

        System.out.print(a_Num1 + " ");                             // Mostrar a_Num1 en pantalla.

        l_SumaTemp = a_Num1 + a_Num2;                               // Sumar a_Num1 y a_Num2 y asignar el resultado a l_SumaTemp

        a_Num1 = a_Num2;                                            // Actualizar a_Num1 con el valor de a_Num2
        a_Num2 = l_SumaTemp;                                        // Actualizar a_Num2 con el valor de l_SumaTemp

        EJ5FibonacciV2 l_Tarea1 = new EJ5FibonacciV2(a_Num1, a_Num2, l_SumaTemp); // Crear una nueva instancia de EJ5FibonacciV2 con los valores actualizados

        l_Tarea1.fork();                                            // Llamar al método fork() en la nueva instancia de EJ5FibonacciV2
        l_Tarea1.join();                                            // Esperar a que la tarea l_Tarea1 se complete antes de continuar
    }
}