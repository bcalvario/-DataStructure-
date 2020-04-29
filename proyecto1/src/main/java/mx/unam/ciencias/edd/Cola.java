package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * 
     * @return una representación en cadena de la cola.
     */
    @Override
    public String toString() {
        Nodo copiaCabeza = cabeza;
        String cadena = "";
        while (copiaCabeza != null) {
            cadena += copiaCabeza.elemento + ",";
            copiaCabeza = copiaCabeza.siguiente;
        }
        return cadena;
    }

    /**
     * Agrega un elemento al final de la cola.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void mete(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        Nodo nuevo = new Nodo(elemento);
        if (cabeza == null)
            cabeza = rabo = nuevo;
        else {
            rabo.siguiente = nuevo;
            rabo = nuevo;
        }
    }
}