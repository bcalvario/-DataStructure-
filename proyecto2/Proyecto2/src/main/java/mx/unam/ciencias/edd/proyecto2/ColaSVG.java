package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.MeteSaca;

public class ColaSVG implements SVG{

    public MeteSaca<Integer> coleccion;
	public int elementos;

	ColaSVG(MeteSaca<Integer> ms, int elementos) {
		coleccion = ms;
		this.elementos = elementos;
	}

	/**
	 * 
	 */
	public String imprime() {
		String cadena = "<?xml version='1.0' encoding='UTF-8' ?>\n";
        cadena += "<svg width='"+ (elementos + 1) * 100 +"' height='140'>\n";
		cadena += "<g>\n";
        cadena += "<rect x='0' y='0' width='"+ (elementos + 1) * 100 +"' height='140' fill='Lavender' />\n";
		for (int i = 0, x = 50; i < elementos; i++, x += 100) {
			cadena += "<rect x='" + x + "' y='20' width='95' height='100' fill='white' stroke='black' stroke-width='4xp' />\n";
			cadena += "<text fill='black' font-size='65' x='" + (x + 20) + "' y='90' >" + coleccion.saca() + "</text>\n";
		}
		cadena += "</g>\n";
		cadena += "</svg>\n";

		return cadena;

    }
}