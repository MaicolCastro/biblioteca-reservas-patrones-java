package biblioteca;

/**
 * Estado inicial de una reserva.
 * Implementación de andamiaje temporal para habilitar la compilación incremental
 * de Reserva en la Estación 3. Se sustituye por la versión definitiva en la Estación 7.
 */
public class Pendiente implements EstadoReserva {
    @Override
    public String nombre() {
        return "Pendiente";
    }

    @Override
    public void confirmar(Reserva reserva) {
        // En estación 7 se integra con la transición formal a Confirmada
    }

    @Override
    public void cancelar(Reserva reserva) {
        // En estación 7 se integra con la transición formal a Cancelada
    }

    @Override
    public void finalizar(Reserva reserva) {
        throw new IllegalStateException("No se puede finalizar una reserva pendiente.");
    }
}
