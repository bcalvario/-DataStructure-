package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>
 * Clase abstracta para árboles binarios genéricos.
 * </p>
 *
 * <p>
 * La clase proporciona las operaciones básicas para árboles binarios, pero deja
 * la implementación de varias en manos de las subclases concretas.
 * </p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        public T elemento;
        /** El padre del vértice. */
        public Vertice padre;
        /** El izquierdo del vértice. */
        public Vertice izquierdo;
        /** El derecho del vértice. */
        public Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del vértice.
         */
        public Vertice(T elemento) {
            this.elemento = elemento;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * 
         * @return <code>true</code> si el vértice tiene padre, <code>false</code> en
         *         otro caso.
         */
        @Override
        public boolean hayPadre() {
            return padre != null;
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * 
         * @return <code>true</code> si el vértice tiene izquierdo, <code>false</code>
         *         en otro caso.
         */
        @Override
        public boolean hayIzquierdo() {
            return izquierdo != null;
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * 
         * @return <code>true</code> si el vértice tiene derecho, <code>false</code> en
         *         otro caso.
         */
        @Override
        public boolean hayDerecho() {
            return derecho != null;
        }

        /**
         * Regresa el padre del vértice.
         * 
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override
        public VerticeArbolBinario<T> padre() {
            if (!hayPadre())
                throw new NoSuchElementException();
            return padre;
        }

        /**
         * Regresa el izquierdo del vértice.
         * 
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override
        public VerticeArbolBinario<T> izquierdo() {
            if (!hayIzquierdo())
                throw new NoSuchElementException();
            return izquierdo;
        }

        /**
         * Regresa el derecho del vértice.
         * 
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override
        public VerticeArbolBinario<T> derecho() {
            if (!hayDerecho())
                throw new NoSuchElementException();
            return derecho;
        }

        /**
         * Regresa la altura del vértice.
         * 
         * @return la altura del vértice.
         */
        @Override
        public int altura() {
            int izquierdoAux = -1;
            int derechoAux = -1;
            if (hayDerecho())
                derechoAux = derecho.altura();
            if (hayIzquierdo())
                izquierdoAux = izquierdo.altura();
            if (izquierdoAux >= derechoAux)
                return izquierdoAux + 1;
            else
                return derechoAux + 1;
        }

        /**
         * Regresa la profundidad del vértice.
         * 
         * @return la profundidad del vértice.
         */
        @Override
        public int profundidad() {
            if (hayPadre())
                return padre.profundidad() + 1;
            else
                return 0;
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * 
         * @return el elemento al que apunta el vértice.
         */
        @Override
        public T get() {
            return elemento;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es <em>recursiva</em>. Las
         * clases que extiendan {@link Vertice} deben sobrecargar el método
         * {@link Vertice#equals}.
         * 
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste vértice, y
         *         los descendientes de ambos son recursivamente iguales;
         *         <code>false</code> en otro caso.
         */
        @Override
        public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
            Vertice vertice = (Vertice) objeto;
            return raiz.elemento.equals(vertice.elemento) && equals(raiz.izquierdo, vertice.izquierdo)
                    && equals(raiz.derecho, vertice.derecho);
        }

        private boolean equals(Vertice izquierdo, Vertice derecho) {
            if (izquierdo == null && derecho == null)
                return true;
            else if (izquierdo != null && derecho == null || izquierdo == null && derecho != null)
                return false;
            return izquierdo.elemento.equals(derecho.elemento) && equals(izquierdo.izquierdo, derecho.izquierdo)
                    && equals(izquierdo.derecho, derecho.derecho);
        }

        /**
         * Regresa una representación en cadena del vértice.
         * 
         * @return una representación en cadena del vértice.
         */
        public String toString() {
            String cadena = "";
            cadena += elemento;
            return cadena;
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {
    }

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario tendrá
     * los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        for (T t : coleccion)
            agrega(t);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan sobrecargarlo
     * y permitir que cada estructura de árbol binario utilice distintos tipos de
     * vértices.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su raíz.
     * 
     * @return la altura del árbol.
     */
    public int altura() {
        if (raiz == null)
            return -1;
        return raiz.altura();
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * 
     * @return el número de elementos en el árbol.
     */
    @Override
    public int getElementos() {
        return elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * 
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol; <code>false</code>
     *         en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        if (busca(elemento) == null)
            return false;
        return true;
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <code>null</code>.
     * 
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        return busca(elemento, raiz);
    }

    /**
     * Método auxiliar que busca el vértice de un elemento en el árbol.
     * 
     * @param elemento elemento a buscar.
     * @param vertice  vértice donde estamos parados.
     * @return un vértice que contiene el elemento buscado, si no lo encuentra;
     *         <tt>null</tt> en otro caso.
     */
    private Vertice busca(T elemento, Vertice vertice) {
        if (vertice == null || elemento == null)
            return null;
        if (vertice.elemento.equals(elemento))
            return vertice;
        Vertice i = busca(elemento, vertice.izquierdo);
        Vertice d = busca(elemento, vertice.derecho);
        return i != null ? i : d;
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * 
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
        if (raiz == null)
            throw new NoSuchElementException();
        return raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * 
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en otro
     *         caso.
     */
    @Override
    public boolean esVacia() {
        return raiz == null;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override
    public void limpia() {
        raiz = null;
        elementos = 0;
    }

    /**
     * Compara el árbol con un objeto.
     * 
     * @param objeto el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        ArbolBinario<T> arbol = (ArbolBinario<T>) objeto;
        if (raiz == null)
            return (arbol.raiz == null);
        return raiz.equals(arbol.raiz);
    }

    /**
     * Método auxiliar para crear la cadena antes de un vértice.
     * 
     * @param l   nivel.
     * @param arr arreglo.
     * @return cadena.
     */
    private String dibujaEspacios(int l, int[] arr) {
        String cadena = "";
        for (int i = 0; i < l; i++)
            if (arr[i] == 1)
                cadena += "│  ";
            else
                cadena += "   ";
        return cadena;
    }

    /**
     * Regresa una representación en cadena del árbol.
     * 
     * @return una representación en cadena del árbol.
     */
    @Override
    public String toString() {
        if (raiz == null) {
            return "";
        }
        int[] A = new int[altura() + 1];
        for (int i = 0; i < altura() + 1; i++)
            A[i] = 0;
        return cad(raiz, 0, A);
    }

    /**
     * Método auxiliar recursivo para crear la cadena que representa la línea de
     * vértices.
     * 
     * @param v       vértice a representar.
     * @param nivel   nivel.
     * @param arreglo arreglo binario.
     */
    private String cad(Vertice v, int l, int[] arr) {
        String cadena = v.toString() + "\n";
        arr[l] = 1;
        if (v.hayIzquierdo() && v.hayDerecho()) {
            cadena += dibujaEspacios(l, arr);
            cadena += "├─›";
            cadena += cad(v.izquierdo, l + 1, arr);
            cadena += dibujaEspacios(l, arr);
            cadena += "└─»";
            arr[l] = 0;
            cadena += cad(v.derecho, l + 1, arr);
        } else if (v.hayIzquierdo()) {
            cadena += dibujaEspacios(l, arr);
            cadena += "└─›";
            arr[l] = 0;
            cadena += cad(v.izquierdo, l + 1, arr);
        } else if (v.hayDerecho()) {
            cadena += dibujaEspacios(l, arr);
            cadena += "└─»";
            arr[l] = 0;
            cadena += cad(v.derecho, l + 1, arr);
        }
        return cadena;
    }

    /**
     * Convierte el vértice (visto como instancia de {@link VerticeArbolBinario}) en
     * vértice (visto como instancia de {@link Vertice}). Método auxiliar para hacer
     * esta audición en un único lugar.
     * 
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice) vertice;
    }
}
