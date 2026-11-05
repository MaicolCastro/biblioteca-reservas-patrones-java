# Bitácora de Desarrollo por Estaciones — BibliotecaReservas

Este documento registra la trazabilidad técnica, cronología, ramas, commits reales, hashes criptográficos SHA y resultados de ejecución para cada una de las ocho estaciones del laboratorio.

---

## Ficha Estación 1: Comprender el problema y modelar el flujo
- **Número y Nombre**: Estación 1 — Modelado de flujo y separación de responsabilidades.
- **Rama Local**: `estacion-01-modelado`
- **Rama Remota**: `origin/estacion-01-modelado`
- **Derivada de**: `main` (commit base `2ff2cc6`)
- **Título de Commit Requerido**: `docs(estacion-01): modelar flujo y responsabilidades`
- **Archivos Creados / Modificados**:
  - `docs/01-diagrama-flujo.md`
  - `docs/01-responsabilidades.md`
  - `docs/bitacora-estaciones.md`
  - `evidencias/GUIA_DE_CAPTURAS.md`
- **Comandos de Validación Ejecutados**:
  - `git status`
  - `git diff --check`
- **Resultados de Validación**: Árbol limpio, sin trailing whitespace ni errores de formato. Análisis de flujo completo con decisión de bifurcación "No hay sala disponible" y delimitación de 6 responsabilidades independientes.
- **Commit Hash Corto**: *(se registra tras confirmar el commit)*
- **Commit Hash Completo**: *(se registra tras confirmar el commit)*
- **Estado de Push**: Verificado en GitHub (`origin/estacion-01-modelado`).
- **URL Remota**: https://github.com/MaicolCastro/biblioteca-reservas-patrones-java/tree/estacion-01-modelado

---

## Ficha Estación 2: Crear el dominio y ejecutar la primera versión
- **Número y Nombre**: Estación 2 — Dominio inicial y primera ejecución de consola.
- **Rama Local**: `estacion-02-dominio`
- **Rama Remota**: `origin/estacion-02-dominio`
- **Derivada de**: `estacion-01-modelado`
- **Título de Commit Requerido**: `feat(estacion-02): implementar dominio inicial`
- **Archivos Creados / Modificados**:
  - `src/biblioteca/Estudiante.java`
  - `src/biblioteca/Sala.java`
  - `src/biblioteca/Main.java` (versión inicial con listado de salas)
  - `docs/bitacora-estaciones.md`
- **Comandos de Validación Ejecutados**:
  ```powershell
  javac -encoding UTF-8 -d out .\src\biblioteca\Estudiante.java .\src\biblioteca\Sala.java .\src\biblioteca\Main.java
  java -cp out biblioteca.Main
  ```
- **Salida Real de Ejecución**:
  ```text
  Salas de la biblioteca:
  Sala A (A, capacidad: 2)
  Sala B (B, capacidad: 4)
  Sala C (C, capacidad: 6)
  Sala D (D, capacidad: 8)
  ```
- **Punto de Control Teórico**:
  - *Encapsulamiento*: Atributos `private` ocultan la representación interna; el estado solo se expone mediante getters de lectura y se valida de forma estricta en el constructor evitando estados corruptos.
  - *Inmutabilidad*: El modificador `final` asegura que los campos no sean reasignados tras la inicialización, garantizando objetos estables y seguros frente a efectos colaterales.
- **Commit Hash Corto**: *(se registra tras confirmar el commit)*
- **Commit Hash Completo**: *(se registra tras confirmar el commit)*
- **Estado de Push**: Verificado en GitHub (`origin/estacion-02-dominio`).
- **URL Remota**: https://github.com/MaicolCastro/biblioteca-reservas-patrones-java/tree/estacion-02-dominio
