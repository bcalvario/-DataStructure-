package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class Estructura {

    /** Colección que contiene los elementos a graficar */
    public Coleccion<Integer> coleccion;

    /** MeteSaca que contiene los elementos a graficar */
    public MeteSaca<Integer> pc;

    /** Entero que contiene el número de elementos */
    public int elementos;

    /** Lector para obtener los datos para graficar */
    public Lector e;

    /** El SVG que se va a graficar */
    public SVG svg;

    public ArbolSVG arbolSVG;

    Estructura(Lector entrada) {
        elementos = Lector.numDeDatos;
        this.e = entrada;
        grafica();
    }

    public void grafica() {
        switch (e.nombreEstructura) {
            case "Lista":
                Lista<Integer> lista = new Lista<>();
                coleccion = lista;
                datosEstructura();
                svg = new ListaSVG(coleccion);
                break;
            case "Pila":
                Pila<Integer> pila = new Pila<>();
                pc = pila;
                datosEstructura();
                svg = new PilaSVG(pc, elementos);
                break;
            case "Cola":
                Cola<Integer> cola = new Cola<>();
                pc = cola;
                datosEstructura();
                svg = new ColaSVG(pc, elementos);
                break;
            case "ArbolRojinegro":
                ArbolRojinegro<Integer> arbolRojinegro = new ArbolRojinegro<>();
                coleccion = arbolRojinegro;
                datosEstructura();
                arbolSVG = new ArbolRojinegroSVG(coleccion);
                break;
            case "ArbolAVL":
                ArbolAVL<Integer> arbolAVL = new ArbolAVL<>();
                coleccion = arbolAVL;
                datosEstructura();
                arbolSVG = new ArbolAVLSVG(coleccion);
                break;
            case "ArbolBinarioOrdenado":
                ArbolBinarioOrdenado<Integer> arbolBinarioOrdenado = new ArbolBinarioOrdenado<>();
                coleccion = arbolBinarioOrdenado;
                datosEstructura();
                arbolSVG = new ArbolBinarioOrdenadoSVG(coleccion);
                break;
            default:
                System.err.println("Ocurrio un error");
        }
    }

    public void datosEstructura() {
        if (coleccion != null)
            for (int i : e.datosNumericos)
                coleccion.agrega(i);
        else if (pc != null)
            for (int i : e.datosNumericos)
                pc.mete(i);
    }

    public String toString() {
        if (coleccion != null)
            return coleccion.toString();
        else
            return "Esta vacía la colección";
    }
}