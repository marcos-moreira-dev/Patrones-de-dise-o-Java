# Diagramas de la guía UML

Todos los diagramas mostrados en `UML_GUIDE.md` son archivos `.svg`. Asi se ven tanto en GitHub como en navegadores donde los bloques interactivos Mermaid no llegan a renderizarse.

Los tres diagramas de cardinalidad (`asociacion-profesor-curso`, `agregacion-equipo-jugador` y `composicion-orden-linea`) tienen modelos fuente `.uml` en PlantUML. Si cambias nombres, miembros o multiplicidades, **actualiza el par `.uml` / `.svg`** correspondiente.

Los demás SVG se producen con un generador pequeño que solo necesita Node.js:

```bash
node docs/uml/generar-ejemplos.mjs
```

Si modificas uno de estos ejemplos, cambia su definicion en `generar-ejemplos.mjs` y ejecuta el comando. Todos los diagramas usan texto y trazos vectoriales: se pueden ampliar sin perder nitidez.
