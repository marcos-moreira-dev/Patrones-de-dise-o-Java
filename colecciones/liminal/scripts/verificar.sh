#!/usr/bin/env bash
# Verifica el piloto con Java 21 y sin frameworks.
set -euo pipefail
raiz="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$raiz"
if ! javac -version 2>&1 | grep -Eq '^javac 21([.]|$)'; then
    echo 'ERROR: se requiere JDK 21.' >&2
    exit 1
fi
mkdir -p out
mapfile -d '' fuentes < <(find src/main/java src/test/java -type f -name '*.java' -print0)
javac --release 21 -encoding UTF-8 -d out "${fuentes[@]}"
java -cp out liminal.PruebasLiminal
salida="$(java -cp out liminal.DemoLiminal todos)"
for esperado in 'STATE' 'STRATEGY' 'OBSERVER' 'Avisos:' 'Monstruo silencioso' 'ABSTRACT FACTORY' 'Original:' 'Puerta P-01' 'Patrullar' 'Luz -> ENCENDIDA'; do
    if [[ "$salida" != *"$esperado"* ]]; then
        echo "ERROR: falta la salida: $esperado" >&2
        exit 1
    fi
done
printf 'OK: compilación, pruebas y salida por consola.\n'
