package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;
import java.io.File;

public class Lector {

    private Lista<String> archivos;
    private String carpeta;

    Lector(String[] args) {
        archivos = new Lista<String>();
        lectura(args);
    }

    /**
     * Método que asigna a carpeta la direccion y crea la lista de archivos.
     * Comprueba que los archivos y la carpeta los sean.
     * 
     * @param args args
     */
    private void lectura(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-o"))
                carpeta = args[i + 1];
            if (!args[i].equals("-o") && !args[i].equals(carpeta))
                archivos.agrega(args[i]);
        }

        File file = new File(carpeta);
        if (!file.exists())
            file.mkdirs();
        else if (!file.isDirectory())
            throw new NullPointerException();

        File[] k = file.listFiles();
        if (k.length != 0)
            for (File f : k)
                f.delete();

        for (String archivo : archivos)
            if (!new File(archivo).isFile())
                throw new NumberFormatException();

    }

    /**
     * Getter que devuelve la carpeta.
     * 
     * @return Direccion de la carpeta.
     */
    public String getCarpeta() {
        return this.carpeta;
    }

    /**
     * Getter que devuelve la lista de los archivos.
     * 
     * @return Lista con los archivos.
     */
    public Lista<String> getArchivos() {
        return this.archivos;
    }
}
