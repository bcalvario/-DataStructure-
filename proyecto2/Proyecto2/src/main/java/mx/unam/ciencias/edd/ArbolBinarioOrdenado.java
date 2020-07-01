package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>
 * Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.
 * </p>
 *
 * <p>
 * Un árbol instancia de esta clase siempre cumple que:
 * </p>
 * <ul>
 * <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 * descendientes por la izquierda.</li>
 * <li>Cualquier elemento en el árbol es menor o igual que todos sus
 * descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        public Iterador() {
            pila = new Pila<>();
            Vertice vertice = raiz;
            while (vertice != null) {
                pila.mete(vertice);
                vertice = vertice.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override
        public T next() {
            Vertice vertice = pila.saca();
            Vertice elemento = vertice.derecho;
            while (elemento != null) {
                pila.mete(elemento);
                elemento = elemento.izquierdo;
            }
            return vertice.elemento;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede garantizar
     * que existe <em>inmediatamente</em> después de haber agregado un elemento al
     * árbol. Si cualquier operación distinta a agregar sobre el árbol se ejecuta
     * después de haber agregado un elemento, el estado de esta variable es
     * indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros de
     * {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() {
        super();
    }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol binario
     *                  ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        Vertice nuevo = nuevoVertice(elemento);
        elementos++;
        if (raiz == null) {
            raiz = ultimoAgregado = nuevo;
        } else
            agrega(elemento, raiz);
    }

    /**
     * Método auxiliar
     * 
     * @param actual
     * @param nuevo
     */
    private void agrega(T actual, Vertice nuevo) {
        if (actual.compareTo(nuevo.elemento) <= 0) {
            if (!nuevo.hayIzquierdo()) {
                nuevo.izquierdo = nuevoVertice(actual);
                nuevo.izquierdo.padre = nuevo;
                ultimoAgregado = nuevo.izquierdo;
            } else
                agrega(actual, nuevo.izquierdo);
        } else {
            if (!nuevo.hayDerecho()) {
                nuevo.derecho = nuevoVertice(actual);
                nuevo.derecho.padre = nuevo;
                ultimoAgregado = nuevo.derecho;
            } else
                agrega(actual, nuevo.derecho);
        }
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        Vertice v = (Vertice) busca(elemento);
        if (v == null)
            return;
        elementos--;
        if (v.hayDerecho() && v.hayIzquierdo()) {
            eliminaVertice(intercambiaEliminable(v));
        } else {
            eliminaVertice(v);
        }
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más un
     * hijo.
     * 
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se intercambió.
     *         El vértice regresado tiene a lo más un hijo distinto de
     *         <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        Vertice v = max(vertice);
        Vertice elemento = nuevoVertice(vertice.elemento);
        vertice.elemento = v.elemento;
        v.elemento = elemento.elemento;
        return v;
    }

    private Vertice max(Vertice vertice) {
        Vertice v = vertice.izquierdo;
        while (v.derecho != null) {
            v = v.derecho;
        }
        return v;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de <code>null</code>
     * subiendo ese hijo (si existe).
     * 
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo distinto de
     *                <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        Vertice hijo = null;
        Vertice padre = null;
        if (vertice.hayIzquierdo())
            hijo = vertice.izquierdo;
        if (vertice.hayDerecho())
            hijo = vertice.derecho;
        if (vertice.hayPadre())
            padre = vertice.padre;
        if (padre != null) {
            if (padre.izquierdo == vertice) {
                padre.izquierdo = hijo;
            } else {
                padre.derecho = hijo;
            }
        } else if (padre == null)
            raiz = hijo;
        if (hijo != null)
            hijo.padre = padre;
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * 
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    @Override
    public VerticeArbolBinario<T> busca(T elemento) {
        return busca(elemento, raiz);
    }

    /**
     * Función auxiliar busca.
     * 
     * @param elemento el elemento a buscar.
     * @param vertice  el vertice.
     * @return un vértice que contiene al elemento buscado si lo encuentra;
     */
    private Vertice busca(T elemento, Vertice vertice) {
        if (vertice == null || elemento == null)
            return null;
        if (elemento.compareTo(vertice.get()) == 0)
            return vertice;
        if (elemento.compareTo(vertice.get()) < 0)
            return busca(elemento, vertice.izquierdo);
        return busca(elemento, vertice.derecho);
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al árbol. Este
     * método sólo se puede garantizar que funcione <em>inmediatamente</em> después
     * de haber invocado al método {@link agrega}. Si cualquier operación distinta a
     * agregar sobre el árbol se ejecuta después de haber agregado un elemento, el
     * comportamiento de este método es indefinido.
     * 
     * @return el vértice que contiene el último elemento agregado al árbol, si el
     *         método es invocado inmediatamente después de agregar un elemento al
     *         árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no tiene
     * hijo izquierdo, el método no hace nada.
     * 
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        if (vertice == null || !vertice.hayIzquierdo())
            return;
        Vertice v = (Vertice) vertice;
        Vertice verticePadre = v.padre;
        Vertice verticeIzquierdo = v.izquierdo;
        Vertice aux = verticeIzquierdo.derecho;
        verticeIzquierdo.derecho = v;
        v.padre = verticeIzquierdo;
        if (aux != null)
            aux.padre = v;
        v.izquierdo = aux;
        verticeIzquierdo.padre = verticePadre;
        if (verticePadre == null) {
            raiz = verticeIzquierdo;
        } else {
            if (verticePadre.izquierdo == v)
                verticePadre.izquierdo = verticeIzquierdo;
            else
                verticePadre.derecho = verticeIzquierdo;
        }
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * 
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        if (vertice == null || !vertice.hayDerecho())
            return;
        Vertice v = (Vertice) vertice;
        Vertice verticePadre = v.padre;
        Vertice verticeDerecho = v.derecho;
        Vertice aux = verticeDerecho.izquierdo;
        verticeDerecho.padre = v.padre;
        verticeDerecho.izquierdo = v;
        v.padre = verticeDerecho;
        if (aux != null)
            aux.padre = v;
        v.derecho = aux;
        verticeDerecho.padre = verticePadre;
        if (verticePadre == null) {
            raiz = verticeDerecho;
        } else {
            if (verticePadre.izquierdo == v)
                verticePadre.izquierdo = verticeDerecho;
            else
                verticePadre.derecho = verticeDerecho;
        }
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la acción
     * recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        dfsPreOrder(accion, raiz);
    }

    /**
     * Método auxiliar.
     * 
     * @param accion
     * @param v      Vertice al que se le aplica acción.
     */
    private void dfsPreOrder(AccionVerticeArbolBinario<T> accion, Vertice v) {
        if (v == null)
            return;
        accion.actua(v);
        if (v.hayIzquierdo())
            dfsPreOrder(accion, v.izquierdo);
        if (v.hayDerecho())
            dfsPreOrder(accion, v.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la acción
     * recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        dfsInOrder(accion, raiz);
    }

    /**
     * Método auxiliar.
     * 
     * @param accion
     * @param v      Vertice al que se le aplica acción.
     */
    private void dfsInOrder(AccionVerticeArbolBinario<T> accion, Vertice v) {
        if (v == null)
            return;
        if (v.hayIzquierdo())
            dfsInOrder(accion, v.izquierdo);
        accion.actua(v);
        if (v.hayDerecho())
            dfsInOrder(accion, v.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        dfsPostOrder(accion, raiz);
    }

    /**
     * Método auxiliar.
     * 
     * @param accion
     * @param v      Vertice al que se le aplica acción.
     */
    private void dfsPostOrder(AccionVerticeArbolBinario<T> accion, Vertice v) {
        if (v == null)
            return;
        if (v.hayIzquierdo())
            dfsPostOrder(accion, v.izquierdo);
        if (v.hayDerecho())
            dfsPostOrder(accion, v.derecho);
        accion.actua(v);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * 
     * @return un iterador para iterar el árbol.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }
}
