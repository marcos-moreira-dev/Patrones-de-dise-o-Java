package liminal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

/** Audita las fichas, el UML y las escenas visuales originales. */
public final class VerificarDocumentacion {
    private static final Pattern UML = Pattern.compile("!\\[[^]]*\\]\\(([^)]+\\.svg)\\)");
    private static final Pattern CODIGO = Pattern.compile("\\]\\(([^)]+\\.java)\\)");
    private static final Pattern AMBIENTE = Pattern.compile("!\\[([^]]+)\\]\\(([^)]+\\.webp)\\)");

    private VerificarDocumentacion() {}

    public static void ejecutar() {
        Path fichas = Path.of("docs", "patrones");
        Path diagramas = Path.of("docs", "uml");
        Path ambientes = Path.of("assets", "ambientes");
        try {
            List<Path> documentos = listar(fichas, ".md");
            List<Path> imagenes = listar(diagramas, ".svg");
            List<Path> fondos = listar(ambientes, ".webp");

            if (documentos.size() != 23 || imagenes.size() != 23) {
                throw new AssertionError("Se esperan 23 fichas y 23 diagramas UML");
            }
            if (fondos.size() != 3) {
                throw new AssertionError("Se esperan 3 ambientes originales en WebP");
            }

            for (Path documento : documentos) {
                String markdown = Files.readString(documento);
                Matcher uml = UML.matcher(markdown);
                Matcher codigo = CODIGO.matcher(markdown);
                if (!uml.find() || !codigo.find()) {
                    throw new AssertionError("Ficha sin UML o clase cliente: " + documento);
                }
                Path svg = documento.getParent().resolve(uml.group(1)).normalize();
                Path java = documento.getParent().resolve(codigo.group(1)).normalize();
                if (!Files.isRegularFile(svg) || !Files.isRegularFile(java)) {
                    throw new AssertionError("Enlace roto en " + documento);
                }
                if (uml.find()) throw new AssertionError("Debe haber un UML principal por ficha");
            }
            for (Path imagen : imagenes) validarSvg(imagen);
            for (Path fondo : fondos) validarWebp(fondo);

            List<Path> documentosVisuales = new ArrayList<>(documentos);
            documentosVisuales.add(Path.of("README.md"));
            documentosVisuales.add(Path.of("GUIA_VISUAL.md"));
            documentosVisuales.add(Path.of("assets", "README.md"));

            int referencias = 0;
            for (Path documento : documentosVisuales) {
                Matcher imagen = AMBIENTE.matcher(Files.readString(documento));
                while (imagen.find()) {
                    if (imagen.group(1).isBlank()) {
                        throw new AssertionError("Imagen sin texto alternativo: " + documento);
                    }
                    Path ruta = documento.getParent().resolve(imagen.group(2)).normalize();
                    if (!ruta.startsWith(ambientes) || !Files.isRegularFile(ruta)) {
                        throw new AssertionError("Fondo roto o fuera de la galería: " + documento);
                    }
                    referencias++;
                }
            }
            if (referencias < 14) throw new AssertionError("Faltan imágenes en las fichas");
            System.out.println("OK 23/23 fichas, clases cliente y UML SVG.");
            System.out.println("OK 3/3 fondos WebP y " + referencias + " referencias Markdown.");
        } catch (Exception e) {
            throw new AssertionError("Auditoría de documentación fallida", e);
        }
    }

    private static List<Path> listar(Path raiz, String extension) throws IOException {
        try (Stream<Path> archivos = Files.walk(raiz)) {
            return archivos.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(extension)).toList();
        }
    }

    private static void validarSvg(Path imagen) throws Exception {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        var documento = f.newDocumentBuilder().parse(imagen.toFile());
        if (!documento.getDocumentElement().getNodeName().equals("svg")
                || documento.getElementsByTagName("title").getLength() == 0
                || documento.getElementsByTagName("desc").getLength() == 0) {
            throw new AssertionError("SVG incompleto: " + imagen);
        }
    }

    private static void validarWebp(Path imagen) throws IOException {
        byte[] datos = Files.readAllBytes(imagen);
        if (datos.length < 12
                || !new String(datos, 0, 4, StandardCharsets.US_ASCII).equals("RIFF")
                || !new String(datos, 8, 4, StandardCharsets.US_ASCII).equals("WEBP")) {
            throw new AssertionError("Imagen WebP dañada o formato incorrecto: " + imagen);
        }
    }
}
