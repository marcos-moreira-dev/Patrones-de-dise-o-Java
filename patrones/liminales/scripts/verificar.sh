#!/usr/bin/env bash
# Verifica 23 patrones GoF, el laboratorio UML y la documentación con Java 21.
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
for esperado in 'STATE' 'STRATEGY' 'OBSERVER' 'Avisos:' 'Monstruo silencioso' 'ABSTRACT FACTORY' 'Original:' 'ADAPTER' 'BRIDGE' 'COMPOSITE' 'DECORATOR' 'FACADE' 'FLYWEIGHT' 'PROXY' 'CHAIN OF RESPONSIBILITY' 'COMMAND' 'INTERPRETER' 'ITERATOR' 'MEDIATOR' 'MEMENTO' 'TEMPLATE METHOD' 'VISITOR' 'Puerta P-01' 'Patrullar' 'Luz -> ENCENDIDA'; do
    if [[ "$salida" != *"$esperado"* ]]; then
        echo "ERROR: falta la salida: $esperado" >&2
        exit 1
    fi
done
laboratorio="$(java -cp out liminal.laboratorios.relaciones.ClienteRelacionesUml)"
# Comprobar fragmentos ASCII: la consola de Windows puede recodificar las tildes.
for esperado in 'ASOCIACI' 'AGREGACI' 'COMPOSICI' 'CIERRE:' 'ENTRADA activa: false'; do
    if [[ "$laboratorio" != *"$esperado"* ]]; then
        echo "ERROR: falta la relación UML en consola: $esperado" >&2
        exit 1
    fi
done
printf 'OK: 23 patrones GoF, laboratorio UML, documentación y consola.\n'
