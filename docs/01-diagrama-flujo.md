# Diagrama de Flujo del Proceso de Reserva de Biblioteca

## Descripción del Flujo
El proceso modela el ciclo de vida completo de una solicitud de reserva en la biblioteca «Espacio de Estudio», integrando los patrones de diseño requeridos:
1. **Entrada de datos**: Se reciben los datos de la solicitud (Estudiante, Fecha, Hora, Duración, Cantidad de Personas y Estrategia).
2. **Consulta de disponibilidad**: La fachada consulta el catálogo de salas disponibles en el repositorio.
3. **Selección por Estrategia (Strategy)**: Se delega el algoritmo de selección a la estrategia configurada (`SeleccionPorCapacidad` o `SeleccionSalaPreferida`).
4. **Decisión clave («¿Hay sala adecuada?»)**:
   - **NO**: El flujo finaliza inmediatamente retornando un `Optional.empty()` e informando que no existe sala disponible que cumpla los criterios. No se genera ninguna reserva ni se notifica.
   - **SÍ**: Se continúa con la construcción y ciclo de vida.
5. **Construcción y Validación (Builder)**: `ReservaBuilder` valida la integridad de los datos (no nulos, fecha presente o futura, duración > 0, personas > 0, capacidad de sala suficiente) y genera el identificador único (`R-XXXXXXXX`).
6. **Coordinación y Registro (Facade)**: La fachada guarda la reserva en el repositorio y suscribe los observadores.
7. **Control de Estado (State)**: La reserva inicia en `Pendiente`. Al solicitar `confirmar()`, el estado valida la transición legal a `Confirmada`. Si una transición no está permitida, se lanza `IllegalStateException` y se aborta sin cambio de estado.
8. **Notificación de Eventos (Observer)**: Cada cambio de estado efectivo dispara el método `actualizar` de todos los observadores registrados (`NotificadorEstudiante`, `RegistroEventos`). Transiciones fallidas no disparan notificación.
9. **Ciclo posterior de la reserva**: Una reserva confirmada puede pasar a `Cancelada` o `Finalizada` validada por sus respectivos estados.

## Diagrama de Flujo (Mermaid)

```mermaid
flowchart TD
    Inicio([Inicio: Solicitud de Reserva]) --> CapturaDatos[Recepción de datos: Estudiante, Fecha, Hora, Duración, Personas, Estrategia]
    CapturaDatos --> ConsultaSalas[RepositorioSalas: Obtener salas disponibles]
    ConsultaSalas --> AplicarStrategy[Strategy: Evaluar salas según criterio Capacidad / Preferencia]
    AplicarStrategy --> DecidirSala{¿Hay sala adecuada?}

    DecidirSala -- No --> FinSinReserva([Fin: No hay sala disponible - Retorna Optional vacío])

    DecidirSala -- Sí --> ConstruirBuilder[Builder: Validar restricciones y ensamblar objeto Reserva en estado Pendiente]
    ConstruirBuilder --> SuscribirObserver[Facade: Suscribir Observadores NotificadorEstudiante y RegistroEventos]
    SuscribirObserver --> GuardarRepositorio[RepositorioReservas: Guardar reserva]
    GuardarRepositorio --> SolicitarConfirmacion[Solicitar confirmación: reserva.confirmar]

    SolicitarConfirmacion --> ValidarTransicion{State: ¿Transición legal Pendiente -> Confirmada?}
    ValidarTransicion -- No --> LanzarError[Lanzar IllegalStateException sin notificar]
    LanzarError --> FinConError([Fin con Error])

    ValidarTransicion -- Sí --> AplicarEstado[Cambiar estado a Confirmada]
    AplicarEstado --> NotificarObservadores[Observer: Notificar a NotificadorEstudiante y RegistroEventos]
    NotificarObservadores --> ReservaActiva([Reserva Confirmada y Activa])

    ReservaActiva --> AccionPosterior{Acción posterior del estudiante}
    AccionPosterior -- Cancelar --> ValidarCancelacion{State: ¿Transición legal a Cancelada?}
    AccionPosterior -- Finalizar --> ValidarFinalizacion{State: ¿Transición legal a Finalizada?}

    ValidarCancelacion -- Sí --> EstadoCancelada[Cambiar estado a Cancelada]
    EstadoCancelada --> NotificarCancelacion[Observer: Notificar cambio a Cancelada]
    NotificarCancelacion --> FinCancelada([Fin: Reserva Cancelada])

    ValidarFinalizacion -- Sí --> EstadoFinalizada[Cambiar estado a Finalizada]
    EstadoFinalizada --> NotificarFinalizacion[Observer: Notificar cambio a Finalizada]
    NotificarFinalizacion --> FinFinalizada([Fin: Reserva Finalizada])

    ValidarCancelacion -- No --> RechazoTransicion[Lanzar IllegalStateException - No notificar]
    ValidarFinalizacion -- No --> RechazoTransicion
```
