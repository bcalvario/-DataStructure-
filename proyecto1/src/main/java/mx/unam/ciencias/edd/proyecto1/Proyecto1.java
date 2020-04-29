package mx.unam.ciencias.edd.proyecto1;

import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;

import mx.unam.ciencias.edd.Lista;

public class Proyecto1 {

    /** Lista que contiene el texto */
    static Lista<String> texto = new Lista<>();

    /** Direccion del archivo */
    static String direccionArchivo;

    /** Bandera -r */
    static boolean _r = false;

    /** Bandera -o */
    static boolean _o = false;

    /**
     * Corre el programa
     * 
     * @param args dirección o direcciones a ordenar.
     */
    public static void main(String[] args) {
        corre(args);
    }

    /**
     * 
     * @param args
     */
    private static void corre(String[] args) {

        lectura(args);
        Lista<String> textoOrdenado = texto.mergeSort(new Comparador());

        if (args.length <= 1)
            banderas(textoOrdenado);
        else
            banderas(textoOrdenado);
    }

    /**
     * Dependiendo a la bandera realiza una acción u otra, si la bandera es -r
     * imprime en orden inverso, si la bandera es -o sobre escribe un archivo.
     * 
     * @param textoOrdenado texto ordena
     */
    private static void banderas(Lista<String> textoOrdenado) {
        // System.out.println("programa principal");
        if (_r && _o) {
            textoOrdenado = textoOrdenado.reversa();
            sobreescribeArchivo(direccionArchivo, textoOrdenado);
        } else if (_r) {
            textoOrdenado = textoOrdenado.reversa();
            imprimeEnConsola(textoOrdenado);
        } else if (_o) {
            sobreescribeArchivo(direccionArchivo, textoOrdenado);
        } else 
            imprimeEnConsola(textoOrdenado);
    }

    /**
     * Imprime un texto ordenado en la consola.
     * 
     * @param textoOrdenado texto ordenado
     */
    private static void imprimeEnConsola(Lista<String> textoOrdenado) {
        // System.out.println("improme en la consola");
        for (String s : textoOrdenado)
            System.out.println(s);
    }

    /**
     * Sobre escribe un archivo.
     * 
     * @param direccionArchivo dirección del archivo que se va a sobre escribir
     * @param textoOrdenado    texto ordenado
     */
    public static void sobreescribeArchivo(String direccionArchivo, Lista<String> textoOrdenado) {
        // System.out.println("Sobre escribe archivos");
        // System.out.println(direccionArchivo);
        try {
            File archivo = new File(direccionArchivo);
            FileWriter escribe = new FileWriter(archivo);
            for (String s : textoOrdenado) {
                    escribe.write(s);
            }
            escribe.close();
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

    /**
     * Activa las banderas correspondientes,
     * 
     * @param args dirección del archivo o archivos
     */
    private static void lectura(String[] args) {
        // System.out.println("Checa banderas");

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-r")) {
                _r = true;
            } else if (args[i].equals("-o")) {
                _o = true;
                direccionArchivo = args[i + 1];
            } else {
                args(args[i]);
            }
        }

        if (args.length == 0 || (args.length == 1 && _r) || (args.length == 1 && _o) || (args.length == 2 && _o && _r))
            estandar();
    }

    /**
     * Agrega el texto por medio de la entrada por argumentos.
     * 
     * @param direccionArchivo dirección del archivo o archivos
     */
    private static void args(String direccionArchivo) {
        // System.out.println("Agrega archivo");
        try {
            FileReader f = new FileReader(direccionArchivo);
            BufferedReader lector = new BufferedReader(f);
            String linea;
            while ((linea = lector.readLine()) != null) {
                texto.agrega(linea);
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
    private static void estandar() {
        // System.out.println("Entrada estandar");
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader lector = new BufferedReader(isr);
            String linea;
            while ((linea = lector.readLine()) != null) {
                String s = new String(linea);
                texto.agrega(s);
            }
            lector.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("No se encontro una entrada");
        }
    }
}