package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {
    }

    /**
     * Función de dispersión XOR.
     * 
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        int r = 0;
        int i = 0;
        int l = llave.length;
        while (l >= 4) {
            r ^= combina(llave[i + 3], llave[i + 2], llave[i + 1], llave[i]);
            i += 4;
            l -= 4;
        }
        if (l == 0)
            return r;
        else if (l == 2) {
            r ^= combina((byte) 0, (byte) 0, llave[i + 1], llave[i]);
            return r;
        } else if (l == 1) {
                r ^= combina((byte) 0 , (byte) 0, (byte) 0, llave[i]);
                return r;
        }
        r ^= combina((byte) 0, llave[i + 2], llave[i + 1], llave[i]);
        return r;
    }

    /**
     * Función de dispersión de Bob Jenkins.
     *
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        int a = 0x9E3779B9;
        int b = 0x9E3779B9;
        int c = 0xFFFFFFFF;
        int i = 0;
        int l = llave.length;
        int[] arr = new int[3];

        while (l >= 12) {
            a += combina(llave[i], llave[i + 1], llave[i + 2], llave[i + 3]);
            b += combina(llave[i + 4], llave[i + 5], llave[i + 6], llave[i + 7]);
            c += combina(llave[i + 8], llave[i + 9], llave[i + 10], llave[i + 11]);

            arr = mezcla(a, b, c);
            a = arr[0];
            b = arr[1];
            c = arr[2];

            i += 12;
            l -= 12;
        }

        c += llave.length;

        switch (l) {
            case 11:
                c += ((llave[i + 10] & 0xFF) << 24);
            case 10:
                c += ((llave[i + 9] & 0xFF) << 16);
            case 9:
                c += ((llave[i + 8] & 0xFF) << 8);
            case 8:
                b += ((llave[i + 7] & 0xFF) << 24);
            case 7:
                b += ((llave[i + 6] & 0xFF) << 16);
            case 6:
                b += ((llave[i + 5] & 0xFF) << 8);
            case 5:
                b += (llave[i + 4] & 0xFF);
            case 4:
                a += ((llave[i + 3] & 0xFF) << 24);
            case 3:
                a += ((llave[i + 2] & 0xFF) << 16);
            case 2:
                a += ((llave[i + 1] & 0xFF) << 8);
            case 1:
                a += (llave[i] & 0xFF);
        }

        arr = mezcla(a, b, c);
        return arr[2];
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * 
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        int h = 5381;
        for (int i = 0; i < llave.length; i++)
            h += (h << 5) + (llave[i] & 0xFF);
        return h;
    }

    private static int combina(byte a, byte b, byte c, byte d) {
        return ((a & 0xFF)) | ((b & 0xFF) << 8) | ((c & 0xFF) << 16) | ((d & 0xFF) << 24);
    }

    private static int[] mezcla(int a, int b, int c) {
        int[] m = new int[3];

        a -= b;
        a -= c;
        a ^= c >>> 13;

        b -= c;
        b -= a;
        b ^= a << 8;

        c -= a;
        c -= b;
        c ^= b >>> 13;

        a -= b;
        a -= c;
        a ^= c >>> 12;

        b -= c;
        b -= a;
        b ^= a << 16;

        c -= a;
        c -= b;
        c ^= b >>> 5;

        a -= b;
        a -= c;
        a ^= c >>> 3;

        b -= c;
        b -= a;
        b ^= a << 10;

        c -= a;
        c -= b;
        c ^= b >>> 15;

        m[0] = a;
        m[1] = b;
        m[2] = c;

        return m;
    }

}
