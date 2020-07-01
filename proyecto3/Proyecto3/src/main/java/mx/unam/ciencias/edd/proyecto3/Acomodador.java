package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Acomodador {

    String archivo;
    File html;
    File arbolRojinegro;
    File arbolAVL;
    File graficaPastel;
    File graficaBarras;
    private LectorArchivo contenido;

    Acomodador(String carpeta, LectorArchivo lectorArchivo) throws IOException {
        contenido = lectorArchivo;

        String cad = contenido.getArchivo();
        File file = new File(cad);
        cad = file.getName();
        int aux = cad.lastIndexOf(".");
        if (aux != -1)
            archivo = cad.substring(0, aux);
        archivo = cad;
        
        html = new File(carpeta, archivo + ".html");
        arbolRojinegro = new File(carpeta, archivo + "-ArbolRojinegro.svg");
        arbolAVL = new File(carpeta, archivo + "-ArbolAVL.svg");
        graficaPastel = new File(carpeta, archivo + "-GraficaPastel.svg");
        graficaBarras = new File(carpeta, archivo + "-GraficaBarras.svg");
        html.createNewFile();
        arbolRojinegro.createNewFile();
        arbolAVL.createNewFile();
        graficaPastel.createNewFile();
        graficaBarras.createNewFile();
        dibujaSVG();
    }

    private void dibujaSVG() throws IOException {
        ArbolRojinegroSVG ab = new ArbolRojinegroSVG(contenido.getMasFrecuentes());
        FileWriter fw = new FileWriter(this.arbolRojinegro);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(ab.imprime());
        bw.close();
        fw.close();

        ArbolAVLSVG ar = new ArbolAVLSVG(contenido.getMasFrecuentes());
        fw = new FileWriter(this.arbolAVL);
        bw = new BufferedWriter(fw);
        bw.write(ar.imprime());
        bw.close();
        fw.close();

        GraficaBarrasSVG g = new GraficaBarrasSVG(contenido.getMasFrecuentes());
        fw = new FileWriter(this.graficaBarras);
        bw = new BufferedWriter(fw);
        bw.write(g.imprime());
        bw.close();
        fw.close();

        GraficaPastelSVG g2 = new GraficaPastelSVG(contenido.getMasFrecuentes());
        fw = new FileWriter(this.graficaPastel);
        bw = new BufferedWriter(fw);
        bw.write(g2.imprime());
        bw.close();
        fw.close();
        
    }

    public LectorArchivo getContenido() {
        return contenido;
    }

    public String getArchivo() {
        return archivo;
    }
}
