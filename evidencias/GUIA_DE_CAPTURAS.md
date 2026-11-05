# Guía Maestra de Capturas de Pantalla para el Informe de Evidencias

Esta guía detalla los pasos exactos para que el estudiante tome manualmente las capturas de pantalla de cada una de las 8 estaciones.

> [!IMPORTANT]
> **REGLA DE ORO DE CADENA DE CUSTODIA**:
> Cada estación tiene su propio estado de archivos y ramas en Git. Si deseas tomar las capturas después de terminar todo el desarrollo, cambia de rama usando:
> ```powershell
> git switch estacion-0N-nombre
> ```
> Asegúrate de que `git status` reporte el árbol limpio antes de cambiar de rama. **Nunca mezcles archivos de una rama con el estado de otra.**

---

## Nomenclatura Estándar de Archivos de Evidencia
Guarda las capturas en formato PNG en la carpeta `evidencias/` con la siguiente convención:
- `E0X-01-codigo.png` : Editor VS Code mostrando el explorador de archivos con la rama activa y el código fuente representativo.
- `E0X-02-consola.png` : Terminal mostrando la compilación (`javac`) y la ejecución (`java`) o visualización del artefacto.
- `E0X-03-git.png` : Terminal mostrando comandos Git de verificación (`git branch --show-current`, `git log -1 --oneline`, `git status`).
- `E0X-04-github.png` : Navegador web en GitHub mostrando el repositorio, la rama seleccionada en el desplegable de branches, el último commit y su hash.

---

## Detalle por Estación

### Estación 1: Comprender el problema y modelar el flujo
- **Rama**: `estacion-01-modelado`
- `E01-01-codigo.png`: VS Code mostrando `docs/01-diagrama-flujo.md` y `docs/01-responsabilidades.md` abiertos en el editor.
- `E01-02-diagrama.png`: Previsualización Markdown del diagrama de flujo Mermaid (o visor de Mermaid).
- `E01-03-git.png`: Terminal con:
  ```powershell
  git branch --show-current
  git log -1 --oneline
  git status
  ```
- `E01-04-github.png`: GitHub en la rama `estacion-01-modelado`, mostrando el commit `docs(estacion-01): modelar flujo y responsabilidades`.

### Estación 2: Crear el dominio y primera ejecución
- **Rama**: `estacion-02-dominio`
- `E02-01-codigo.png`: VS Code con `src/biblioteca/Estudiante.java`, `Sala.java` y `Main.java`.
- `E02-02-consola.png`: Terminal mostrando la compilación y ejecución de `Main.java` con las 4 salas impresas:
  ```powershell
  javac -encoding UTF-8 -d out (Get-ChildItem .\src\biblioteca\*.java | ForEach-Object { $_.FullName })
  java -cp out biblioteca.Main
  ```
- `E02-03-git.png`: Terminal con verificación de rama `estacion-02-dominio`, `git log -1 --oneline` y `git status`.
- `E02-04-github.png`: GitHub en la rama `estacion-02-dominio` con el commit `feat(estacion-02): implementar dominio inicial`.

### Estación 3: Construir reservas con Builder
- **Rama**: `estacion-03-builder`
- `E03-01-codigo.png`: VS Code con `ReservaBuilder.java` y `Reserva.java`.
- `E03-02-consola.png`: Terminal mostrando la prueba de construcción válida y las excepciones por datos inválidos.
- `E03-03-git.png`: Terminal con verificación de rama `estacion-03-builder` y último commit.
- `E03-04-github.png`: GitHub en la rama `estacion-03-builder` con el commit `feat(estacion-03): construir reservas con builder`.

### Estación 4: Seleccionar salas con Strategy
- **Rama**: `estacion-04-strategy`
- `E04-01-codigo.png`: VS Code con `EstrategiaSeleccionSala.java`, `SeleccionPorCapacidad.java` y `SeleccionSalaPreferida.java`.
- `E04-02-consola.png`: Terminal mostrando las 3 salidas del algoritmo de selección (Capacidad para 4 -> Sala B, Preferida C -> Sala C, Preferida B para 7 -> null).
- `E04-03-git.png`: Terminal con verificación de rama `estacion-04-strategy` y último commit.
- `E04-04-github.png`: GitHub en la rama `estacion-04-strategy` con el commit `feat(estacion-04): aplicar estrategia de seleccion`.

### Estación 5: Separar el acceso a datos y coordinar con Facade
- **Rama**: `estacion-05-facade`
- `E05-01-codigo.png`: VS Code con `ReservaFacade.java` y las interfaces e implementaciones de repositorio.
- `E05-02-consola.png`: Terminal con la ejecución del caso de uso mediante la fachada.
- `E05-03-git.png`: Terminal con verificación de rama `estacion-05-facade` y último commit.
- `E05-04-github.png`: GitHub en la rama `estacion-05-facade` con el commit `feat(estacion-05): integrar repositorios y fachada`.

### Estación 6: Notificar eventos con Observer
- **Rama**: `estacion-06-observer`
- `E06-01-codigo.png`: VS Code con `ObservadorReserva.java`, `NotificadorEstudiante.java`, `RegistroEventos.java` y la actualización en `Reserva.java`.
- `E06-02-consola.png`: Terminal mostrando la confirmación y cancelación emitiendo los dos mensajes respectivos («Notificación a...» y «Evento registrado...»).
- `E06-03-git.png`: Terminal con verificación de rama `estacion-06-observer` y último commit.
- `E06-04-github.png`: GitHub en la rama `estacion-06-observer` con el commit `feat(estacion-06): notificar cambios con observer`.

### Estación 7: Controlar el ciclo de vida con State
- **Rama**: `estacion-07-state`
- `E07-01-codigo.png`: VS Code con `EstadoReserva.java`, `Pendiente.java`, `Confirmada.java`, `Cancelada.java` y `Finalizada.java`.
- `E07-02-consola.png`: Terminal mostrando las transiciones válidas y el rechazo con `IllegalStateException` sin emisión de notificaciones.
- `E07-03-git.png`: Terminal con verificación de rama `estacion-07-state` y último commit.
- `E07-04-github.png`: GitHub en la rama `estacion-07-state` con el commit `feat(estacion-07): controlar transiciones con state`.

### Estación 8: Integrar y ejecutar la aplicación completa
- **Rama**: `estacion-08-integracion` (y posteriormente `main` integrada)
- `E08-01-codigo.png`: VS Code con el árbol final de los 21 archivos Java en `src/biblioteca/` y la suite de pruebas en `tests/biblioteca/`.
- `E08-02-consola-main.png`: Terminal con la ejecución de `Main.java` mostrando los 4 escenarios de la guía (Laura, Andrés, Camila sin sala, Samuel finalizado).
- `E08-03-consola-pruebas.png`: Terminal con la ejecución exitosa de la suite de pruebas completa `PruebasBiblioteca.java` (P01 a P11 todas con estatus APROBADA).
- `E08-04-git.png`: Terminal con `git log --graph --oneline --all` mostrando el historial lineal acumulativo de las 8 ramas.
- `E08-05-github.png`: GitHub mostrando el repositorio con las 8 ramas publicadas y `main` integrada al día.
