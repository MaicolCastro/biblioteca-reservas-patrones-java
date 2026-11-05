# Ejecución de la guía en Windows PowerShell, Java 17+ sin Maven ni Gradle.
$ErrorActionPreference = 'Stop'
Set-Location (Split-Path -Parent $PSScriptRoot)
if (-not (Get-Command javac -ErrorAction SilentlyContinue)) { throw 'JDK no disponible en PATH (javac).' }
New-Item -ItemType Directory -Force -Path '.\out' | Out-Null
$fuentes = @(Get-ChildItem '.\src\biblioteca\*.java' | ForEach-Object { $_.FullName })
& javac -encoding UTF-8 -d out @fuentes
if ($LASTEXITCODE -ne 0) { throw 'Compilación de producción fallida.' }
$pruebas = @(Get-ChildItem '.\tests\biblioteca\*.java' | ForEach-Object { $_.FullName })
& javac -encoding UTF-8 -cp out -d out @pruebas
if ($LASTEXITCODE -ne 0) { throw 'Compilación de pruebas fallida.' }
Write-Host "`n===== APLICACIÓN COMPLETA ====="
& java -Dfile.encoding=UTF-8 -cp out biblioteca.Main
if ($LASTEXITCODE -ne 0) { throw 'Ejecución de Main fallida.' }
Write-Host "`n===== PRUEBAS P01-P11 ====="
& java -Dfile.encoding=UTF-8 -cp out biblioteca.PruebasBiblioteca
if ($LASTEXITCODE -ne 0) { throw 'Pruebas obligatorias fallidas.' }
