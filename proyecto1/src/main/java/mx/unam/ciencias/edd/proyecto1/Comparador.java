package mx.unam.ciencias.edd.proyecto1;

import java.util.Comparator;
import java.text.Normalizer;

public class Comparador implements Comparator<String> {

    public int compare(String a, String b) {

        a = (((a.replaceAll(" ", ""))).replaceAll("[^\\dA-Za-z ]", "")).toUpperCase();
        a = Normalizer.normalize(a, Normalizer.Form.NFD);
        a = a.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        
        b = (((b.replaceAll(" ", ""))).replaceAll("[^\\dA-Za-z ]", "")).toUpperCase();
        b = Normalizer.normalize(b, Normalizer.Form.NFD);
        b = b.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        
        return a.compareTo(b);

    }

}