package biblioteca;

/** Estado confirmado provisional. Las demás transiciones se completan en State (estación 7). */
public class Confirmada implements EstadoReserva {
    @Override
    public String nombre() { return "Confirmada"; }

    @Override
    public void confirmar(Reserva reserva) {
        throw new IllegalStateException("La reserva ya está confirmada.");
    }

    @Override
    public void cancelar(Reserva reserva) {
        throw new IllegalStateException("Cancelación pendiente de integrar en la estación 6.");
    }

    @Override
    public void finalizar(Reserva reserva) {
        throw new IllegalStateException("Finalización pendiente de integrar en la estación 7.");
    }
}
