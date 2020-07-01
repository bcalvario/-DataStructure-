package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;
import java.util.Iterator;

public class GraficaPastelSVG {

    Lista<Palabra> lista;
    int maxRepeticiones;
    int total;

    GraficaPastelSVG(Diccionario<Palabra, Integer> diccionario) {
        Lista<Palabra> l = new Lista<>();
        Iterator<Palabra> i = diccionario.iteradorLlaves();
        while (i.hasNext())
            l.agrega(i.next());
        lista = l;

        maxRepeticiones = 0;
        for (Palabra p : lista)
            if (p.numRepeticiones > maxRepeticiones)
                maxRepeticiones = p.numRepeticiones;
        
        total = 0;
        for (Palabra p : lista)
            total += p.numRepeticiones;
        
    }

    /**
     * Método para la cadena con el código para la grafica de pastels.
     */

    public String imprime() {
        String cadena = "<?xml version='1.0' encoding='UTF-8' ?>\n";
        cadena += "<svg width=\"2500\" height=\"500\" xmlns=\"http://www.w3.org/2000/svg\">\n";
        cadena += "<rect x=\"" + 0 + "\" y=\"" + 0 + "\" width=\"2500\" height=\"" + 500 + "\" style=\"fill:Lavender\"/>\n";
        double ini = 0;
        double aux = 0;
        int color = 1;
        int x2 = 50;
        for (Palabra p : lista) {
            if (color == 8)
                color = 1;
            aux = (p.numRepeticiones * 330) / total + ini;
            double fin = 0.0;

            switch (color) {
                case 1:  
                    cadena += "\t<path d=\"" + "M" + 1250 + ", " + 225 + " L" + (1250 + 200 * Math.cos((Math.PI / 180) * ini)) + "," + ((225 + 200 * Math.sin((Math.PI / 180) * ini))) + " A" + 200 + "," + 200 + " 0 0, 1 " + " "
                    + (1250 + 200 * Math.cos((Math.PI / 180) * aux)) + "," + (225 + 200 * Math.sin((Math.PI / 180) * aux)) + " z" + "\" fill = \"pink\" />\n";
                    cadena += "<rect x=\"" + 850 + "\" y=\"" + x2 + "\" width=\"10\" height=\"" + 10 + "\" style=\"fill:pink\"/>\n";
                    break;
                case 2:  
                    cadena += "\t<path d=\"" + "M" + 1250 + ", " + 225 + " L" + (1250 + 200 * Math.cos((Math.PI / 180) * ini)) + "," + ((225 + 200 * Math.sin((Math.PI / 180) * ini))) + " A" + 200 + "," + 200 + " 0 0, 1 " + " "
                    + (1250 + 200 * Math.cos((Math.PI / 180) * aux)) + "," + (225 + 200 * Math.sin((Math.PI / 180) * aux)) + " z" + "\" fill = \"tan\" />\n";
                    cadena += "<rect x=\"" + 850 + "\" y=\"" + x2 + "\" width=\"10\" height=\"" + 10 + "\" style=\"fill:tan\"/>\n";
                    break;
                case 3:
                    cadena += "\t<path d=\"" + "M" + 1250 + ", " + 225 + " L" + (1250 + 200 * Math.cos((Math.PI / 180) * ini)) + "," + ((225 + 200 * Math.sin((Math.PI / 180) * ini))) + " A" + 200 + "," + 200 + " 0 0, 1 " + " "
                    + (1250 + 200 * Math.cos((Math.PI / 180) * aux)) + "," + (225 + 200 * Math.sin((Math.PI / 180) * aux)) + " z" + "\" fill = \"lightsalmon\" />\n";
                    cadena += "<rect x=\"" + 850 + "\" y=\"" + x2 + "\" width=\"10\" height=\"" + 10 + "\" style=\"fill:lightsalmon\"/>\n";
                    break;
                case 4:  
                    cadena += "\t<path d=\"" + "M" + 1250 + ", " + 225 + " L" + (1250 + 200 * Math.cos((Math.PI / 180) * ini)) + "," + ((225 + 200 * Math.sin((Math.PI / 180) * ini))) + " A" + 200 + "," + 200 + " 0 0, 1 " + " "
                    + (1250 + 200 * Math.cos((Math.PI / 180) * aux)) + "," + (225 + 200 * Math.sin((Math.PI / 180) * aux)) + " z" + "\" fill = \"lightblue\" />\n";
                    cadena += "<rect x=\"" + 850 + "\" y=\"" + x2 + "\" width=\"10\" height=\"" + 10 + "\" style=\"fill:lightblue\"/>\n";
                    break;
                case 5:  
                    cadena += "\t<path d=\"" + "M" + 1250 + ", " + 225 + " L" + (1250 + 200 * Math.cos((Math.PI / 180) * ini)) + "," + ((225 + 200 * Math.sin((Math.PI / 180) * ini))) + " A" + 200 + "," + 200 + " 0 0, 1 " + " "
                    + (1250 + 200 * Math.cos((Math.PI / 180) * aux)) + "," + (225 + 200 * Math.sin((Math.PI / 180) * aux)) + " z" + "\" fill = \"yellow\" />\n";
                    cadena += "<rect x=\"" + 850 + "\" y=\"" + x2 + "\" width=\"10\" height=\"" + 10 + "\" style=\"fill:yellow\"/>\n";
                    break;
                case 6:
                    cadena += "\t<path d=\"" + "M" + 1250 + ", " + 225 + " L" + (1250 + 200 * Math.cos((Math.PI / 180) * ini)) + "," + ((225 + 200 * Math.sin((Math.PI / 180) * ini))) + " A" + 200 + "," + 200 + " 0 0, 1 " + " "
                    + (1250 + 200 * Math.cos((Math.PI / 180) * aux)) + "," + (225 + 200 * Math.sin((Math.PI / 180) * aux)) + " z" + "\" fill = \"lightsalmon\" />\n";
                    cadena += "<rect x=\"" + 850 + "\" y=\"" + x2 + "\" width=\"10\" height=\"" + 10 + "\" style=\"fill:lightsalmon\"/>\n";
                    break;
                case 7:
                    cadena += "\t<path d=\"" + "M" + 1250 + ", " + 225 + " L" + (1250 + 200 * Math.cos((Math.PI / 180) * ini)) + "," + ((225 + 200 * Math.sin((Math.PI / 180) * ini))) + " A" + 200 + "," + 200 + " 0 0, 1 " + " "
                    + (1250 + 200 * Math.cos((Math.PI / 180) * aux)) + "," + (225 + 200 * Math.sin((Math.PI / 180) * aux)) + " z" + "\" fill = \"lightsalmon\" />\n";
                    cadena += "<rect x=\"" + 850 + "\" y=\"" + x2 + "\" width=\"10\" height=\"" + 10 + "\" style=\"fill:lightsalmon\"/>\n";
                    break;
                case 8:
                    cadena += "\t<path d=\"" + "M" + 1250 + ", " + 225 + " L" + (1250 + 200 * Math.cos((Math.PI / 180) * ini)) + "," + ((225 + 200 * Math.sin((Math.PI / 180) * ini))) + " A" + 200 + "," + 200 + " 0 0, 1 " + " "
                    + (1250 + 200 * Math.cos((Math.PI / 180) * fin)) + "," + (225 + 200 * Math.sin((Math.PI / 180) * fin)) + " z" + "\" fill = \"red\" />\n";
                    cadena += "<rect x=\"" + 850 + "\" y=\"" + x2 + "\" width=\"10\" height=\"" + 10 + "\" style=\"fill:red\"/>\n";
                    break;
                default:
                    cadena += "\t<path d=\"" + "M" + 1250 + ", " + 225 + " L" + (1250 + 200 * Math.cos((Math.PI / 180) * ini)) + "," + ((225 + 200 * Math.sin((Math.PI / 180) * ini))) + " A" + 200 + "," + 200 + " 0 0, 1 " + " "
                    + (1250 + 200 * Math.cos((Math.PI / 180) * aux)) + "," + (225 + 200 * Math.sin((Math.PI / 180) * aux)) + " z" + "\" fill = \"thistle\" />\n";
                    cadena += "<rect x=\"" + 850 + "\" y=\"" + x2 + "\" width=\"10\" height=\"" + 10 + "\" style=\"fill:thistle\"/>\n";
                    break;
            }
            cadena += "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + 950 + "' y='" + (x2 + 10) + "' text-anchor='middle'>" + p + " [" + p.numRepeticiones +"]</text>\n";
            color++;
            ini = aux;
            x2 += 25;
        }

        cadena += "<rect x=\"" + 850 + "\" y=\"" + 439 + "\" width=\"10\" height=\"" + 10 + "\" style=\"fill:MediumVioletRed\"/>\n";
        cadena += "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + 920 + "' y='" + 450 + "' text-anchor='middle'>Otras [" + (LectorArchivo.totalPalabras - total) +"]</text>\n";

        double fin = 0.0;
         if (color == 2)
            cadena += "\t<path d=\"" + "M" + 1250 + ", " + 225 + " L" + (1250 + 200 * Math.cos((Math.PI / 180) * ini)) + "," + ((225 + 200 * Math.sin((Math.PI / 180) * ini))) + " A" + 200 + "," + 200 + " 0 0, 1 " + " "
            + (1250 + 200 * Math.cos((Math.PI / 180) * fin)) + "," + (225 + 200 * Math.sin((Math.PI / 180) * fin)) + " z" + "\" fill = \"MediumVioletRed\" />\n";
        return cadena + "</svg>";

    }
}
