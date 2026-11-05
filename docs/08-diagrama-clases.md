# Estación 8 — Diagrama de clases del sistema de reservas

El siguiente diagrama representa las **21 clases e interfaces de producción** implementadas en el paquete `biblioteca`. Se organizan las responsabilidades por dominios y patrones para facilitar su lectura. La clase `Main` solo crea dependencias e invoca a la fachada.

```mermaid
classDiagram
    direction LR
    class Main
    class Estudiante {
      -String identificador
      -String nombre
      +getNombre() String
    }
    class Sala {
      -String codigo
      -String nombre
      -int capacidad
      +getCapacidad() int
    }
    class Reserva {
      -String codigo
      -Estudiante estudiante
      -Sala sala
      -EstadoReserva estado
      -List~ObservadorReserva~ observadores
      +confirmar() void
      +cancelar() void
      +finalizar() void
      +agregarObservador(ObservadorReserva) void
    }
    class ReservaBuilder {
      +estudiante(Estudiante) ReservaBuilder
      +fecha(LocalDate) ReservaBuilder
      +horaInicio(LocalTime) ReservaBuilder
      +duracionHoras(int) ReservaBuilder
      +cantidadPersonas(int) ReservaBuilder
      +sala(Sala) ReservaBuilder
      +build() Reserva
    }
    class EstrategiaSeleccionSala {
      <<interface>>
      +seleccionar(List~Sala~, int) Optional~Sala~
    }
    class SeleccionPorCapacidad
    class SeleccionSalaPreferida
    class RepositorioSalas {
      <<interface>>
      +obtenerDisponibles() List~Sala~
    }
    class RepositorioReservas {
      <<interface>>
      +guardar(Reserva) void
    }
    class RepositorioSalasMemoria
    class RepositorioReservasMemoria
    class ReservaFacade {
      -RepositorioSalas repositorioSalas
      -RepositorioReservas repositorioReservas
      -List~ObservadorReserva~ observadores
      +realizarReserva(...) Optional~Reserva~
    }
    class ObservadorReserva {
      <<interface>>
      +actualizar(Reserva, String, String) void
    }
    class NotificadorEstudiante
    class RegistroEventos
    class EstadoReserva {
      <<interface>>
      +nombre() String
      +confirmar(Reserva) void
      +cancelar(Reserva) void
      +finalizar(Reserva) void
    }
    class Pendiente
    class Confirmada
    class Cancelada
    class Finalizada

    Main ..> ReservaFacade : configura
    Estudiante <-- Reserva : pertenece a
    Sala <-- Reserva : utiliza
    ReservaBuilder ..> Reserva : construye
    EstrategiaSeleccionSala <|.. SeleccionPorCapacidad
    EstrategiaSeleccionSala <|.. SeleccionSalaPreferida
    RepositorioSalas <|.. RepositorioSalasMemoria
    RepositorioReservas <|.. RepositorioReservasMemoria
    ReservaFacade --> RepositorioSalas : consulta
    ReservaFacade --> RepositorioReservas : registra
    ReservaFacade ..> EstrategiaSeleccionSala : delega selección
    ReservaFacade ..> ReservaBuilder : construye
    ReservaFacade ..> ObservadorReserva : suscribe
    ObservadorReserva <|.. NotificadorEstudiante
    ObservadorReserva <|.. RegistroEventos
    Reserva --> ObservadorReserva : notifica cambios
    Reserva --> EstadoReserva : delega transiciones
    EstadoReserva <|.. Pendiente
    EstadoReserva <|.. Confirmada
    EstadoReserva <|.. Cancelada
    EstadoReserva <|.. Finalizada
```

## Relaciones y cardinalidades principales

- Cada `Reserva` contiene un `Estudiante` y una `Sala`; una misma sala puede estar referenciada por múltiples reservas porque la disponibilidad es simulada.
- Cada `Reserva` tiene exactamente un estado activo, que implementa `EstadoReserva`.
- Cada `Reserva` mantiene cero o más observadores; en el flujo principal se registran dos.
- `ReservaFacade` se construye con un repositorio de salas, un repositorio de reservas y una lista de observadores; la estrategia se selecciona por llamada.
- El dominio de la práctica no incluye persistencia, base de datos ni prevención de horarios solapados.
