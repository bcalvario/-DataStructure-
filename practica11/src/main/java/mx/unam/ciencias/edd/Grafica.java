package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override
        public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override
        public T next() {
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T>, ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* El diccionario de vecinos del vértice. */
        public Diccionario<T, Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            this.color = Color.NINGUNO;
            vecinos = new Diccionario<T, Vecino>();
        }

        /* Regresa el elemento del vértice. */
        @Override
        public T get() {
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override
        public int getGrado() {
            return vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override
        public Color getColor() {
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override
        public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }

        /* Define el índice del vértice. */
        @Override
        public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override
        public int getIndice() {
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override
        public int compareTo(Vertice vertice) {
            if (distancia > vertice.distancia)
                return 1;
            else if (distancia < vertice.distancia)
                return -1;
            else
                return 0;
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /*
         * Construye un nuevo vecino con el vértice recibido como vecino y el peso
         * especificado.
         */
        public Vecino(Vertice vecino, double peso) {
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override
        public T get() {
            return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override
        public int getGrado() {
            return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override
        public Color getColor() {
            return vecino.getColor();
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override
        public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecino.vecinos;
        }
    }

    /*
     * Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino.
     */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Diccionario<T, Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Diccionario<T, Vertice>();
        aristas = 0;
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es igual
     * al número de vértices.
     * 
     * @return el número de elementos en la gráfica.
     */
    @Override
    public int getElementos() {
        return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * 
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a la
     *                                  gráfica.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento == null || contiene(elemento))
            throw new IllegalArgumentException();
        vertices.agrega(elemento, new Vertice(elemento));
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la gráfica.
     * El peso de la arista que conecte a los elementos será 1.
     * 
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException   si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *                                  igual a b.
     */
    public void conecta(T a, T b) {
        Vertice vA = (Vertice) vertice(a);
        Vertice vB = (Vertice) vertice(b);
        if (!vertices.contiene(b) && !vertices.contiene(a))
            throw new NoSuchElementException();
        if (vA == vB || sonVecinos(a, b))
            throw new IllegalArgumentException();
        vA.vecinos.agrega(b, new Vecino(vB, 1));
        vB.vecinos.agrega(a, new Vecino(vA, 1));
        aristas++;
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la gráfica.
     * 
     * @param a    el primer elemento a conectar.
     * @param b    el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException   si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es igual
     *                                  a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        Vertice vA = (Vertice) vertice(a);
        Vertice vB = (Vertice) vertice(b);
        if (!vertices.contiene(b) && !vertices.contiene(a))
            throw new NoSuchElementException();
        if (vA == vB || sonVecinos(a, b) || peso < 0)
            throw new IllegalArgumentException();
        vA.vecinos.agrega(b, new Vecino(vB, peso));
        vB.vecinos.agrega(a, new Vecino(vA, peso));
        aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * 
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException   si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        Vertice vA = (Vertice) vertice(a);
        Vertice vB = (Vertice) vertice(b);
        if (!vertices.contiene(b) && !vertices.contiene(a))
            throw new NoSuchElementException();
        if (vA == vB || !sonVecinos(a, b))
            throw new IllegalArgumentException();
        vA.vecinos.elimina(b);
        vB.vecinos.elimina(a);
        aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * 
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        for (Vertice v : vertices)
            if (v.elemento.equals(elemento))
                return true;
        return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido en
     * la gráfica.
     * 
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *                                gráfica.
     */
    @Override
    public void elimina(T elemento) {
        if (!contiene(elemento))
            throw new NoSuchElementException();
        Vertice v = (Vertice) vertice(elemento);
        this.vertices.elimina(elemento);
        for (Vecino v1 : v.vecinos)
            for (Vecino v2 : v1.vecino.vecinos)
                if (v2.vecino.elemento == elemento) {
                    v1.vecino.vecinos.elimina(elemento);
                    aristas--;
                }
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos deben
     * estar en la gráfica.
     * 
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro
     *         caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException();
        Vertice vA = (Vertice) vertice(a);
        Vertice vB = (Vertice) vertice(b);
        Vecino va = null;
        Vecino vb = null;
        for (Vecino v : vA.vecinos)
            if (v.vecino.elemento.equals(b))
                vb = v;
        for (Vecino v : vB.vecinos)
            if (v.vecino.elemento.equals(a))
                va = v;
        Vecino Av = va;
        Vecino Bv = vb;
        return Av != null && Bv != null;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a los
     * elementos recibidos.
     * 
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a los
     *         elementos recibidos.
     * @throws NoSuchElementException   si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException();
        if (!sonVecinos(a, b))
            throw new IllegalArgumentException();
        Vertice vA = (Vertice) vertice(a);
        Vecino v = null;
        for (Vecino ve : vA.vecinos)
            if (ve.vecino.elemento.equals(b))
                v = ve;
        return v.peso;
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a los
     * elementos recibidos.
     * 
     * @param a    el primer elemento.
     * @param b    el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *             contienen a los elementos recibidos.
     * @throws NoSuchElementException   si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso es
     *                                  menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException();
        if (peso < 0 || !sonVecinos(a, b))
            throw new IllegalArgumentException();
        Vertice vA = (Vertice) vertice(a);
        Vertice vB = (Vertice) vertice(b);
        Vecino A = null;
        Vecino B = null;
        for (Vecino v : vA.vecinos)
            if (v.vecino.elemento.equals(b))
                B = v;
        for (Vecino v : vB.vecinos)
            if (v.vecino.elemento.equals(a))
                A = v;
        A.peso = peso;
        B.peso = peso;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * 
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        for (Vertice v : vertices)
            if (v.elemento.equals(elemento))
                return v;
        throw new NoSuchElementException();
    }

    /**
     * Define el color del vértice recibido.
     * 
     * @param vertice el vértice al que queremos definirle el color.
     * @param color   el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if (vertice == null || (vertice.getClass() != Vertice.class && vertice.getClass() != Vecino.class))
            throw new IllegalArgumentException();
        if (vertice.getClass() == Vertice.class) {
            Vertice v = (Vertice) vertice;
            v.color = color;
        }
        if (vertice.getClass() == Vecino.class) {
            Vecino v = (Vecino) vertice;
            v.vecino.color = color;
        }
    }

    /**
     * Nos dice si la gráfica es conexa.
     * 
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en otro
     *         caso.
     */
    public boolean esConexa() {
        Cola<Vertice> cola = new Cola<>();
        for (Vertice V : vertices)
            V.color = Color.ROJO;
        Iterator<Vertice> i = vertices.iterator();
        Vertice v = i.next();
        v.color = Color.NEGRO;
        cola.mete(v);
        while (!cola.esVacia()) {
            v = cola.saca();
            for (Vecino v2 : v.vecinos)
                if (v2.vecino.color == Color.ROJO) {
                    v2.vecino.color = Color.NEGRO;
                    cola.mete(v2.vecino);
                }
        }
        Iterator<Vertice> iterador = vertices.iterator();
        while (iterador.hasNext())
            if (iterador.next().color != Color.NEGRO)
                return false;
        return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en el
     * orden en que fueron agregados.
     * 
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for (Vertice v : vertices)
            accion.actua(v);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el orden
     * determinado por BFS, comenzando por el vértice correspondiente al elemento
     * recibido. Al terminar el método, todos los vértices tendrán color
     * {@link Color#NINGUNO}.
     * 
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *                 recorrido.
     * @param accion   la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        if (!contiene(elemento))
            throw new NoSuchElementException();
        Vertice w = (Vertice) vertice(elemento);
        for (Vertice v : vertices)
            v.color = Color.ROJO;
        Cola<Vertice> q = new Cola<>();
        w.color = Color.NEGRO;
        q.mete(w);
        while (!q.esVacia()) {
            Vertice u;
            u = q.saca();
            accion.actua(u);
            for (Vecino v : u.vecinos)
                if (v.vecino.color == Color.ROJO) {
                    v.vecino.color = Color.NEGRO;
                    q.mete(v.vecino);
                }
        }
        for (Vertice v : vertices)
            v.color = Color.NINGUNO;
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el orden
     * determinado por DFS, comenzando por el vértice correspondiente al elemento
     * recibido. Al terminar el método, todos los vértices tendrán color
     * {@link Color#NINGUNO}.
     * 
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *                 recorrido.
     * @param accion   la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        if (!contiene(elemento))
            throw new NoSuchElementException();
        Vertice w = (Vertice) vertice(elemento);
        for (Vertice v : vertices)
            v.color = Color.ROJO;
        Pila<Vertice> s = new Pila<>();
        w.color = Color.NEGRO;
        s.mete(w);
        while (!s.esVacia()) {
            Vertice u;
            u = s.saca();
            accion.actua(u);
            for (Vecino v : u.vecinos)
                if (v.vecino.color == Color.ROJO) {
                    v.vecino.color = Color.NEGRO;
                    s.mete(v.vecino);
                }
        }
        for (Vertice v : vertices)
            v.color = Color.NINGUNO;
    }

    /**
     * Nos dice si la gráfica es vacía.
     * 
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en otro
     *         caso.
     */
    @Override
    public boolean esVacia() {
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override
    public void limpia() {
        vertices.limpia();
        aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * 
     * @return una representación en cadena de la gráfica.
     */
    @Override
    public String toString() {
        String V = "{";
        String A = "{";
        Lista<T> aux = new Lista<>();
        for (Vertice v : vertices) {
            V += v.elemento + ", ";
            aux.agrega(v.elemento);
            for (Vecino v2 : v.vecinos)
                if (!aux.contiene(v2.vecino.elemento))
                    A += "(" + v.elemento + ", " + v2.vecino.elemento + "), ";
        }
        V += "}";
        A += "}";
        return V + ", " + A;
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * 
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Grafica<T> grafica = (Grafica<T>) objeto;
        if (getElementos() != grafica.getElementos() || aristas != grafica.aristas)
            return false;
        for (Vertice v : vertices)
            for (Vecino u : v.vecinos)
                if (!grafica.sonVecinos(u.vecino.elemento, v.elemento))
                    return false;
        for (Vertice v : vertices)
            if (!grafica.contiene(v.elemento))
                return false;
        return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el orden
     * en que fueron agregados sus elementos.
     * 
     * @return un iterador para iterar la gráfica.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * 
     * @param origen  el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una trayectoria
     *         de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en la
     *                                gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        if (!contiene(origen) || !contiene(destino))
            throw new NoSuchElementException();
        Lista<VerticeGrafica<T>> trayectoria = new Lista<VerticeGrafica<T>>();
        Vertice s = (Vertice) vertice(origen);
        Vertice t = (Vertice) vertice(destino);
        if (s == t) {
            trayectoria.agrega(s);
            return trayectoria;
        }
        for (Vertice v : vertices)
            v.distancia = Double.MAX_VALUE;
        s.distancia = 0;
        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(s);
        Vertice v;
        while (!cola.esVacia()) {
            v = cola.saca();
            for (Vecino vecino : v.vecinos)
                if (vecino.vecino.distancia == Double.MAX_VALUE) {
                    vecino.vecino.distancia = v.distancia + 1;
                    cola.mete(vecino.vecino);
                }
        }
        if (t.distancia == Double.MAX_VALUE)
            return trayectoria;
        trayectoria.agrega(t);
        Vertice u = t;
        while (u != s) {
            for (Vecino vec : u.vecinos)
                if (vec.vecino.distancia == u.distancia - 1) {
                    trayectoria.agregaInicio(vec.vecino);
                    u = vec.vecino;
                }
        }
        return trayectoria;
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento de
     * destino.
     * 
     * @param origen  el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en la
     *                                gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        if (!contiene(origen) || !contiene(destino))
            throw new NoSuchElementException();
        Lista<VerticeGrafica<T>> trayectoria = new Lista<VerticeGrafica<T>>();
        Vertice t = (Vertice) vertice(origen);
        Vertice s = (Vertice) vertice(destino);
        if (t == s) {
            trayectoria.agrega(s);
            return trayectoria;
        }
        for (Vertice v : vertices) {
            v.distancia = Double.MAX_VALUE;
        }
        t.distancia = 0;
        int n = vertices.getElementos();
        MonticuloDijkstra<Vertice> monticulo = null;
        if (aristas > (n * (n - 1)) / 2 - n)
            monticulo = new MonticuloArreglo<Vertice>(vertices, getElementos());
        else
            monticulo = new MonticuloMinimo<Vertice>(vertices, vertices.getElementos());
        Vertice u = null;
        while (monticulo.getElementos() > 0) {
            u = monticulo.elimina();
            for (Vecino vecino : u.vecinos)
                if (vecino.vecino.distancia > u.distancia + vecino.peso) {
                    vecino.vecino.distancia = u.distancia + vecino.peso;
                    monticulo.reordena(vecino.vecino);
                }
        }
        if (s.distancia == Double.MAX_VALUE)
            return trayectoria;
        trayectoria.agrega(s);
        Vertice v = s;
        while (v != t) {
            for (Vecino vecino : v.vecinos)
                if (vecino.vecino.distancia == v.distancia - vecino.peso) {
                    trayectoria.agregaInicio(vecino.vecino);
                    v = vecino.vecino;
                }
        }
        return trayectoria;
    }
}
