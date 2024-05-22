package EjTriangulo;

public class EjTrianguloV2 {

    private static final int ALTURA = 20;
    private static final String ASTERISCO = "*";
    private static final String ESPACIO = " ";

    public static void main(String[] args) {

        for (int l_ContFilas = 1; l_ContFilas <= ALTURA; l_ContFilas++) {

            // Espacios en blanco a la izquierda de los asteriscos.
            System.out.print(ESPACIO.repeat(ALTURA - l_ContFilas));

            // Imprime los asteriscos.
            if (l_ContFilas == 1 || l_ContFilas == ALTURA) {
                System.out.println(ASTERISCO.repeat(2 * l_ContFilas - 1));
            } else {
                System.out.println(ASTERISCO + ESPACIO.repeat(2 * l_ContFilas - 3) + ASTERISCO);
            }
        }
    }
}