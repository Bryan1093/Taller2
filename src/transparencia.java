import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class transparencia {

    public static void main(String[] args) {
        File file1 = new File("images/manchester.jpg");
        File file2 = new File("images/montanas.jpg");
        File file3 = new File("images/galaxia2.jpg"); //
        File fileSalida = new File("images/resultado_transparencia.jpg");

        float w1 = 0.33f;
        float w2 = 0.33f;
        float w3 = 0.34f;

        int mascara = 0xFF;

        try {
            BufferedImage bufer1 = ImageIO.read(file1);
            BufferedImage bufer2 = ImageIO.read(file2);
            BufferedImage bufer3 = ImageIO.read(file3);

            if (bufer1 == null || bufer2 == null || bufer3 == null) {
                System.out.println("Error: No se pudieron leer las imágenes.");
                return;
            }

            int ancho = Math.min(Math.min(bufer1.getWidth(), bufer2.getWidth()), bufer3.getWidth());
            int alto = Math.min(Math.min(bufer1.getHeight(), bufer2.getHeight()), bufer3.getHeight());

            Image impTemp2 = bufer2.getScaledInstance(ancho, alto, Image.SCALE_FAST);
            BufferedImage bufer2Temp = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
            Graphics2D grTemp2 = bufer2Temp.createGraphics();
            grTemp2.drawImage(impTemp2, 0, 0, null);
            grTemp2.dispose();
            bufer2 = bufer2Temp;

            Image impTemp3 = bufer3.getScaledInstance(ancho, alto, Image.SCALE_FAST);
            BufferedImage bufer3Temp = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
            Graphics2D grTemp3 = bufer3Temp.createGraphics();
            grTemp3.drawImage(impTemp3, 0, 0, null);
            grTemp3.dispose();
            bufer3 = bufer3Temp;

            BufferedImage buferSalida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    int pixel1 = bufer1.getRGB(x, y);
                    int pixel2 = bufer2.getRGB(x, y);
                    int pixel3 = bufer3.getRGB(x, y);

                    int r1 = (pixel1 >> 16) & mascara;
                    int g1 = (pixel1 >> 8) & mascara;
                    int b1 = (pixel1 >> 0) & mascara;

                    int r2 = (pixel2 >> 16) & mascara;
                    int g2 = (pixel2 >> 8) & mascara;
                    int b2 = (pixel2 >> 0) & mascara;

                    int r3 = (pixel3 >> 16) & mascara;
                    int g3 = (pixel3 >> 8) & mascara;
                    int b3 = (pixel3 >> 0) & mascara;

                    int rOut = (int) (w1 * r1 + w2 * r2 + w3 * r3);
                    int gOut = (int) (w1 * g1 + w2 * g2 + w3 * g3);
                    int bOut = (int) (w1 * b1 + w2 * b2 + w3 * b3);

                    int pixelNuevo = (rOut << 16) | (gOut << 8) | bOut;
                    buferSalida.setRGB(x, y, pixelNuevo);
                }
            }

            if (ImageIO.write(buferSalida, "jpg", fileSalida)) {
                System.out.println("Imagen guardada!");
            }

        } catch (IOException e) {
            System.out.println("Error al procesar: " + e.getMessage());
        }
    }
}
