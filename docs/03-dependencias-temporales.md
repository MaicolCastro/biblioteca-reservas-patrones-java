# Documentación de Dependencias Temporales (Andamiaje Estación 3 → Estación 7)

## Contexto y Justificación
En la guía oficial del laboratorio, la clase `Reserva` (introducida en la Estación 3) posee referencias directas al patrón State mediante:
- El atributo `private EstadoReserva estado;`
- La inicialización en el constructor `this.estado = new Pendiente();`
- Métodos delegados: `confirmar()`, `cancelar()`, `finalizar()`, `cambiarEstado(EstadoReserva)` y `getNombreEstado()`.

Sin embargo, el bloque didáctico completo del patrón State se profundiza y formaliza en la **Estación 7**. Para mantener la política de compilación continua, incremental y acumulativa exigida en cada rama sin saltarse la verificación de tipos ni posponer la ejecución del Builder:

1. **Andamiaje Temporal Creado**:
   - `EstadoReserva.java`: Interfaz con las cuatro firmas exactas (`nombre()`, `confirmar(Reserva)`, `cancelar(Reserva)`, `finalizar(Reserva)`).
   - `Pendiente.java`: Clase que implementa `EstadoReserva`, retorna `"Pendiente"` y ofrece stubs no operativos para confirmar/cancelar, mientras que `finalizar()` lanza `IllegalStateException` de acuerdo a la regla de dominio.

2. **Garantías de Diseño**:
   - No se renombraron tipos, paquetes ni métodos.
   - No se anticipó la lógica de transición completa de la Estación 7 ni se inventaron comportamientos ajenos a la guía.
   - En la Estación 7, estas clases provisionales se sustituirán y completarán con el código definitivo oficial del docente, incorporando `Confirmada`, `Cancelada` y `Finalizada`.
