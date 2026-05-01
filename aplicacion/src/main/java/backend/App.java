package backend;

import backend.logica.*;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.http.UploadedFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
        }).start(8080);

        app.post("/api/procesar", ctx -> {
            try {
                String efecto = ctx.formParam("efecto");
                String parametroStr = ctx.formParam("parametro");
                float paramFloat = (parametroStr != null && !parametroStr.isEmpty()) ? Float.parseFloat(parametroStr)
                        : 1.0f;
                int paramInt = (int) paramFloat;

                BufferedImage resultado = null;

                if (efecto.startsWith("GEN_")) {
                    int w = 900, h = 500;
                    switch (efecto) {
                        case "GEN_GRAD_HORIZ":
                            resultado = Generadores.generarGradiente(w, h, "HORIZONTAL_NORMAL");
                            break;
                        case "GEN_GRAD_HORIZ_INV":
                            resultado = Generadores.generarGradiente(w, h, "HORIZONTAL_INVERSO_ROJO_AZUL");
                            break;
                        case "GEN_GRAD_VERT":
                            resultado = Generadores.generarGradiente(w, h, "VERTICAL_NORMAL");
                            break;
                        case "GEN_GRAD_VERT_INV":
                            resultado = Generadores.generarGradiente(w, h, "VERTICAL_INVERSO");
                            break;
                        case "GEN_GRAD_RADIAL":
                            resultado = Generadores.generarGradiente(w, h, "RADIAL");
                            break;
                        case "GEN_RUIDO":
                            resultado = Generadores.generarRuido(w, h);
                            break;
                    }
                } else {
                    UploadedFile archivoWeb = ctx.uploadedFile("imagen");
                    if (archivoWeb == null) {
                        ctx.status(400).result("No enviaste ninguna imagen.");
                        return;
                    }

                    BufferedImage imagenOriginal;
                    try (InputStream is = archivoWeb.content()) {
                        imagenOriginal = ImageIO.read(is);
                    }

                    switch (efecto) {
                        case "BLANCO_Y_NEGRO":
                            resultado = FiltrosBasicos.aplicarBlancoYNegro(imagenOriginal);
                            break;
                        case "TRANSPARENCIA":
                            resultado = FiltrosBasicos.aplicarTransparencia(imagenOriginal, paramFloat);
                            break;
                        case "ESCALA_GRISES":
                            resultado = FiltrosBasicos.aplicarEscalaGrises(imagenOriginal);
                            break;
                        case "NEGATIVO":
                            resultado = FiltrosBasicos.aplicarNegativo(imagenOriginal);
                            break;
                        case "BRILLO":
                            resultado = FiltrosBasicos.aplicarBrillo(imagenOriginal, paramInt);
                            break;
                        case "SATURACION":
                            resultado = FiltrosBasicos.aplicarSaturacion(imagenOriginal, paramFloat);
                            break;
                        case "RECORTE_BITS":
                            resultado = FiltrosBasicos.aplicarRecorteBits(imagenOriginal, paramInt);
                            break;
                        case "SOLO_RECORTE":
                            resultado = FiltrosBasicos.aplicarSoloRecorteBits(imagenOriginal, paramInt);
                            break;
                        case "TINTE_OCRE":
                            resultado = FiltrosBasicos.aplicarTinteOcre(imagenOriginal);
                            break;

                        case "VIDRIO_ESMERILADO":
                            resultado = EfectosRetro.aplicarVidrioEsmerilado(imagenOriginal);
                            break;
                        case "DESVANECIMIENTO_CIRCULAR":
                            resultado = EfectosRetro.aplicarDesvanecimientoCircular(imagenOriginal);
                            break;
                        case "RETRO_POSTERIZACION":
                            resultado = EfectosRetro.aplicarEfectoRetro(imagenOriginal, paramInt);
                            break;
                        case "RETRO_2_R":
                            resultado = EfectosRetro.aplicarEfectoRetroAnulacion(imagenOriginal, paramInt, "GB");
                            break;
                        case "RETRO_2_G":
                            resultado = EfectosRetro.aplicarEfectoRetroAnulacion(imagenOriginal, paramInt, "RB");
                            break;
                        case "RETRO_2_B":
                            resultado = EfectosRetro.aplicarEfectoRetroAnulacion(imagenOriginal, paramInt, "RG");
                            break;
                        case "RETRO_ANULAR_R":
                            resultado = EfectosRetro.aplicarEfectoRetroAnulacion(imagenOriginal, 256, "GB");
                            break;
                        case "RETRO_ANULAR_G":
                            resultado = EfectosRetro.aplicarEfectoRetroAnulacion(imagenOriginal, 256, "RB");
                            break;
                        case "RETRO_ANULAR_B":
                            resultado = EfectosRetro.aplicarEfectoRetroAnulacion(imagenOriginal, 256, "RG");
                            break;
                        case "GRISES_RETRO":
                            resultado = EfectosRetro.aplicarEscalaDeGrisesRetro(imagenOriginal, paramInt);
                            break;

                        case "IDENTITY":
                            resultado = FiltrosEspaciales.aplicarIdentity(imagenOriginal);
                            break;
                        case "EDGE_1":
                            resultado = FiltrosEspaciales.aplicarEdge1(imagenOriginal);
                            break;
                        case "EDGE_2":
                            resultado = FiltrosEspaciales.aplicarEdge2(imagenOriginal);
                            break;
                        case "EDGE_3":
                            resultado = FiltrosEspaciales.aplicarEdge3(imagenOriginal);
                            break;
                        case "SHARPEN_WIKI":
                            resultado = FiltrosEspaciales.aplicarSharpenWiki(imagenOriginal);
                            break;
                        case "BOX_BLUR_WIKI":
                            resultado = FiltrosEspaciales.aplicarBoxBlurWiki(imagenOriginal);
                            break;
                        case "BOX_BLUR":
                            resultado = FiltrosEspaciales.aplicarBoxBlur(imagenOriginal, paramInt);
                            break;

                        case "DIVIDIDO_3":
                            resultado = EfectosRetro.aplicarDivididoEn3(imagenOriginal);
                            break;
                        case "ESPEJO":
                            resultado = EfectosRetro.aplicarEspejo(imagenOriginal);
                            break;
                        case "VERDE_GRIS":
                            resultado = EfectosRetro.aplicarVerdeYGris(imagenOriginal);
                            break;
                        case "RELIEVE_ROJO":
                            resultado = FiltrosEspaciales.aplicarRelieveRojo(imagenOriginal);
                            break;
                        case "SOBEL_N":
                            resultado = FiltrosEspaciales.aplicarSobelNVeces(imagenOriginal, paramInt);
                            break;

                        default:
                            resultado = imagenOriginal;
                            break;
                    }
                }

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(resultado, "png", os);
                ctx.contentType("image/png");
                ctx.result(os.toByteArray());

            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).result("Error interno: " + e.getMessage());
            }
        });

        System.out.println("Backend listo en http://localhost:8080");
    }
}
