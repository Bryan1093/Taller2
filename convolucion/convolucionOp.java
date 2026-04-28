import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;

public class convolucionOp {

    public static void main(String[] args) {
        try {
            File archivoEntrada = new File("images/manu.jpg");
            BufferedImage imagenOriginal = ImageIO.read(archivoEntrada);

            if (imagenOriginal == null) {
                System.out.println("No se encontró la imagen.");
                return;
            }

            float[] matriz = kernels.KBlur;
            Kernel kernel = new Kernel(3, 3, matriz);

            ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            BufferedImage imagenFiltrada = op.filter(imagenOriginal, null);

            File archivoSalida = new File("images/resultado_op.jpg");
            ImageIO.write(imagenFiltrada, "jpg", archivoSalida);

            System.out.println("¡Filtro aplicado con éxito!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
