package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;
import java.util.Iterator;

public class ArbolAVLSVG {

	public ArbolAVL<Palabra> a;
	public VerticeArbolBinario<Palabra> raizIzquierdo;
	public VerticeArbolBinario<Palabra> raizDerecho;

	ArbolAVLSVG(Diccionario<Palabra,Integer> diccionario) {
		ArbolAVL<Palabra> arbol = new ArbolAVL<>();
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
	 * Método para la cadena con el código para el ArbolAVL.
	 */
	public String imprime() {
		//Lavender
		String cadena = "<?xml version='1.0' encoding='UTF-8' ?>\n";
		cadena += "<svg width=\"" + (int)(50 * Math.pow(2,a.altura())) * 3 + "\" height=\"" + (a.altura() * 100 + 100) + "\" xmlns=\"http://www.w3.org/2000/svg\">\n";
        cadena += "<rect x=\"0\" y=\"0\" width=\"" + (int)(50 * Math.pow(2,a.altura())) * 3 + "\" height=\"" + (a.altura() * 100 + 100) + "\" style=\"fill:Lavender\"/>\n";
		cadena += crea((int)(50 * Math.pow(2,a.altura())) * 3,a.raiz(), ((int)(50 * Math.pow(2,a.altura())) * 3) / 2, 50);
		cadena += "</svg>";
		return cadena;
	}

	public String crea(int d, VerticeArbolBinario<Palabra> p, int xp, int yp) {
		String cadena = "";
		if (p.hayIzquierdo()) {
			cadena += "\t<line x1='" + xp + "' y1='" + yp + "' x2='" + (xp - (d/4)) + "' y2='" + (yp + 100) + "' stroke='black' stroke-width='3' />\n";
			cadena += "\t<circle cx='" + xp + "' cy='" + yp + "' r='30' stroke='black' stroke-width='3' fill='white' />\n";
			cadena += "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + xp + "' y='"+ (yp + 6) + "' text-anchor='middle'>" + p.get() + "</text>\n";
			cadena += "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + xp + "' y='"+ (yp - 35) + "' text-anchor='middle'>" + p.altura() + " / " + balance(p) + "</text>\n";
			cadena += "\t<circle cx='" + (xp - (d/4)) + "' cy='" + (yp + 100) + "' r='30' stroke='black' stroke-width='3' fill='white' />\n";
			cadena += "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + (xp - (d/4)) + "' y='"+ ((yp + 100) + 6) + "' text-anchor='middle'>" + p.izquierdo().get() + "</text>\n";
			cadena += "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + (xp - (d/4)) + "' y='"+ ((yp + 100) - 35) + "' text-anchor='middle'>" + p.izquierdo().altura() + " / " + balance(p.izquierdo()) + "</text>\n";
			cadena += crea(d/2, p.izquierdo(), (xp - (d/4)), yp + 100);

		}
		if (p.hayDerecho()) {
			cadena += "\t<line x1='" + xp + "' y1='" + yp + "' x2='" + (xp + (d/4)) + "' y2='" + (yp + 100) + "' stroke='black' stroke-width='3' />\n";
			cadena += "\t<circle cx='" + xp + "' cy='" + yp + "' r='30' stroke='black' stroke-width='3' fill='white' />\n";
			cadena += "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + xp + "' y='"+ (yp + 6) + "' text-anchor='middle'>" + p.get() + "</text>\n";
			cadena += "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + xp + "' y='"+ (yp - 35) + "' text-anchor='middle'>" + p.altura() + " / " + balance(p) + "</text>\n";
			cadena += "\t<circle cx='" + (xp + (d/4)) + "' cy='" + (yp + 100) + "' r='30' stroke='black' stroke-width='3' fill='white' />\n";
			cadena += "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + (xp + (d/4)) + "' y='"+ ((yp + 100) + 6) + "' text-anchor='middle'>" + p.derecho().get() + "</text>\n";
			cadena += "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + (xp + (d/4)) + "' y='"+ ((yp + 100) - 35) + "' text-anchor='middle'>" + p.derecho().altura() + " / " + balance(p.derecho()) + "</text>\n";
			cadena += crea(d/2, p.derecho(), (xp + (d/4)), yp + 100);
		}
		return cadena;
	}

	/**
	 * Método para calcula el balance del vertice.
	 * @param vertice Vertice al que se le va a calcular su balance.
	 * @return Balance del vertice.
	 */
	public int balance(VerticeArbolBinario<Palabra> vertice) {
		if (vertice.hayIzquierdo() && vertice.hayDerecho())
            return vertice.izquierdo().altura() - vertice.derecho().altura();
        if (vertice.hayIzquierdo() && !vertice.hayDerecho())
            return vertice.izquierdo().altura() + 1;
        else if (vertice.hayDerecho() && !vertice.hayIzquierdo())
            return -1 - vertice.derecho().altura();
        else
            return 0;
	}

}
