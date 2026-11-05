# BibliotecaReservas — Laboratorio de Patrones de Diseño en Java

Prototipo académico de consola desarrollado en **Java 17 o superior**, sin base de datos ni dependencias externas, que aplica cinco patrones: **Builder, Strategy, Facade, Observer y State**.

## Estructura

```text
src/biblioteca/                 21 clases e interfaces de producción
tests/biblioteca/              Pruebas académicas P01–P11 en Java estándar
 docs/                          Diagramas, matriz, SOLID y reflexiones
 evidencias/                    Instrucciones para capturas auténticas
 scripts/ejecutar-y-probar.ps1  Compila y ejecuta Main + pruebas (Windows)
 scripts/publicar-ramas.ps1     Publica las ramas existentes en GitHub (con verificación)
```

## Ejecución en VS Code / PowerShell (Windows)

Abra la terminal PowerShell en la **raíz del proyecto**.

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem .\src\biblioteca\*.java | ForEach-Object { $_.FullName })
java -Dfile.encoding=UTF-8 -cp out biblioteca.Main
javac -encoding UTF-8 -cp out -d out (Get-ChildItem .\tests\biblioteca\*.java | ForEach-Object { $_.FullName })
java -Dfile.encoding=UTF-8 -cp out biblioteca.PruebasBiblioteca
```

O ejecute directamente el script de ejecución:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\ejecutar-y-probar.ps1
```

El programa debe ejecutar el escenario con Laura, Andrés, Camila (sin sala) y Samuel, y las pruebas deben terminar con **11 APROBADAS / 0 FALLIDAS**. Los códigos de reserva son aleatorios y las fechas dependen de la fecha actual.

## Ramas y commits acumulativos

Las ocho ramas se derivan una de otra y preservan el historial inicial de `main`.

1. `estacion-01-modelado` — Análisis, responsabilidades y diagrama de flujo.
2. `estacion-02-dominio` — Estudiante, Sala y Main inicial.
3. `estacion-03-builder` — Reserva, ReservaBuilder y validaciones.
4. `estacion-04-strategy` — Estrategias de selección.
5. `estacion-05-facade` — Repositorios y fachada.
6. `estacion-06-observer` — Suscriptores y eventos.
7. `estacion-07-state` — Transiciones y rechazo de estados inválidos.
8. `estacion-08-integracion` — Main final, pruebas P01–P11 y documentación técnica.

**Importante:** el ZIP de recuperación conserva los commits localmente, pero los commits nuevos solo aparecerán en GitHub después de realizar el `push` desde un equipo autenticado. Ningún resultado de prueba equivale por sí mismo a una captura de pantalla.

## Diseño y limitaciones

Consulte el diagrama de flujo en `docs/01-diagrama-flujo.md`, el de clases en `docs/08-diagrama-clases.md`, la matriz de trazabilidad en `docs/08-matriz-trazabilidad.md`, el análisis de patrones, SOLID y antipatrones en `docs/08-solid-antipatrones.md`, y la guía de capturas en `evidencias/GUIA_DE_CAPTURAS.md`.

La disponibilidad es simulada; no se controlan conflictos de horario ni existe persistencia entre ejecuciones. La notificación ocurre por consola. No hay interfaz gráfica ni base de datos, de acuerdo con el alcance de la guía.

## Evidencias individuales

Realice capturas de cada rama, sus archivos Java, la salida de la consola y el commit real en GitHub. En `evidencias/GUIA_DE_CAPTURAS.md` se explica qué mostrar para las ocho estaciones; las capturas no están incluidas porque deben corresponder a una ejecución real del estudiante.
