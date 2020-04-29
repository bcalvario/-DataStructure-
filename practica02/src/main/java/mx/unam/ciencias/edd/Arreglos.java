package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.EventListener;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {
    }

    /**
     * Intercambia los indices recibidos de un arreglo.
     * 
     * @param arreglo el arreglo en el que estamos ordenando.
     * @param a       índice a intercambiar.
     * @param b       índice a intercambiar.
     */
    private static <T> void intercambia(T[] arreglo, int a, int b) {
        T t = arreglo[a];
        arreglo[a] = arreglo[b];
        arreglo[b] = t;
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * 
     * @param <T>        tipo del que puede ser el arreglo.
     * @param arreglo    el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     * @param a          índice inferior del rango a ordenar.
     * @param b          índice superior del rengo a ordenar.
     */
    public static <T> void quickSort(T[] arreglo, Comparator<T> comparador, int a, int b) {
        if (b <= a)
            return;
        int i = a;
        int j = b;
        while (i < j) {
            if (comparador.compare(arreglo[j], arreglo[a]) > 0)
                j--;
            else if (comparador.compare(arreglo[i], arreglo[a]) <= 0)
                i++;
            else {
                intercambia(arreglo, i, j);
            }
        }
        intercambia(arreglo, a, j);
        quickSort(arreglo, comparador, a, i - 1);
        quickSort(arreglo, comparador, i + 1, b);
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * 
     * @param <T>        tipo del que puede ser el arreglo.
     * @param arreglo    el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSort(arreglo, comparador, 0, arreglo.length - 1);
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * 
     * @param <T>     tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * 
     * @param <T>        tipo del que puede ser el arreglo.
     * @param arreglo    el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void selectionSort(T[] arreglo, Comparator<T> comparador) {
        for (int i = 0; i < arreglo.length; i++) {
            int m = i;
            for (int j = i + 1; j < arreglo.length; j++) {
                if (comparador.compare(arreglo[j], arreglo[m]) < 0)
                    m = j;
            }
            intercambia(arreglo, i, m);
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * 
     * @param <T>     tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice del
     * elemento en el arreglo, o -1 si no se encuentra.
     * 
     * @param <T>        tipo del que puede ser el arreglo.
     * @param arreglo    el arreglo dónde buscar.
     * @param elemento   el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        int inicio = 0;
        int fin = arreglo.length - 1;
        while (inicio <= fin) {
            int m = (fin + inicio) / 2;
            if (comparador.compare(arreglo[m], elemento) < 0)
                inicio = m + 1;
            else if (comparador.compare(arreglo[m], elemento) > 0)
                fin = m - 1;
            else
                return m;
        }
        return -1;
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice del
     * elemento en el arreglo, o -1 si no se encuentra.
     * 
     * @param <T>      tipo del que puede ser el arreglo.
     * @param arreglo  un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}