import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class matrizDeColores {

    public static void ejemplo(String rutaEntrada, String rutaSalida) {

        int r1, g1, b1;

        try {

            /*
             * float[][] colores = {
             * { 0.299f, 0.587f, 0.114f, 0.0f },
             * { 0.299f, 0.587f, 0.114f, 0.0f },
             * { 0.299f, 0.587f, 0.114f, 0.0f },
             * { 0.0f, 0.0f, 0.0f, 1.0f },
             * };
             */

            /*
             * float[][] sepia = {
             * { 0.393f, 0.769f, 0.189f, 0.0f },
             * { 0.349f, 0.686f, 0.168f, 0.0f },
             * { 0.272f, 0.534f, 0.131f, 0.0f },
             * { 0.0f, 0.0f, 0.0f, 1.0f }
             * };
             */

            float[][] brillo = {
                    { 1.0f, 0.0f, 0.0f, 0.3f },
                    { 0.0f, 1.0f, 0.0f, 0.3f },
                    { 0.0f, 0.0f, 1.0f, 0.3f },
                    { 0.0f, 0.0f, 0.0f, 1.0f }
            };

            File imagenEntrada = new File(rutaEntrada);
            BufferedImage imagenOriginal = ImageIO.read(imagenEntrada);

            if (imagenOriginal == null) {
                System.out.println("Error");
                return;
            }

            int ancho = imagenOriginal.getWidth();
            int alto = imagenOriginal.getHeight();

            BufferedImage imagenSalida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    int imagenPixel = imagenOriginal.getRGB(x, y);

                    int r = (imagenPixel >> 16) & 0xFF;
                    int g = (imagenPixel >> 8) & 0xFF;
                    int b = imagenPixel & 0xFF;

                    float[][] matriz = brillo;

                    r1 = Math.min(255, Math.max(0, (int) (r * matriz[0][0] + g * matriz[0][1] + b * matriz[0][2])));
                    g1 = Math.min(255, Math.max(0, (int) (r * matriz[1][0] + g * matriz[1][1] + b * matriz[1][2])));
                    b1 = Math.min(255, Math.max(0, (int) (r * matriz[2][0] + g * matriz[2][1] + b * matriz[2][2])));

                    r1 = Math.min(255, Math.max(0, r1));
                    g1 = Math.min(255, Math.max(0, g1));
                    b1 = Math.min(255, Math.max(0, b1));

                    int nuevoColor = (255 << 24) | (r1 << 16) | (g1 << 8) | b1;

                    imagenSalida.setRGB(x, y, nuevoColor);

                }
            }

            File archivoSalida = new File(rutaSalida);
            boolean exito = ImageIO.write(imagenSalida, "jpg", archivoSalida);
            if (exito) {
                System.out.println("Listo: Imagen generada en " + archivoSalida.getAbsolutePath());
            } else {
                System.out.println("Error: No se pudo guardar la imagen en " + rutaSalida);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String rutaEntrada = "images\\manu.jpg";
        String rutaSalida = "images\\manu2_colores.jpg";
        ejemplo(rutaEntrada, rutaSalida);

    }
}
