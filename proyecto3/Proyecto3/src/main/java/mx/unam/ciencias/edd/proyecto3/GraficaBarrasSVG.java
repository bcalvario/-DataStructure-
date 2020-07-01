package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;
import java.util.Iterator;

public class GraficaBarrasSVG {

    Lista<Palabra> lista;
    int maxRepeticioines;

    GraficaBarrasSVG(Diccionario<Palabra, Integer> diccionario) {
        Lista<Palabra> l = new Lista<>();
        Iterator<Palabra> i = diccionario.iteradorLlaves();
        while (i.hasNext())
            l.agrega(i.next());
        lista = l;

        maxRepeticioines = 0;
        for (Palabra p : lista)
            if (p.numRepeticiones > maxRepeticioines)
                maxRepeticioines = p.numRepeticiones;
    }

    /**
     * Método para la cadena con el código para la grafica de barras.
     */
    public String imprime() {
        String cadena = "<?xml version='1.0' encoding='UTF-8' ?>\n";
        cadena += "<svg width=\"" + 2500 + "\" height=\"" + 500 + "\" xmlns=\"http://www.w3.org/2000/svg\">\n";
        cadena += "<rect x=\"" + 0 + "\" y=\"" + 0 + "\" width=\"2500\" height=\"" + 500 + "\" style=\"fill:Lavender\"/>\n";
        int x = 7;
        for (Palabra p : lista) {
            cadena += "<rect x=\"" + x + "\" y=\"" + ((450 - ((int) (p.numRepeticiones * 450) / maxRepeticioines)) + 5) + "\" width=\"130\" height=\"" + ((int) (p.numRepeticiones * 450) / maxRepeticioines) + "\" style=\"fill:pink\"/>\n";
            cadena += "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + (x + 65) + "' y='" + 480 + "' text-anchor='middle'>" + p + "</text>\n";
            cadena += "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + (x + 65) + "' y='" + 440 + "' text-anchor='middle'>" + p.numRepeticiones + "</text>\n";
            x += 160;
        }
        cadena += "</svg>";
        return cadena;
    }
}
