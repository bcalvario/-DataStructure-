package mx.unam.ciencias.edd.proyecto3;

import java.text.Normalizer;

public class Palabra implements Comparable<Palabra> {

    public String palabraOriginal;
    public String palabraNormalizada;
    public char identificador;
    public int numRepeticiones;

    public Palabra(String palabra) {
        palabraOriginal = palabra.replaceAll(",", "");
        palabraNormalizada = normaliza(palabraOriginal);
        numRepeticiones = 1;
    }

    /**
     * Método que normaliza la palabra.
     * 
     * @param palabra La palabra a depurar.
     * @return La palabra normalizada.
     */
    private String normaliza(String palabra) {
        palabra = Normalizer.normalize(palabra, Normalizer.Form.NFKD);
        palabra = palabra.replaceAll("[^\\p{IsAlphabetic}\\s]", "");
        palabra = palabra.toLowerCase();
        palabra = palabra.trim();
        return palabra;
    }

    public boolean equals(Object oobject) {
        Palabra k = new Palabra(oobject.toString());
        return palabraNormalizada.equals(k.palabraNormalizada);
    }

    public int hashCode() {
        return palabraNormalizada.hashCode();
    }

    public int compareTo(Palabra palabra) {
        if (numRepeticiones > palabra.numRepeticiones)
            return 1;
        if (numRepeticiones < palabra.numRepeticiones)
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        return palabraOriginal;
    }

}
