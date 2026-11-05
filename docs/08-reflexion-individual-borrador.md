# Reflexiones técnicas — borrador para adaptar con experiencia propia

> Estas respuestas son una base técnica. En la entrega individual deben revisarse y adaptarse a las decisiones y dificultades realmente experimentadas por el estudiante.

1. **Separación de responsabilidades:** el dominio modela estudiantes y salas; Strategy selecciona, Builder valida la creación, Facade coordina, Observer informa y State protege el ciclo de vida. Una clase única produciría baja cohesión y sería difícil de probar.
2. **Builder frente a constructor extenso:** el encadenamiento explicita cada dato y evita errores de orden; permite validar una sola vez antes de crear el objeto.
3. **Nueva estrategia sin tocar la fachada:** una clase nueva que implemente `EstrategiaSeleccionSala` acepta las salas disponibles y el número de personas, y retorna un `Optional<Sala>`; `ReservaFacade` continúa usando la misma abstracción.
4. **Facade:** reduce a una operación la consulta de repositorio, selección, construcción, almacenamiento y confirmación; evita que Main conozca el orden de coordinación.
5. **Observer:** `Reserva` depende del contrato `ObservadorReserva` y no de clases como `NotificadorEstudiante`, lo que permite agregar otro suscriptor sin cambiar Reserva.
6. **State:** las transiciones dependen del estado como objeto, no de asignar una cadena que permita cambios ilegales. Un estado terminal rechaza operaciones sin notificar.
7. **Dos principios SOLID:** SRP está en Builder, estrategias y observadores con una responsabilidad; DIP está en la fachada, que usa `RepositorioSalas` y `RepositorioReservas`.
8. **Antipatrón evitado:** el God Object se previene moviendo cada responsabilidad desde `Main` hacia clases independientes.
9. **Límite de disponibilidad:** como las cuatro salas son fijas, no hay detección de conflictos de horario. Cambiaría la implementación de `RepositorioSalas` para consultar disponibilidad real y agregaría persistencia a `RepositorioReservas`.

## Conclusión técnica (modelo, revisar antes de entregar)

El desarrollo incremental de un prototipo de reservas de biblioteca permite comparar una solución monolítica con otra organizada mediante patrones de diseño. Builder hizo explícita la construcción de reservas; Strategy separó los criterios de elección; Facade coordinó el caso de uso; Observer desacopló las notificaciones y State impidió cambios inválidos de ciclo de vida. La separación facilitó las pruebas independientes P01–P11 y permitió incorporar comportamientos nuevos sin introducir un gran bloque de condicionales. La principal limitación actual es la disponibilidad simulada sin prevención de solapamientos ni almacenamiento persistente. Si el sistema continuara, priorizaría un repositorio real con validación de franjas horarias, conservando las interfaces existentes para reducir el impacto del cambio.
