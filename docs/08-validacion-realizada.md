# Informe de validación técnica — Laboratorio completo

Fecha de validación local: 2026-11-04/05 (según zona horaria del entorno de ejecución).

## Verificaciones realizadas en el entorno de recuperación

- Compilación de clases de producción con `javac` (Java 21, compatible con requisito mínimo Java 17): correcta.
- Ejecución de `biblioteca.Main`: correcta; presenta los cuatro escenarios de la guía.
- Compilación de la suite `biblioteca.PruebasBiblioteca` con Java estándar: correcta.
- Ejecución de las pruebas P01 a P11: **11 aprobadas, 0 fallidas**.
- Conteo de archivos de producción: **21 archivos `.java`** en `src/biblioteca/`.
- Verificación de transiciones: `Pendiente→Confirmada`, `Confirmada→Cancelada` y `Confirmada→Finalizada`; operaciones inválidas rechazadas.
- Eventos: se emiten notificaciones únicamente al cambiar realmente el estado.

## Advertencias de trazabilidad

Estos resultados se obtuvieron en el entorno de recuperación local. **No equivalen a una verificación remota de las ramas pendientes de publicación**, ni a capturas de pantalla originales del equipo del estudiante. Las ramas 4 a 8 y la actualización de `main` deben publicarse desde el VS Code autenticado antes de declarar completado el historial de GitHub.

Los códigos de reserva se generan con UUID y serán diferentes en cada ejecución. Las fechas también dependen de `LocalDate.now()`.
