package mx.unam.ciencias.edd.proyecto2;

public class Proyecto2 {

    /**
     * Corre el programa
     * 
     * @param args dirección o direcciones a ordenar.
     */
    public static void main(String[] args) {
        run(args);
    }

    public static void run(String[] args) {
        try {
            corre(args);
        } catch (Exception e) {
            System.err.println("Ocurrió un error");
        }
    }

    /**
     * 
     * @param args
     */
    private static void corre(String[] args) {
        Lector lector = new Lector(args);
        Estructura e = new Estructura(lector);

        if (e.svg != null)
            System.out.println(e.svg.imprime());
        if (e.arbolSVG != null)
            System.out.println(e.arbolSVG.imprime());

    }
}