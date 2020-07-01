package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.MeteSaca;

public class PilaSVG implements SVG{

	public MeteSaca<Integer> coleccion;
	public int elementos;

	PilaSVG(MeteSaca<Integer> c, int elementos) {
		coleccion = c;
		this.elementos = elementos;
	}

	/**
	 * 
	 */
	public String imprime() {
		String cadena = "<?xml version='1.0' encoding='UTF-8' ?>\n";
        cadena += "<svg width='200' height='"+ (elementos + 1) * 100 +"'>\n";
		cadena += "<g>\n";
        cadena += "<rect x='0' y='0' width='200' height='"+ (elementos + 1) * 100 +"' fill='Lavender' />\n";
		for (int i = 0, x = 50; i < elementos; i++, x += 100) {
			cadena += "<rect x='20' y='" + x + "' width='160' height='100' fill='white' stroke='black' stroke-width='4xp' />\n";
			cadena += "<text fill='black' font-size='70' x='75' y='" + (x + 75) + "' >" + coleccion.saca() + "</text>\n";
		}
		cadena += "</g>\n";
		cadena += "</svg>\n";

		return cadena;

	}
}