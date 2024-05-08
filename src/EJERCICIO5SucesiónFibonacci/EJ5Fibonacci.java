package EJERCICIO5SucesiónFibonacci;

public class EJ5Fibonacci {

    private final static int LIMITE = 1000;

    // FORMA ITERATIVA ***********************
    public static void main(String[] args) {

        // Declaración de variables.
        int l_A = 0;
        int l_B = 1;
        int l_C = l_A + l_B;

        while (l_C < LIMITE) {

            System.out.print(l_C + " ");    // Mostrar suma en pantalla.

            l_C = l_A + l_B;    // Sumar los números.

            l_A = l_B; // Actualizar el primer número.
            l_B = l_C; // Actulizar el segundo número.

        }
    }
}