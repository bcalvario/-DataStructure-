package mx.unam.ciencias.edd.proyecto3;

import java.util.NoSuchElementException;
import mx.unam.ciencias.edd.*;
import java.io.BufferedReader;
import java.text.Normalizer;
import java.io.IOException;
import java.io.FileReader;
import java.util.Iterator;
import java.io.File;

public class LectorArchivo {

    private String archivo;
    private Lista<Palabra> palabrasSinRepeticion;
    public Diccionario<Palabra, Integer> palabrasConRepeticion;
    private Diccionario<Palabra, Integer> masFrecuentes;
    private Conjunto<Palabra> conjuntoSiete;
    public static int totalPalabras;

    public LectorArchivo(String archivo) throws IOException {
        this.archivo = archivo;
        conjuntoSiete = new Conjunto<Palabra>();
        leeArchivo();
        masFrecuentes = calculaFrecuentes();
        totalPalabras = palabrasConRepeticion.getElementos() + 15;
    }

    private void leeArchivo() throws IOException {
        File f = new File(archivo);
        if (!f.exists())
            throw new NoSuchElementException();

        BufferedReader br = new BufferedReader(new FileReader(f));
        Lista<Palabra> lista = new Lista<>();
        String linea;
        String[] aux;

        while ((linea = br.readLine()) != null) {
            linea = Normalizer.normalize(linea, Normalizer.Form.NFKD);
            linea = linea.replaceAll("[^\\p{IsAlphabetic}\\s]", "");
            linea = linea.toLowerCase();
            linea = linea.trim();
            aux = linea.split(" ");
            for (int i = 0; i < aux.length; i++) {
                if (aux[i].equals("") || aux[i].equals("\n") || aux[i].equals(" ") || aux[i].equals("\t")) {
                    continue;
                } else {
                    lista.agrega(new Palabra(aux[i]));
                }
                if (aux[i].length() >= 7)
                    this.conjuntoSiete.agrega(new Palabra(aux[i]));
            }
        }
        br.close();
        palabrasSinRepeticion = lista;

        Diccionario<Palabra, Integer> frecuente = new Diccionario<>();
        int cont = 0;
        for (Palabra wrd : palabrasSinRepeticion) {
            try {
                cont = frecuente.get(wrd);
            } catch (NoSuchElementException nse) {
                cont = 0;
            }
            frecuente.agrega(wrd, cont + 1);
        }
        palabrasConRepeticion = frecuente;
    }

    private Diccionario<Palabra, Integer> calculaFrecuentes() {
        Diccionario<Palabra, Integer> frecuente = palabrasConRepeticion;
        Diccionario<Palabra, Integer> aux = new Diccionario<>();
        Iterator<Palabra> i = null;
        Palabra masFrecuente = null;
        Palabra q = null;
        while (aux.getElementos() < 15) {
            i = frecuente.iteradorLlaves();
            masFrecuente = i.next();
            while (i.hasNext()) {
                q = i.next();
                if (frecuente.get(masFrecuente) > frecuente.get(q)) {
                    continue;
                } else {
                    masFrecuente = q;
                }
            }
            masFrecuente.numRepeticiones = frecuente.get(masFrecuente);
            aux.agrega(masFrecuente, frecuente.get(masFrecuente));
            frecuente.elimina(masFrecuente);
        }
        int a = 65;
        i = aux.iteradorLlaves();
        while (i.hasNext()) {
            masFrecuente = i.next();
            masFrecuente.identificador = 65;
            a++;
        }
        return aux;
    }

    public Diccionario<Palabra, Integer> getPalabrasConRepeticion() {
        return palabrasConRepeticion;
    }

    public Diccionario<Palabra, Integer> getMasFrecuentes() {
        return masFrecuentes;
    }

    public String getArchivo() {
        return archivo;
    }

    public Conjunto<Palabra> getConjuntoSiete() {
        return conjuntoSiete;
    }

    @Override
    public String toString() {
        return "Total de palabras: " + (palabrasConRepeticion.getElementos() + 15) + ". " + "Las 15 palabras más frecuentes: " + masFrecuentes.toString();
    }

}
