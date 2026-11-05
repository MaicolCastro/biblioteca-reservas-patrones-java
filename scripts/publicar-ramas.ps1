# Publica sin reescritura de historial las 8 ramas locales y main.
# Revisar su contenido ANTES de ejecutarlo. No utiliza --force.
$ErrorActionPreference = 'Stop'
Set-Location (Split-Path -Parent $PSScriptRoot)
$esperado = 'https://github.com/MaicolCastro/biblioteca-reservas-patrones-java.git'
$origin = (git remote get-url origin).Trim()
if ($LASTEXITCODE -ne 0 -or $origin -ne $esperado) {
    throw "El remote origin no coincide con el repositorio esperado: $origin"
}
if ((git status --porcelain).Length -ne 0) {
    throw 'Hay cambios sin guardar. Limpia el árbol de trabajo antes de publicar.'
}
$ramas = @(
    'estacion-01-modelado',
    'estacion-02-dominio',
    'estacion-03-builder',
    'estacion-04-strategy',
    'estacion-05-facade',
    'estacion-06-observer',
    'estacion-07-state',
    'estacion-08-integracion'
)
$anterior = 'main'
foreach ($rama in $ramas) {
    & git rev-parse --verify --quiet "refs/heads/$rama" | Out-Null
    if ($LASTEXITCODE -ne 0) { throw "Falta rama: $rama" }
    # Las ramas se publican conservando los hashes que ya existen.
    Write-Host "`nPublicando $rama" -ForegroundColor Cyan
    & git push -u origin $rama
    if ($LASTEXITCODE -ne 0) { throw "Error en el push de $rama. Detenido sin forzar." }
    & git ls-remote --heads origin $rama
    if ($LASTEXITCODE -ne 0) { throw "No se pudo verificar $rama." }
}
Write-Host "`nPublicando main (avance rápido, sin reescribir historia)" -ForegroundColor Cyan
& git push -u origin main
if ($LASTEXITCODE -ne 0) { throw 'Error publicando main. Detenido sin forzar.' }
Write-Host "`nPublicación completada; verifica las nueve ramas en GitHub." -ForegroundColor Green
