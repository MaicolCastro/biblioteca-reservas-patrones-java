# Matriz de trazabilidad — Requerimientos, implementación y pruebas

| ID | Requerimiento verificable | Archivos que materializan la funcionalidad | Patrón / componente | Prueba |
|---|---|---|---|---|
| RF01 | Registrar estudiante con identificador y nombre | `Estudiante.java` | Dominio y encapsulamiento | P01 (al construir la reserva) |
| RF02 | Representar sala con código, nombre y capacidad | `Sala.java`, `RepositorioSalasMemoria.java` | Dominio | P03–P05 |
| RF03 | Construir reserva válida y generar identificador | `Reserva.java`, `ReservaBuilder.java` | Builder | P01 |
| RF04 | Rechazar construcción sin datos obligatorios | `ReservaBuilder.java` | Builder | P02 |
| RF05 | Elegir sala disponible de menor capacidad suficiente | `SeleccionPorCapacidad.java`, `EstrategiaSeleccionSala.java` | Strategy | P03 |
| RF06 | Elegir sala preferida solo si cumple capacidad | `SeleccionSalaPreferida.java`, `EstrategiaSeleccionSala.java` | Strategy | P04–P05 |
| RF07 | Coordinar consulta, selección, construcción y registro | `ReservaFacade.java`, `RepositorioSalas.java`, `RepositorioReservas.java`, sus implementaciones en memoria | Facade + DIP | P06, P10 |
| RF08 | Notificar confirmación a dos suscriptores | `Reserva.java`, `ObservadorReserva.java`, `NotificadorEstudiante.java`, `RegistroEventos.java` | Observer | P07 |
| RF09 | Permitir confirmada → cancelada y notificar | `Confirmada.java`, `Cancelada.java`, `Reserva.java` | State + Observer | P08 |
| RF10 | Rechazar cancelada → confirmada sin notificar | `Cancelada.java`, `Reserva.java` | State | P09 |
| RF11 | No crear reserva si ninguna sala tiene capacidad suficiente | `SeleccionPorCapacidad.java`, `ReservaFacade.java` | Strategy + Facade | P10 |
| RF12 | Permitir confirmada → finalizada y notificar | `Confirmada.java`, `Finalizada.java`, `Reserva.java` | State + Observer | P11 |

## Regla transversal de validación

`ReservaBuilder` centraliza la comprobación de fecha no pasada, duración positiva, número de personas positivo y capacidad suficiente de la sala; `Strategy` filtra las opciones disponibles según el criterio; `State` impide transiciones no permitidas. Una transición rechazada no invoca `cambiarEstado`, por lo que no notifica observadores.
