package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;
import java.util.Iterator;

public class ArbolRojinegroSVG {

	public ArbolRojinegro<Palabra> a;
	public VerticeArbolBinario<Palabra> raizIzquierdo;
	public VerticeArbolBinario<Palabra> raizDerecho;

	ArbolRojinegroSVG(Diccionario<Palabra, Integer> diccionario) {
		ArbolRojinegro<Palabra> arbol = new ArbolRojinegro<>();
		Iterator<Palabra> i = diccionario.iteradorLlaves();
		while (i.hasNext())
			arbol.agrega(i.next());
		a = arbol;

		if (a.getElementos() > 1) {
			if (a.raiz().hayIzquierdo())
				raizIzquierdo = a.raiz().izquierdo();
			if (a.raiz().hayDerecho())
				raizDerecho = a.raiz().derecho();
		}
	}

	/**
	 * Método para la cadena con el código para el ArbolRojinegro.
	 */
	public String imprime() {
		String cadena = "<?xml version='1.0' encoding='UTF-8' ?>\n";
		cadena += "<svg width=\"" + (3 * ((int) (50 * Math.pow(2, a.altura())))) + "\" height=\"" + (a.altura() * 130) + "\" xmlns=\"http://www.w3.org/2000/svg\">\n";
		cadena += "<rect x=\"" + 0 + "\" y=\"" + 0 + "\" width=\"" + (3 * ((int) (50 * Math.pow(2, a.altura())))) + "\" height=\"" + (a.altura() * 130) + "\" style=\"fill:Lavender\"/>\n";
		cadena += crea((3 * ((int) (50 * Math.pow(2, a.altura())))), a.raiz(),(3 * ((int) (50 * Math.pow(2, a.altura())))) / 2, 35);
		cadena += "</svg>";
		return cadena;
	}

	public String crea(int d, VerticeArbolBinario<Palabra> p, int x, int y) {
		String cadena = "";
		if (p.hayIzquierdo()) {
			cadena += "\t<line x1='" + x + "' y1='" + y + "' x2='" + (x - (d / 4)) + "' y2='"+ (y + 100) + "' stroke='black' stroke-width='3' />\n";
			String c = "";
			if (a.getColor(p) == Color.ROJO)
				c = "red";
			else
				c = "black";
			cadena += "\t<circle cx='" + x + "' cy='" + y + "' r='30' stroke='" + c + "' stroke-width='3' fill='"+ c + "' />\n";
			cadena += "\t<text fill='white' font-family='sans-serif' font-size='16' x='" + x + "' y='" + (y + 6) + "' text-anchor='middle'>" + p.get() + "</text>\n";
			if (a.getColor(p.izquierdo()) == Color.ROJO)
				c = "red";
			else
				c = "black";
			cadena += "\t<circle cx='" + (x - (d / 4)) + "' cy='" + (y + 100) + "' r='30' stroke='" + c + "' stroke-width='3' fill='" + c + "' />\n";
			cadena += "\t<text fill='white' font-family='sans-serif' font-size='16' x='" + (x - (d / 4)) + "' y='" + ((y + 100) + 6) + "' text-anchor='middle'>" + p.izquierdo().get() + "</text>\n";
			cadena += crea(d / 2, p.izquierdo(), (x - (d / 4)), y + 100);

		}
		if (p.hayDerecho()) {
			cadena += "\t<line x1='" + x + "' y1='" + y + "' x2='" + (x + (d / 4)) + "' y2='" + (y + 100) + "' stroke='black' stroke-width='3' />\n";
			String color = "";
			if (a.getColor(p) == Color.ROJO)
				color = "red";
			else
				color = "black";
			cadena += "\t<circle cx='" + x + "' cy='" + y + "' r='30' stroke='" + color + "' stroke-width='3' fill='" + color + "' />\n";
			cadena += "\t<text fill='white' font-family='sans-serif' font-size='16' x='" + x + "' y='" + (y + 6) + "' text-anchor='middle'>" + p.get() + "</text>\n";
			if (a.getColor(p.derecho()) == Color.ROJO)
				color = "red";
			else
				color = "black";
			cadena += "\t<circle cx='" + (x + (d / 4)) + "' cy='" + (y + 100) + "' r='30' stroke='" + color + "' stroke-width='3' fill='" + color + "' />\n";
			cadena += "\t<text fill='white' font-family='sans-serif' font-size='16' x='" + (x + (d / 4)) + "' y='"+ ((y + 100) + 6) + "' text-anchor='middle'>" + p.derecho().get() + "</text>\n";
			cadena += crea(d / 2, p.derecho(), (x + (d / 4)), y + 100);
		}
		return cadena;
	}
}