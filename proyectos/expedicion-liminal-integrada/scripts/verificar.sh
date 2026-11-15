#!/usr/bin/env bash
# Compilación y pruebas del proyecto integrado, independientes de las demos GoF.
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
java -cp out liminal.integrado.PruebasExpedicion
salida="$(java -cp out liminal.integrado.presentacion.ClienteExpedicion)"
for esperado in 'RECHAZADO' 'ACEPTADO' 'H-05' 'turnos=4' 'FINALIZADA'; do
    if [[ "$salida" != *"$esperado"* ]]; then
        echo "ERROR: falta el fragmento de consola: $esperado" >&2
        exit 1
    fi
done
printf 'OK: Expedición Liminal Integrada (etapa 1), Java 21.\n'

