# Análisis de Responsabilidades del Sistema de Reservas

## 1. Responsabilidades Separadas de la Clase Main

Para evitar una arquitectura monolítica y frágil, se han identificado y extraído al menos cinco responsabilidades fundamentales fuera del punto de entrada (`Main`):

1. **Consulta y Gestión de Disponibilidad de Salas (Persistencia/Catálogo)**:
   - *Responsable*: `RepositorioSalas` / `RepositorioSalasMemoria`.
   - *Propósito*: Consultar las salas físicas registradas y su capacidad sin que el cliente conozca la fuente de datos subyacente (memoria, base de datos relacional o servicio REST).

2. **Criterio Algorítmico de Selección de Espacios (Estrategia)**:
   - *Responsable*: `EstrategiaSeleccionSala` (`SeleccionPorCapacidad`, `SeleccionSalaPreferida`).
   - *Propósito*: Encapsular el algoritmo que analiza la lista de salas y selecciona la idónea según la cantidad de asistentes o preferencia explícita.

3. **Construcción y Validación de Integridad de la Reserva (Construcción)**:
   - *Responsable*: `ReservaBuilder`.
   - *Propósito*: Validar precondiciones de dominio (presencia de estudiante, fechas no pasadas, duraciones estrictamente positivas y capacidad suficiente de la sala asignada) antes de instanciar un objeto `Reserva` inmutable y coherente.

4. **Coordinación y Orquestación del Caso de Uso (Fachada)**:
   - *Responsable*: `ReservaFacade`.
   - *Propósito*: Simplificar la interacción del cliente con el subsistema; orquesta la consulta de salas, aplicación de estrategia, invocación del builder, suscripción de observadores, persistencia y confirmación.

5. **Control de Transiciones del Ciclo de Vida (Máquina de Estados)**:
   - *Responsable*: `EstadoReserva` (`Pendiente`, `Confirmada`, `Cancelada`, `Finalizada`).
   - *Propósito*: Centralizar y encapsular qué operaciones son válidas en cada fase del ciclo de vida de la reserva, impidiendo estados inconsistentes sin ensuciar la entidad con cadenas de `if/else`.

6. **Difusión Desacoplada de Eventos a Interesados (Notificaciones)**:
   - *Responsable*: `ObservadorReserva` (`NotificadorEstudiante`, `RegistroEventos`).
   - *Propósito*: Informar a múltiples destinatarios de los cambios de estado sin que la entidad `Reserva` conozca detalles de canales de salida (consola, correo, SMS, auditoría).

---

## 2. Consecuencias de Concentrar Todo en `Main` (Antipatrón God Object)

Si la clase `Main` asumiera directamente la consulta de salas, validación de campos, selección de sala, control de transiciones y envío de notificaciones:

- **Baja Cohesión**: `Main` tendría múltiples motivos para cambiar (un cambio en el formato de fecha, un cambio en la regla de selección de sala, un nuevo canal de notificación o una nueva regla de estado obligarían a modificar la misma clase).
- **Alto Acoplamiento**: El punto de entrada estaría fuertemente vinculado a los detalles técnicos de cada etapa (estructuras de datos, mensajes de consola específicos, algoritmos de ordenamiento), impidiendo reutilizar la lógica en interfaces web, API REST o pruebas automatizadas.
- **Riesgo de Antipatrón God Object / Spaghetti Code**: La clase se convertiría en un bloque monolítico con condicionales anidados difíciles de depurar, con duplicación de validaciones y alta propensión a introducir regresiones con cada modificación menor.
