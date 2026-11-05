package biblioteca;

/**
 * Contrato de comportamiento para los estados del ciclo de vida de una reserva.
 * Implementación de andamiaje temporal para habilitar la compilación incremental
 * de Reserva en la Estación 3. Se completa formalmente en la Estación 7.
 */
public interface EstadoReserva {
    String nombre();
    void confirmar(Reserva reserva);
    void cancelar(Reserva reserva);
    void finalizar(Reserva reserva);
}
