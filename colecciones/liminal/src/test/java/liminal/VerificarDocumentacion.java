package liminal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

/** Verifica que las 23 fichas tengan SVG válido y enlaces a código. */
public final class VerificarDocumentacion {
    private static final Pattern IMAGEN = Pattern.compile("!\\[[^]]*\\]\\(([^)]+\\.svg)\\)");
    private static final Pattern CODIGO = Pattern.compile("\\]\\(([^)]+\\.java)\\)");

    private VerificarDocumentacion() {}

    public static void ejecutar() {
        Path base = Path.of("docs");
        Path fichas = base.resolve("patrones");
        Path diagramas = base.resolve("uml");
        try {
            List<Path> documentos = listar(fichas, ".md");
            List<Path> imagenes = listar(diagramas, ".svg");
            if (documentos.size() != 23 || imagenes.size() != 23) {
                throw new AssertionError("Se esperan 23 fichas y 23 diagramas UML");
            }
            for (Path documento : documentos) {
                String markdown = Files.readString(documento);
                Matcher imagen = IMAGEN.matcher(markdown);
                Matcher codigo = CODIGO.matcher(markdown);
                if (!imagen.find() || !codigo.find()) {
                    throw new AssertionError("Ficha sin imagen o clase cliente: " + documento);
                }
                Path svg = documento.getParent().resolve(imagen.group(1)).normalize();
                Path java = documento.getParent().resolve(codigo.group(1)).normalize();
                if (!Files.isRegularFile(svg) || !Files.isRegularFile(java)) {
                    throw new AssertionError("Enlace inexistente en " + documento);
                }
                if (imagen.find()) throw new AssertionError("Debe haber un UML principal por ficha");
            }
            for (Path imagen : imagenes) validarSvg(imagen);
            System.out.println("OK 23/23 fichas, enlaces y diagramas SVG.");
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
}
