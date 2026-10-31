#!/usr/bin/env bash
# Verificación reproducible de ambos microproyectos. Ejecutar con:
# bash scripts/verificar.sh
set -euo pipefail

raiz_repositorio="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$raiz_repositorio"

for comando in java javac mvn; do
    if ! command -v "$comando" >/dev/null 2>&1; then
        printf 'ERROR: no se encuentra %s en PATH\n' "$comando" >&2
        exit 1
    fi
done

version_compilador="$(javac -version 2>&1)"
if [[ ! "$version_compilador" =~ ^javac[[:space:]]21([[:space:].]|$) ]]; then
    printf 'ERROR: se requiere JDK 21; detectado: %s\n' "$version_compilador" >&2
    exit 1
fi
if ! mvn -version | grep -Eq 'Java version:[[:space:]]*21([.]|$)'; then
    printf 'ERROR: Maven debe utilizar Java 21; revisa JAVA_HOME y mvn -version\n' >&2
    exit 1
fi

printf '\n[1/4] Arena Java: compilar, probar y empaquetar\n'
mvn -B -ntp -f proyectos/arena-java/pom.xml clean verify

printf '\n[2/4] MiniSO: compilar, probar y empaquetar\n'
mvn -B -ntp -f proyectos/miniso/pom.xml clean verify

printf '\n[3/4] Smoke test: arrancar JAR de Arena Java\n'
salida_arena="$(printf '0\n' | java -jar proyectos/arena-java/target/arena-java-1.0.0-SNAPSHOT.jar)"
if [[ "$salida_arena" != *'=== ARENA JAVA ==='* ]] || [[ "$salida_arena" != *'1 Arena'* ]]; then
    printf 'ERROR: no se observo la pantalla inicial de Arena Java\n' >&2
    exit 1
fi

printf '\n[4/4] Smoke test: planificar y consultar metricas en MiniSO\n'
salida_miniso="$(printf '2\ncrear demo 2 5 4\ntick 2\nestado\nmetricas\ncomparar\nsalir\n' | java -jar proyectos/miniso/target/miniso-1.0.0-SNAPSHOT.jar)"
if [[ "$salida_miniso" != *'PID 1 termino y libero memoria'* ]] \
        || [[ "$salida_miniso" != *'memoria=0/32 KiB'* ]] \
        || [[ "$salida_miniso" != *'RESPUESTA | RETORNO'* ]] \
        || [[ "$salida_miniso" != *'ESPERA MEDIA | RESPUESTA MEDIA'* ]]; then
    printf 'ERROR: el flujo interactivo de MiniSO no produjo lo esperado\n' >&2
    exit 1
fi

printf '\nVERIFICACION COMPLETA: 2 proyectos, 2 suites de pruebas y 2 pruebas de arranque correctas.\n'
