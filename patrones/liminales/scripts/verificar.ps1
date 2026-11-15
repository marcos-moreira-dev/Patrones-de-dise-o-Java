# PowerShell: powershell -ExecutionPolicy Bypass -File .\colecciones\liminal\scripts\verificar.ps1
$ErrorActionPreference = 'Stop'
$raiz = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
Push-Location $raiz
try {
    $version = (& javac -version 2>&1 | Out-String).Trim()
    if ($LASTEXITCODE -ne 0 -or $version -notmatch '^javac 21(\.|$)') {
        throw "Se requiere JDK 21. Detectado: $version"
    }
    New-Item -ItemType Directory -Force out | Out-Null
    $fuentes = @(Get-ChildItem src/main/java, src/test/java -Recurse -Filter *.java | ForEach-Object FullName)
    if ($fuentes.Count -eq 0) { throw 'No se encontraron archivos Java' }
    & javac --release 21 -encoding UTF-8 -d out @fuentes
    if ($LASTEXITCODE -ne 0) { throw 'Falló javac' }
    & java -cp out liminal.PruebasLiminal
    if ($LASTEXITCODE -ne 0) { throw 'Fallaron las pruebas' }
    & java -cp out liminal.DemoLiminal todos
    if ($LASTEXITCODE -ne 0) { throw 'Falló la demostración' }
    & java -cp out liminal.laboratorios.relaciones.ClienteRelacionesUml
    if ($LASTEXITCODE -ne 0) { throw 'Falló el laboratorio UML' }
    Write-Host 'OK: 23 patrones GoF, laboratorio UML y salida por consola.'
} finally {
    Pop-Location
}
