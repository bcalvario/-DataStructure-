package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedWriter;
import mx.unam.ciencias.edd.*;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

public class Proyecto3 {

    public static void main(String[] args) {
        run(args);
    }

    public static void run(String[] args) {
        try {
            corre(args);
        } catch (Exception e) {
            System.err.println("Ocurrió un error");
        }
    }

    /**
     * Método que corre el programa.
     */
    public static void corre(String[] args) throws IOException {
        Lector lector = new Lector(args);
        Lista<LectorArchivo> lectorArchivo = new Lista<>();
        
        for (String archivo : lector.getArchivos())
            lectorArchivo.agrega(new LectorArchivo(archivo));
        
        Lista<Acomodador> acomoda = new Lista<>();
        for (LectorArchivo LA : lectorArchivo)
            acomoda.agrega(new Acomodador(lector.getCarpeta(), LA));

        AcomodaHTML(acomoda, lector.getCarpeta());
        AcomodaSVG(acomoda);

    }

    /* Da el codigo SVG*/
    private static void AcomodaSVG(Lista<Acomodador> organiza) throws IOException{
        Lista<Acomodador> lista = organiza;
        File f = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        for (Acomodador o : lista ) {
            f = o.html;
            fw = new FileWriter(f);
            bw = new BufferedWriter(fw);
            bw.write("<!DOCTYPE html>\n<html>\n<body style=background-color:Lavender>");
            bw.write("\n<h1>" + o.getArchivo() +"</h1>");
            bw.write("\n<p>" + o.getContenido().toString() + "</p>");
            bw.write("\n<br><br><img width=\"1300px\" src=\"" + o.graficaBarras.getAbsolutePath() + "\"></img><br><br>");
            bw.write("\n<br><br><img width=\"1300px\" src=\"" + o.graficaPastel.getAbsolutePath() + "\"></img><br><br>");
            bw.write("\n<br><br><img width=\"1300px\" src=\"" + o.arbolRojinegro.getAbsolutePath() + "\"></img><br><br>");
            bw.write("\n<br><br><img width=\"1300px\" src=\"" + o.arbolAVL.getAbsolutePath() + "\"></img><br><br>");
            bw.write("\n<p>Palabras del archivo[" + o.getContenido().getPalabrasConRepeticion().getElementos() + "]" + o.getContenido().getPalabrasConRepeticion().toString() + "</p>");
            //bw.write("\n<p>" + o.getContenido().getPalabrasConRepeticion().toString() + "</p>");
            bw.write("\n<p>Las palabras del archivo[" + o.getContenido().getConjuntoSiete().getElementos() + "]" + o.getContenido().getConjuntoSiete().toString() + "</p>");
            //bw.write("\n<p>" + o.getContenido().getConjuntoSiete().toString() + "</p>");
            bw.write("\n<p>PROYECTO3</p>");
            bw.write("\n</body>\n</html>");
            bw.close();
            fw.close();
        }
    }

    /* Da el codigo HTML*/
    public static void AcomodaHTML(Lista<Acomodador> l, String folder) throws IOException{
        Lista<Acomodador> lista = l;

        File file = new File(folder, "index.html");
        file.createNewFile();

            File g = new File(folder, "Grafica-Archivos.svg");
            g.createNewFile();
            GraficaSVG(lista);
            FileWriter filew = new FileWriter(g);
            BufferedWriter bwriter = new BufferedWriter(filew);
            bwriter.write("<?xml version='1.0' encoding='UTF-8' ?>\n<svg width=\"2500\" height=\"500\" xmlns=\"http://www.w3.org/2000/svg\">\n</svg>");
            bwriter.close();
            filew.close();

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("<!DOCTYPE html>\n<html>\n<body style=background-color:Lavender>");
            bw.write("\n<h1>REPORTE DE ARCHIVOS</h1>");
            int c = 1;
            for (Acomodador o : lista) {
                bw.write("\n<p>" + c + ": " + "<a href=\"" + o.html.getAbsolutePath() + "\">" + o.getArchivo() + "</a></p>");
                c++;
            }
            bw.write("\n<br><br><img width=\"1300px\" src=\"" + g.getAbsolutePath() + "\"></img><br><br>");
            bw.write("\n</body>\n</html>");
            bw.close();
            fw.close();
    }

    public static void GraficaSVG(Lista<Acomodador> l) {
        Lista<Conjunto<Palabra>> lista;
        lista = new Lista<Conjunto<Palabra>>();
        for (Acomodador o : l)
            lista.agrega(o.getContenido().getConjuntoSiete());
    }
}
