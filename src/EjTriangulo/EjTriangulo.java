package EjTriangulo;

public class EjTriangulo {

    private static final int ALTURA = 100;
    private static final String ASTERISCO = "*";
    private static final String ESPACIO = " ";

    public static void main(String[] args) {

        for (int l_ContFilas = 1; l_ContFilas <= ALTURA; l_ContFilas++) {

            // Espacios en blanco a la izquierda de los asteriscos.
            for (int l_ContEspaciosIz = 1; l_ContEspaciosIz <= ALTURA - l_ContFilas; l_ContEspaciosIz++) {
                System.out.print(ESPACIO);
            }

            // Imprime los asteriscos.
            if (l_ContFilas == 1 || l_ContFilas == ALTURA) {

                for (int l_ContAsteriscos = 1; l_ContAsteriscos <= 2 * l_ContFilas - 1; l_ContAsteriscos++) {
                    System.out.print(ASTERISCO);
                }

            } else {

                System.out.print(ASTERISCO);

                for (int l_ContEspacios2 = 1; l_ContEspacios2 <= 2 * l_ContFilas - 3; l_ContEspacios2++) {
                    System.out.print(ESPACIO);
                }

                if (l_ContFilas != 1) {
                    System.out.print(ASTERISCO);
                }
            }

            System.out.println();
        }
    }
}