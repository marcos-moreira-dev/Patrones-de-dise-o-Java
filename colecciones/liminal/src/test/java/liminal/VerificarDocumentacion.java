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

    private static final Pattern TIPO_SUPERIOR = Pattern.compile(
            "(?m)^(?:(?:public|abstract|final|sealed|non-sealed|static)\\s+)*"
                    + "(?:class|interface|record|enum)\\s+([A-Za-z_$][\\w$]*)");

    private VerificarDocumentacion() {}

    public static void ejecutar() {
        Path fichas = Path.of("docs", "patrones");
        Path diagramas = Path.of("docs", "uml");
        Path ambientes = Path.of("assets", "ambientes");
        Path objetos = Path.of("assets", "objetos");
        try {
            List<Path> documentos = listar(fichas, ".md");
            List<Path> imagenes = listar(diagramas, ".svg");
            Path secuencias = Path.of("docs", "secuencias");
            List<Path> ilustracionesDinamicas = listar(secuencias, ".svg");
            List<Path> fondos = listar(ambientes, ".webp");
            List<Path> laminas = listar(objetos, ".webp");

            if (documentos.size() != 23 || imagenes.size() != 23) {
                throw new AssertionError("Se esperan 23 fichas y 23 diagramas UML");
            }
            if (ilustracionesDinamicas.size() != 3) {
                throw new AssertionError("Se esperan 3 secuencias didácticas SVG");
            }
            if (fondos.size() != 3 || laminas.size() != 1) {
                throw new AssertionError("Se esperan 3 ambientes y 1 lámina de objetos en WebP");
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
                if (!Files.isRegularFile(svg) || !Files.isRegularFile(java)
                        || !svg.startsWith(diagramas)) {
                    throw new AssertionError("UML principal o clase Java inexistentes: " + documento);
                }
                comprobarCarpetaJava(documento, java, markdown);
                while (uml.find()) {
                    Path adicional = documento.getParent().resolve(uml.group(1)).normalize();
                    if (!adicional.startsWith(secuencias) || !Files.isRegularFile(adicional)) {
                        throw new AssertionError("SVG adicional fuera de secuencias: " + documento);
                    }
                }
            }
            for (Path imagen : imagenes) validarSvg(imagen);
            for (Path secuencia : ilustracionesDinamicas) validarSvg(secuencia);
            // Comprobaciones semánticas puntuales además de XML bien formado.
            comprobarUml(diagramas, "composite.svg", "hijos: 0..*");
            comprobarUml(diagramas, "decorator.svg", "envuelta: 1");
            comprobarUml(diagramas, "chainofresponsibility.svg", "siguiente: 0..1");
            comprobarUml(diagramas, "iterator.svg", "Iterable", "Iterator");
            comprobarUml(diagramas, "flyweight.svg",
                    "Map&lt;ClaveTextura, Textura&gt;", "0..*");
            // UML: + público, # protegido y ~ paquete, conforme a las firmas Java.
            comprobarUml(diagramas, "factorymethod.svg", "# crearCriatura(...)");
            comprobarUml(diagramas, "chainofresponsibility.svg",
                    "~ verificar(s): String", "# detectarRechazo(s): String");
            comprobarUml(diagramas, "templatemethod.svg", "# observar(): String");
            comprobarUml(diagramas, "singleton.svg", "~ instancia(): PanelAvisos");
            comprobarUml(diagramas, "prototype.svg", "~ objetos(): List");
            comprobarUml(diagramas, "adapter.svg", "~ emitirPorFrecuencia");
            comprobarUml(diagramas, "bridge.svg", "# mensaje(): String");
            comprobarUml(diagramas, "composite.svg", "~ agregar(objeto): void");
            comprobarUml(diagramas, "facade.svg", "~ cruzar(destino): void", "~ salida(origen, destino)");
            comprobarUml(diagramas, "flyweight.svg", "~ obtener(...): Textura");
            comprobarUml(diagramas, "abstractfactory.svg", "~ descripcion(): String");
            comprobarUml(diagramas, "command.svg", "~ pulsar(): String");
            comprobarUml(diagramas, "interpreter.svg", "~ analizar(texto): Expresion");
            comprobarUml(diagramas, "iterator.svg", "~ id(): String");
            comprobarUml(diagramas, "mediator.svg", "~ avisar()");
            comprobarUml(diagramas, "memento.svg", "~ guardar(linterna)", "~ restaurar(recuerdo)");
            comprobarUml(diagramas, "observer.svg", "~ suscribir(observador): void");
            comprobarUml(diagramas, "state.svg", "~ desbloquear(llave): void");
            comprobarUml(diagramas, "strategy.svg", "~ cambiarEstrategia(nueva)");
            comprobarUml(diagramas, "abstractfactory.svg",
                    "LuzAmarilla", "LuzPiscinas", "PuertaAmarilla", "PuertaPiscinas",
                    "FabricaAmarilla", "FabricaPiscinas");
            comprobarUml(diagramas, "facade.svg",
                    "salida(origen, destino)", "salidas: Map&lt;Ruta, Salida&gt;",
                    "puerta: PuertaPaso", "0..1");
            comprobarUml(diagramas, "observer.svg",
                    "ObservadorInterruptor", "observadores: Map", "0..*");
            String[][] rutas = {
                    {"state", "transiciones-state.svg", "CERRADA", "BLOQUEADA"},
                    {"strategy", "recorrido-strategy.svg", "M-01", "Piscina"},
                    {"observer", "notificaciones-observer.svg", "I-01", "L-01"}
            };
            String atlas = Files.readString(Path.of("GUIA_VISUAL.md"));
            for (String[] ruta : rutas) {
                Path secuencia = secuencias.resolve(ruta[1]);
                comprobarUml(secuencias, ruta[1], ruta[2], ruta[3]);
                Path ficha = fichas.resolve("comportamiento").resolve(ruta[0] + ".md");
                String contenido = Files.readString(ficha);
                if (!contenido.contains("../../secuencias/" + ruta[1])
                        || !atlas.contains("docs/secuencias/" + ruta[1])
                        || !Files.isRegularFile(secuencia)) {
                    throw new AssertionError("Secuencia no enlazada: " + ruta[1]);
                }
            }
            System.out.println("OK 3/3 secuencias SVG, fichas y atlas enlazados.");
            for (Path fondo : fondos) validarWebp(fondo);
            for (Path lamina : laminas) validarWebp(lamina);

            Path laboratorio = Path.of("docs", "laboratorios");
            Path guiaRelaciones = laboratorio.resolve("relaciones-uml.md");
            Path clasesRelaciones = laboratorio.resolve("relaciones-uml.svg");
            Path objetosRelaciones = laboratorio.resolve("referencias-objetos.svg");
            String teoria = Files.readString(guiaRelaciones);
            if (!teoria.contains("R-09") || !teoria.contains("LAB-UML-01")
                    || !teoria.contains("relaciones-uml.svg")
                    || !teoria.contains("referencias-objetos.svg")) {
                throw new AssertionError("Falta teoría o enlaces del laboratorio UML");
            }
            // Cada colaborador UML aparece en la carpeta con su propio nombre y archivo.
            Path clasesJava = Path.of("src", "main", "java", "liminal", "laboratorios", "relaciones");
            for (String tipo : List.of("Habitacion", "MapaLiminal", "Explorador",
                    "EquipoExploracion", "VistaEntrada", "RegistroExpedicion",
                    "InformeExpedicion", "ServicioConsulta", "Reglas")) {
                Path fuente = clasesJava.resolve(tipo + ".java");
                if (!Files.isRegularFile(fuente)) {
                    throw new AssertionError("Falta colaborador navegable UML: " + tipo);
                }
                Matcher declarado = TIPO_SUPERIOR.matcher(Files.readString(fuente));
                if (!declarado.find() || !tipo.equals(declarado.group(1)) || declarado.find()) {
                    throw new AssertionError("El archivo UML debe tener un único tipo superior: " + fuente);
                }
            }
            validarSvg(clasesRelaciones);
            validarSvg(objetosRelaciones);
            comprobarUml(laboratorio, "relaciones-uml.svg",
                    "RegistroExpedicion", "EquipoExploracion", "VistaEntrada",
                    "EntradaRegistro", "~ buscar(id): Habitacion",
                    "~ anotar(id, texto)", "~ consultar(): String");
            comprobarUml(laboratorio, "referencias-objetos.svg",
                    "EquipoExploracion", "RegistroExpedicion", "InformeExpedicion");
            comprobarUml(laboratorio, "referencias-objetos.svg",
                    "entrada:EntradaRegistro", "activa=false");
            System.out.println("OK laboratorio UML, dos diagramas y reglas enlazadas.");

            comprobarAmpliacion();
            List<Path> documentosVisuales = new ArrayList<>(documentos);
            documentosVisuales.add(Path.of("docs", "laboratorios", "java", "class-vs-record.md"));
            documentosVisuales.addAll(listar(Path.of("docs", "variantes"), ".md"));
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
                    Path directorio = documento.getParent() == null ? Path.of(".") : documento.getParent();
                    Path ruta = directorio.resolve(imagen.group(2)).normalize();
                    if ((!ruta.startsWith(ambientes) && !ruta.startsWith(objetos))
                            || !Files.isRegularFile(ruta)) {
                        throw new AssertionError("Fondo roto o fuera de la galería: " + documento);
                    }
                    referencias++;
                }
            }
            if (referencias < 14) throw new AssertionError("Faltan imágenes en las fichas");
            System.out.println("OK 23/23 fichas, clases cliente y UML SVG.");
            System.out.println("OK 3/3 fondos + 1/1 lámina WebP y "
                    + referencias + " referencias Markdown.");
        } catch (Exception e) {
            throw new AssertionError("Auditoría de documentación fallida", e);
        }
    }

    /** Solo el catálogo base tiene un UML propio por ficha; las variantes se verifican aparte. */
    private static void comprobarAmpliacion() throws IOException {
        Path laboratorio = Path.of("docs", "laboratorios", "java", "class-vs-record.md");
        String teoria = Files.readString(laboratorio);
        if (!teoria.contains("ClaveTextura") || !teoria.contains("mermaid")) {
            throw new AssertionError("Laboratorio class/record incompleto");
        }
        verificarEnlacesJava(laboratorio);

        List<Path> variantes = listar(Path.of("docs", "variantes"), ".md");
        if (variantes.size() != 7) {
            throw new AssertionError("Se esperan 7 variantes GoF, no " + variantes.size());
        }
        for (Path ficha : variantes) {
            String texto = Files.readString(ficha);
            if (!texto.contains("R-LOCAL-")
                    || !(texto.contains("```mermaid") || texto.contains("~~~mermaid"))
                    || texto.contains("§")
                    || !texto.contains("Cliente") || !texto.contains("Prueba")) {
                throw new AssertionError("Variante sin reglas, UML o ruta de lectura: " + ficha);
            }
            verificarEnlacesJava(ficha);
        }
        System.out.println("OK laboratorio class/record y 7/7 fichas de variantes.");
    }

    private static void verificarEnlacesJava(Path ficha) throws IOException {
        Matcher enlaces = CODIGO.matcher(Files.readString(ficha));
        int encontrados = 0;
        while (enlaces.find()) {
            Path ruta = ficha.getParent().resolve(enlaces.group(1)).normalize();
            if (!Files.isRegularFile(ruta)) {
                throw new AssertionError("Enlace Java roto: " + ficha + " -> " + ruta);
            }
            encontrados++;
        }
        if (encontrados < 2) {
            throw new AssertionError("Faltan enlaces a cliente y prueba: " + ficha);
        }
    }

    private static List<Path> listar(Path raiz, String extension) throws IOException {
        try (Stream<Path> archivos = Files.walk(raiz)) {
            return archivos.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(extension)).toList();
        }
    }

    /** Evita repetir la confusión de Builder: cada tipo en un archivo localizable. */
    private static void comprobarCarpetaJava(Path ficha, Path cliente, String markdown)
            throws IOException {
        String nombreCliente = cliente.getFileName().toString();
        if (!nombreCliente.startsWith("Cliente") || !nombreCliente.endsWith(".java")) {
            throw new AssertionError("La ficha debe enlazar primero su Cliente: " + ficha);
        }

        Path carpeta = cliente.getParent();
        List<Path> archivos = listar(carpeta, ".java");
        if (archivos.size() < 2) {
            throw new AssertionError("No hay clases navegables por separado: " + carpeta);
        }
        for (Path archivo : archivos) {
            Matcher tipo = TIPO_SUPERIOR.matcher(Files.readString(archivo));
            if (!tipo.find()) throw new AssertionError("Archivo sin tipo: " + archivo);
            String nombreTipo = tipo.group(1);
            if (tipo.find() || !archivo.getFileName().toString().equals(nombreTipo + ".java")) {
                throw new AssertionError("Un tipo superior por archivo: " + archivo);
            }
        }

        String categoria = ficha.getParent().getFileName().toString();
        String patron = ficha.getFileName().toString().replace(".md", "");
        String nombrePrueba = "Prueba" + nombreCliente.substring("Cliente".length());
        Path prueba = Path.of("src", "test", "java", "liminal", categoria, patron, nombrePrueba);
        if (!Files.isRegularFile(prueba) || !markdown.contains(nombrePrueba)) {
            throw new AssertionError("Prueba separada o enlace faltante: " + ficha);
        }
        if (Files.readString(cliente).contains("public static void probar()")) {
            throw new AssertionError("Las pruebas no deben estar en el cliente: " + cliente);
        }
    }

    private static void comprobarUml(Path directorio, String nombre, String... elementos)
            throws IOException {
        String contenido = Files.readString(directorio.resolve(nombre));
        for (String elemento : elementos) {
            if (!contenido.contains(elemento)) {
                throw new AssertionError("UML de " + nombre + " omite: " + elemento);
            }
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
