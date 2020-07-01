package mx.unam.ciencias.edd.proyecto2;

import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;

import mx.unam.ciencias.edd.*;

public class Lector {

    /** String que contiene el texto */
    public String textocadena = "";

    /** Arreglo que contiene la estructura y los datos */
    public String[] datos;

    /** Cadena que contiene el nombre de la estructura */
    public String nombreEstructura;

    /** Arreglo que contiene los datos a graficar */
    public int datosNumericos[];

    /** Número de datos a graficar */
    public static int numDeDatos;


    Lector(String[] args) {
        Lectura(args);
    }

    /**
     * 
     * @param args
     */
    private void Lectura(String[] args) {
        if (args.length == 1)
            args(args[0]);
        else
            estandar();

        datos = textocadena.split(" ");
        nombreEstructura = datos[0];
        datosNumericos = new int[datos.length - 1];

        for (int i = 0; i < datosNumericos.length; i++)
            datosNumericos[i] = Integer.parseInt(datos[i + 1]);

        numDeDatos = datosNumericos.length;
    }

    private String formato(String cadena) {
        cadena = cadena.trim();
        cadena = cadena.replace("\t", "");
        int k = cadena.indexOf("#");
        if (k == -1)
            return cadena + " ";
        if (k == 0)
            return "";
        else
            return cadena.substring(0, k) + " ";
    }

    /**
     * Agrega el texto por medio de la entrada por argumentos.
     * 
     * @param direccionArchivo dirección del archivo.
     */
    private void args(String direccionArchivo) {
        try {
            FileReader f = new FileReader(direccionArchivo);
            BufferedReader lector = new BufferedReader(f);
            String linea;
            while ((linea = lector.readLine()) != null) {
                textocadena += formato(linea);
            }
            lector.close();
        } catch (Exception e) {
            System.err.println("No se encontro el archivo(s)");
        }
    }

    /**
     * Agrega el texto por medio de la entrada estandar.
     * 
     */
    private void estandar() {
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader lector = new BufferedReader(isr);
            String linea;
            while ((linea = lector.readLine()) != null) {
                String s = new String(linea);
                textocadena += formato(s);
            }
            lector.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("No se encontro una entrada");
        }
    }

    public String getEstructura() {
        return nombreEstructura;
    }
}