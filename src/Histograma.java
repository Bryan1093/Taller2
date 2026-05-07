import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Histograma {

    public static void main(String[] args) {

        File file = new File("images/galaxia.jpg");
        File file2 = new File("images/galaxiaHistoRojo.jpg");
        File file3 = new File("images/galaxiaHistoVerde.jpg");
        File file4 = new File("images/galaxiaHistoAzul.jpg");
        File file5 = new File("images/galaxiaHistoCombinado.jpg");

        int ancho, alto, pixel;
        int r, g, b;

        try {

            BufferedImage bufer = ImageIO.read(file);

            if (bufer == null) {
                System.out.println("No se pudo cargar la imagen.");
                return;
            }

            ancho = bufer.getWidth();
            alto = bufer.getHeight();

            int anchoHisto = 800;
            int altoHisto = 600;

            int[] histoR = new int[256];
            int[] histoG = new int[256];
            int[] histoB = new int[256];

            BufferedImage buferRojo = new BufferedImage(anchoHisto, altoHisto, BufferedImage.TYPE_INT_RGB);
            BufferedImage buferVerde = new BufferedImage(anchoHisto, altoHisto, BufferedImage.TYPE_INT_RGB);
            BufferedImage buferAzul = new BufferedImage(anchoHisto, altoHisto, BufferedImage.TYPE_INT_RGB);
            BufferedImage buferCombinado = new BufferedImage(anchoHisto, altoHisto, BufferedImage.TYPE_INT_RGB);

            Graphics2D grR = buferRojo.createGraphics();
            Graphics2D grG = buferVerde.createGraphics();
            Graphics2D grB = buferAzul.createGraphics();
            Graphics2D grC = buferCombinado.createGraphics();

            grR.setColor(Color.WHITE);
            grR.fillRect(0, 0, anchoHisto, altoHisto);
            grG.setColor(Color.WHITE);
            grG.fillRect(0, 0, anchoHisto, altoHisto);
            grB.setColor(Color.WHITE);
            grB.fillRect(0, 0, anchoHisto, altoHisto);
            grC.setColor(Color.WHITE);
            grC.fillRect(0, 0, anchoHisto, altoHisto);

            for (int i = 0; i < alto; i++) {

                for (int j = 0; j < ancho; j++) {

                    pixel = bufer.getRGB(j, i);

                    r = (pixel >> 16) & 0xFF;
                    g = (pixel >> 8) & 0xFF;
                    b = pixel & 0xFF;

                    histoR[r]++;
                    histoG[g]++;
                    histoB[b]++;
                }
            }

            imprimir(histoR);
            imprimir(histoG);
            imprimir(histoB);

            int max = maximo(histoR);
            int max2 = maximo(histoG);
            int max3 = maximo(histoB);

            float escalaX = (float) anchoHisto / 256;
            float escalaY = (float) altoHisto / max;
            float escalaY2 = (float) altoHisto / max2;
            float escalaY3 = (float) altoHisto / max3;

            grR.setColor(Color.RED);
            grC.setColor(Color.RED);

            for (int i = 1; i < histoR.length; i++) {
                int x1 = (int) (escalaX * (i - 1));
                int y1 = altoHisto - (int) (escalaY * histoR[i - 1]);
                int x2 = (int) (escalaX * i);
                int y2 = altoHisto - (int) (escalaY * histoR[i]);
                grR.drawLine(x1, y1, x2, y2);
                grC.drawLine(x1, y1, x2, y2);
            }

            grG.setColor(Color.GREEN);
            grC.setColor(Color.GREEN);

            for (int i = 1; i < histoG.length; i++) {
                int x1 = (int) (escalaX * (i - 1));
                int y1 = altoHisto - (int) (escalaY2 * histoG[i - 1]);
                int x2 = (int) (escalaX * i);
                int y2 = altoHisto - (int) (escalaY2 * histoG[i]);
                grG.drawLine(x1, y1, x2, y2);
                grC.drawLine(x1, y1, x2, y2);
            }

            grB.setColor(Color.BLUE);
            grC.setColor(Color.BLUE);

            for (int i = 1; i < histoB.length; i++) {
                int x1 = (int) (escalaX * (i - 1));
                int y1 = altoHisto - (int) (escalaY3 * histoB[i - 1]);
                int x2 = (int) (escalaX * i);
                int y2 = altoHisto - (int) (escalaY3 * histoB[i]);
                grB.drawLine(x1, y1, x2, y2);
                grC.drawLine(x1, y1, x2, y2);
            }

            Graphics2D[] graficos = { grR, grG, grB, grC };
            for (Graphics2D g2 : graficos) {
                g2.setColor(Color.BLACK);
                g2.drawLine(0, altoHisto - 1, anchoHisto, altoHisto - 1);
                g2.drawLine(0, 0, 0, altoHisto);
                g2.dispose();
            }

            ImageIO.write(buferRojo, "jpg", file2);
            ImageIO.write(buferVerde, "jpg", file3);
            ImageIO.write(buferAzul, "jpg", file4);
            ImageIO.write(buferCombinado, "jpg", file5);

            System.out.println("Histograma creado correctamente");

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void imprimir(int[] h) {

        for (int i = 0; i < h.length; i++) {

            System.out.println(i + ": " + h[i]);
        }
    }

    private static int maximo(int[] h) {

        // Ignoramos el 0 absoluto (negro) que en imágenes espaciales es gigante y achata el gráfico
        int aux = h[1];
        int indice = 1;

        for (int i = 1; i < h.length; i++) {

            if (h[i] > aux) {

                aux = h[i];
                indice = i;
            }
        }

        System.out.println("indice = " + indice +
                ", valor = " + aux);

        return aux;
    }
}