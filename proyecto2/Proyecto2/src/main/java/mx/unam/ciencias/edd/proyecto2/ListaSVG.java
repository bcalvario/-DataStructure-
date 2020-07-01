package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;

public class ListaSVG implements SVG {

    public Coleccion<Integer> coleccion;

    ListaSVG(Coleccion<Integer> coleccion) {
        this.coleccion = coleccion;
    }

    /**
     * 
     */
    public String imprime() {
        int x = 50;
        int x2 = 110;
        String cadena = "<?xml version='1.0' encoding='UTF-8' ?>\n";
        cadena += "<svg width='" + coleccion.getElementos() * 300 + "' height='200'>\n";
        cadena += "<g>\n";
        cadena += "<rect x='0' y='0' width='" + coleccion.getElementos() * 300 + "' height='200' fill='Lavender' />\n";
        for (int i : coleccion) {
            cadena += "<rect x='"+ x +"' y='50' width='200' height='100' stroke='black' stroke-width='4px' fill='white' />\n";
            cadena += "<text fill='black' font-size='70' x='" + x2 + "' y='130'>" + i + "</text>";
            if (i < Lector.numDeDatos) {
                cadena += "<rect x='"+ (x + 210) +"' y='100' width='80' height='5' fill='black' />\n";
                cadena += "<polygon stroke='black' stroke-width='10' points='" + (x2 + 230) + ",102.5 " + (x2 + 227) + ",100.5 " + (x2 + 227) + ",104.5'/>\n";
                cadena += "<polygon stroke='black' stroke-width='10' points='" + (x2 + 150) + ",102.5 " + (x2 + 153) + ",100.5 " + (x2 + 153) + ",104.5'/>\n";
            }
            x += 300;
            x2 += 300;
        }
        cadena += "</g>\n";
        cadena += "</svg>\n";

        return cadena;
    }

}