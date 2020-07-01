package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 * <li>Todos los vértices son NEGROS o ROJOS.</li>
 * <li>La raíz es NEGRA.</li>
 * <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la
 * raíz).</li>
 * <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 * <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 * mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>> extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * 
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            if (color == Color.ROJO)
                return "R{" + elemento.toString() + "}";
            return "N{" + elemento.toString() + "}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es <em>recursiva</em>.
         * 
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente iguales, y los
         *         colores son iguales; <code>false</code> en otro caso.
         */
        @Override
        public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeRojinegro vertice = (VerticeRojinegro) objeto;
            return color == vertice.color && super.equals(vertice);
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros de
     * {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol rojinegro
     * tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeRojinegro}.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override
    protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * 
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de
     *                            {@link VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro) vertice;
        return v.color;
    }

    private boolean esRojo(VerticeRojinegro vertice) {
        return vertice != null && vertice.color == Color.ROJO;
    }

    private boolean esNegro(VerticeRojinegro vertice) {
        return vertice == null || vertice.color == Color.NEGRO;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método
     * {@link ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeRojinegro v = verticeRojinegro(super.ultimoAgregado);
        v.color = Color.ROJO;
        agrega(v);
    }

    /**
     * 
     * @param v
     */
    private void agrega(VerticeRojinegro v) {
        // CASO1
        if (!v.hayPadre()) {
            v.color = Color.NEGRO;
            return;
        }

        // CASO2
        VerticeRojinegro padre = verticeRojinegro(v.padre);
        if (esNegro(padre) && esRojo(v))
            return;

        // CASO3
        VerticeRojinegro abuelo = verticeRojinegro(padre.padre);
        VerticeRojinegro tio = null;
        if (abuelo.izquierdo == padre)
            tio = verticeRojinegro(abuelo.derecho);
        else
            tio = verticeRojinegro(abuelo.izquierdo);

        if (esRojo(tio) && esRojo(padre)) {
            tio.color = padre.color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            agrega(abuelo);
            return;
        }

        // CASO4
        VerticeRojinegro aux = padre;
        if (abuelo.izquierdo == padre && padre.derecho == v) {
            super.giraIzquierda(padre);
            padre = v;
            v = aux;
        } else if (abuelo.derecho == padre && padre.izquierdo == v) {
            super.giraDerecha(padre);
            padre = v;
            v = aux;
        }

        // CASO5
        padre.color = Color.NEGRO;
        abuelo.color = Color.ROJO;
        if (abuelo.izquierdo == padre)
            super.giraDerecha(abuelo);
        else if (abuelo.derecho == padre)
            super.giraIzquierda(abuelo);
    }

    private boolean esDerecho(VerticeRojinegro vertice) {
        return vertice.padre.derecho == vertice;
    }

    private boolean esIzquierdo(VerticeRojinegro vertice) {
        return vertice.padre.izquierdo == vertice;
    }

    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro) vertice;
        return v;
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene el
     * elemento, y recolorea y gira el árbol como sea necesario para rebalancearlo.
     * 
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override
    public void elimina(T elemento) {
        VerticeRojinegro v = verticeRojinegro(busca(elemento));
        if (v == null)
            return;
        if (v.hayIzquierdo() && v.hayDerecho())
            v = verticeRojinegro(intercambiaEliminable(v));
        if (!v.hayIzquierdo() && !v.hayDerecho()) {
            VerticeRojinegro V = verticeRojinegro(nuevoVertice(null));
            v.izquierdo = V;
            V.padre = v;
            V.color = Color.NEGRO;
        }
        VerticeRojinegro hijo = null;
        if (v.hayIzquierdo()) {
            hijo = verticeRojinegro(v.izquierdo);
            super.eliminaVertice(verticeRojinegro(v));
        } else {
            hijo = verticeRojinegro(v.derecho);
            super.eliminaVertice(verticeRojinegro(v));
        }
        eliminaAux(v, hijo);
        if (hijo.elemento == null) {
            eliminaVertice(hijo);
        }
        elementos--;
    }

    private void eliminaAux(VerticeRojinegro v, VerticeRojinegro h) {
        if (esRojo(h))
            h.color = Color.NEGRO;
        else if (esRojo(v))
            return;
        else
            rebalancea(h);
    }

    /**
     * Algoritmo auxiliar recusrsivo de rebalance.
     * 
     * @param vertice
     */
    private void rebalancea(VerticeRojinegro vertice) {
        // CASO1
        if (!vertice.hayPadre()) {
            vertice.color = Color.NEGRO;
            return;
        }

        // CASO2
        VerticeRojinegro padre = verticeRojinegro(vertice.padre);
        VerticeRojinegro hermano = null;
        if (padre.derecho == vertice)
            hermano = verticeRojinegro(padre.izquierdo);
        else
            hermano = verticeRojinegro(padre.derecho);

        if (esRojo(hermano) && esNegro(padre)) {
            padre.color = Color.ROJO;
            hermano.color = Color.NEGRO;
            if (esIzquierdo(vertice)) {
                super.giraIzquierda(padre);
                hermano = verticeRojinegro(padre.derecho);
            } else if (esDerecho(vertice)) {
                super.giraDerecha(padre);
                hermano = verticeRojinegro(padre.izquierdo);
            }
        }

        // CASO3
        VerticeRojinegro hijoIzq = verticeRojinegro(hermano.izquierdo);
        VerticeRojinegro hijoDer = verticeRojinegro(hermano.derecho);
        if (esNegro(padre) && esNegro(hermano) && esNegro(hijoIzq) && esNegro(hijoDer)) {
            hermano.color = Color.ROJO;
            rebalancea(padre);
            return;
        }

        // CASO4
        if (esNegro(hermano) && esNegro(hijoIzq) && esNegro(hijoDer) && esRojo(padre)) {
            hermano.color = Color.ROJO;
            padre.color = Color.NEGRO;
            return;
        }

        // CASO 5
        if ((esIzquierdo(vertice) && esRojo(hijoIzq) && esNegro(hijoDer))
                || (esDerecho(vertice) && esNegro(hijoIzq) && esRojo(hijoDer))) {
            hermano.color = Color.ROJO;
            if (esRojo(hijoIzq) && esNegro(hijoDer)) {
                hijoIzq.color = Color.NEGRO;
                super.giraDerecha(hermano);
                hermano = hijoIzq;
            } else if (esNegro(hijoIzq) && esRojo(hijoDer)) {
                hijoDer.color = Color.NEGRO;
                super.giraIzquierda(hermano);
                hermano = hijoDer;
            }
            hijoDer = verticeRojinegro(hermano.derecho);
            hijoIzq = verticeRojinegro(hermano.izquierdo);
        }

        // CASO6
        if ((esIzquierdo(vertice) && esRojo(hijoDer)) || (esDerecho(vertice) && esRojo(hijoIzq))) {
            hermano.color = padre.color;
            padre.color = Color.NEGRO;
            if (esIzquierdo(vertice)) {
                hijoDer.color = Color.NEGRO;
                super.giraIzquierda(padre);
            } else if (esDerecho(vertice)) {
                hijoIzq.color = Color.NEGRO;
                super.giraDerecha(padre);
            }
        }
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la clase,
     * porque se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException(
                "Los árboles rojinegros no " + "pueden girar a la izquierda " + "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la clase,
     * porque se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException(
                "Los árboles rojinegros no " + "pueden girar a la derecha " + "por el usuario.");
    }
}
