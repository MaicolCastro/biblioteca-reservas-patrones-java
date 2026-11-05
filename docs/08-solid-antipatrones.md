# Decisiones de diseño, cinco patrones y principios SOLID

## Aplicación de los patrones

**Builder — `ReservaBuilder`.** Sustituye un constructor público complejo por una secuencia expresiva de métodos encadenados; `build()` concentra las validaciones antes de construir la `Reserva`. Evita que `Main` dependa del orden posicional de todos los argumentos.

**Strategy — `EstrategiaSeleccionSala`.** `SeleccionPorCapacidad` encuentra la menor capacidad admisible y `SeleccionSalaPreferida` exige coincidencia de código y capacidad. `ReservaFacade` recibe la interfaz y puede intercambiar algoritmos sin editar su lógica.

**Facade — `ReservaFacade`.** Resume en `realizarReserva` la consulta a salas, la selección, el Builder, el registro y la confirmación. `Main` no controla la secuencia interna ni depende de repositorios concretos.

**Observer — `ObservadorReserva`.** `Reserva` mantiene una lista de suscriptores y les comunica cada cambio de estado efectivo. `NotificadorEstudiante` y `RegistroEventos` implementan comportamientos independientes. Se utiliza una copia de la lista al publicar para evitar modificaciones durante la iteración.

**State — `EstadoReserva`.** `Pendiente`, `Confirmada`, `Cancelada` y `Finalizada` encapsulan transiciones válidas y rechazos claros. El estado solo se actualiza por una operación admitida; en transiciones inválidas se lanza una excepción antes de notificar.

## Justificación SOLID

| Principio | Evidencia en el código | Beneficio |
|---|---|---|
| SRP | `ReservaBuilder` construye; `SeleccionPorCapacidad` decide; `RegistroEventos` comunica; cada estado valida sus propias operaciones. | Alta cohesión y cambios localizados. |
| OCP | Una nueva estrategia implementa `EstrategiaSeleccionSala` sin modificar `ReservaFacade`; un nuevo observador implementa `ObservadorReserva`. | Extensión sin alterar los clientes existentes. |
| LSP | Ambas estrategias producen `Optional<Sala>` con el mismo significado; implementaciones de repositorios cumplen sus interfaces. | Intercambio seguro de implementaciones. |
| ISP | `RepositorioSalas`, `RepositorioReservas`, `ObservadorReserva` y `EstrategiaSeleccionSala` son interfaces separadas y específicas. | Ninguna clase debe implementar métodos ajenos a su responsabilidad. |
| DIP | `ReservaFacade` recibe `RepositorioSalas` y `RepositorioReservas` mediante el constructor; `Reserva` conoce la abstracción `ObservadorReserva`. | Menor acoplamiento con detalles de infraestructura. |

## Antipatrones evitados

- **God Object:** `Main` no construye manualmente reservas, selecciona salas ni gestiona estados.
- **Long Parameter List:** se usa Builder para la construcción compleja de `Reserva`; los datos del caso de uso siguen explícitos en la interfaz de la fachada tal como exige la guía.
- **Primitive Obsession:** el estado no se almacena como una cadena mutable sino como un objeto `EstadoReserva`.
- **Shotgun Surgery:** agregar `SeleccionPorEquipamiento` exigiría un archivo nuevo que implemente la interfaz, sin alterar la fachada.
- **Tight Coupling:** la reserva no crea observadores concretos; la fachada no crea sus propios repositorios.
- **Duplicate Code:** las validaciones estructurales de creación permanecen en `ReservaBuilder` y la regla de selección se encapsula en cada estrategia.
- **Swallowing Exceptions:** `Main` informa el rechazo de una transición inválida en el escenario académico.
- **Premature Optimization:** no se añaden frameworks, persistencia ni interfaz gráfica no solicitados.

## Limitaciones reconocidas

1. La disponibilidad usa una lista fija de cuatro salas, no un calendario.
2. No se revisan coincidencias de horario; una misma sala puede aparecer en varias reservas simultáneas.
3. Los datos solo viven en memoria y se pierden al finalizar la ejecución.
4. La notificación se representa mediante mensajes de consola, no SMS ni correo.
5. El identificador es un prefijo aleatorio de UUID adecuado para un prototipo académico, pero no garantiza unicidad absoluta a gran escala.

**Mejora futura concreta:** implementar `RepositorioSalas` y `RepositorioReservas` con persistencia y consulta de solapamientos horarios, preservando las interfaces para no cambiar la fachada.
