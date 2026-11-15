# Patrones GoF clásicos — Java 21

Ejemplos técnicos tradicionales, deliberadamente pequeños, para estudiar los
**23 patrones** sin depender del dominio liminal ni de un framework.

- [Creacionales](creacionales/): construcción y creación de objetos
- [Estructurales](estructurales/): adaptación y composición
- [De comportamiento](comportamiento/): colaboración, estados y algoritmos

`Main.java` abre el menú; `App.java` coordina las tres categorías.
`ProbarCreacionales`, `ProbarEstructurales` y `ProbarComportamiento` orquestan
sus ejemplos. Son *clientes de demostración*, no superclases GoF.

Compilación desde la raíz del repositorio (Git Bash o Linux):

```bash
mkdir -p out/clasicos
find patrones/clasicos/creacionales patrones/clasicos/estructurales patrones/clasicos/comportamiento -name '*.java' -print0 \
  | xargs -0 javac -encoding UTF-8 -d out/clasicos patrones/clasicos/App.java patrones/clasicos/Main.java
java -cp out/clasicos Main
```

Los paquetes declarados dentro de `.java` siguen siendo `creacionales.*`,
`estructurales.*` y `comportamiento.*`: mover los archivos no cambia
su identidad Java.

[Comparar con los patrones liminales](../liminales/README.md) ·
[Guía UML](../../UML_GUIDE.md) · [Inicio](../../README.md).
