import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class convolucion {

    public static void main(String[] args) {
        File file = new File("images/manu.jpg");
        File file2 = new File("images/nuevo_3x3.jpg");
        File file3 = new File("images/nuevo_9x9.jpg");

        try {
            BufferedImage bufer = ImageIO.read(file);
            if (bufer == null) {
                System.out.println("No se pudo leer la imagen. Revisa la ruta.");
                return;
            }

            // Matriz de 3x3 aplicada 9 veces
            BufferedImage resultado3x3 = bufer;
            for (int i = 0; i < 9; i++) {
                resultado3x3 = aplicarConvolucion(resultado3x3);
            }
            ImageIO.write(resultado3x3, "jpg", file2);
            System.out.println("Imagen 3x3 iterada guardada como: " + file2.getName());

            // Matriz de 9x9 aplicada 1 sola vez
            BufferedImage resultado9x9 = aplicarConvolucion9x9(bufer);
            ImageIO.write(resultado9x9, "jpg", file3);
            System.out.println("Imagen 9x9 dinámica guardada como: " + file3.getName());

        } catch (Exception e) {
            System.out.println("Error en el proceso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static BufferedImage aplicarConvolucion(BufferedImage img) {
        int ancho = img.getWidth();
        int alto = img.getHeight();

        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        // Box blur
        /*
         * float[] matrizConv = {
         * 1f / 9, 1f / 9, 1f / 9,
         * 1f / 9, 1f / 9, 1f / 9,
         * 1f / 9, 1f / 9, 1f / 9
         * };
         */

        // Identy
        /*
         * float[] matrizConv = {
         * 0, 0, 0,
         * 0, 1f, 0,
         * 0, 0, 0
         * };
         */

        // Edge detect
        /*
         * float[] matrizConv = {
         * -1, -1, -1,
         * -1, 8, -1,
         * -1, -1, -1
         * };
         */

        // sobel

        float[] matrizConv = {
                1, 0, -1,
                0, 0, 0,
                -1, 0, 1
        };

        /*
         * float[] matrizConv = {
         * 0, 1, 0,
         * 1, -4, 1,
         * 0, 1, 0
         * };
         */

        // sharpen
        /*
         * float[] matrizConv = {
         * 0, -1, 0,
         * -1, 5, -1,
         * 0, -1, 0
         * };
         */

        // Gaussiano
        /*
         * float[] matrizConv = {
         * 1f / 16, 2f / 16, 1f / 16,
         * 2f / 16, 4f / 16, 2f / 16,
         * 1f / 16, 2f / 16, 1f / 16
         * };
         */

        for (int y = 1; y < alto - 1; y++) {
            for (int x = 1; x < ancho - 1; x++) {

                float sumaR = 0, sumaG = 0, sumaB = 0;
                int indice = 0;

                // Recorremos la máscara de 3x3
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        // Obtenemos el píxel vecino
                        int pixel = img.getRGB(x + j, y + i);

                        // Separamos los canales
                        int r = (pixel >> 16) & 0xFF;
                        int g = (pixel >> 8) & 0xFF;
                        int b = (pixel) & 0xFF;

                        // Multiplicamos por la matriz y acumulamos cada canal por separado
                        sumaR += r * matrizConv[indice];
                        sumaG += g * matrizConv[indice];
                        sumaB += b * matrizConv[indice];

                        indice++;
                    }
                }

                int red = Math.min(255, Math.max(0, (int) sumaR));
                int green = Math.min(255, Math.max(0, (int) sumaG));
                int blue = Math.min(255, Math.max(0, (int) sumaB));

                int pixelNuevo = (red << 16) | (green << 8) | blue;
                salida.setRGB(x, y, pixelNuevo);
            }
        }
        return salida;
    }

    public static BufferedImage aplicarConvolucion9x9(BufferedImage img) {
        int ancho = img.getWidth();
        int alto = img.getHeight();

        BufferedImage salida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        // Matriz dinámica de 9x9
        int n = 9;
        int radio = n / 2;

        float[] matrizConv = new float[n * n];
        for (int i = 0; i < matrizConv.length; i++) {
            matrizConv[i] = 1f / (n * n);
        }

        // Saltamos los bordes dependiendo del radio del kernel
        for (int y = radio; y < alto - radio; y++) {
            for (int x = radio; x < ancho - radio; x++) {

                float sumaR = 0, sumaG = 0, sumaB = 0;
                int indice = 0;

                for (int i = -radio; i <= radio; i++) {
                    for (int j = -radio; j <= radio; j++) {

                        int pixel = img.getRGB(x + j, y + i);

                        int r = (pixel >> 16) & 0xFF;
                        int g = (pixel >> 8) & 0xFF;
                        int b = (pixel) & 0xFF;

                        sumaR += r * matrizConv[indice];
                        sumaG += g * matrizConv[indice];
                        sumaB += b * matrizConv[indice];

                        indice++;
                    }
                }

                int red = Math.min(255, Math.max(0, (int) sumaR));
                int green = Math.min(255, Math.max(0, (int) sumaG));
                int blue = Math.min(255, Math.max(0, (int) sumaB));

                int pixelNuevo = (red << 16) | (green << 8) | blue;
                salida.setRGB(x, y, pixelNuevo);
            }
        }
        return salida;
    }
}