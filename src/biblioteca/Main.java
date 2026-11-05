package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ESTACIÓN 7: STATE ===");
        ReservaFacade fachada = new ReservaFacade(new RepositorioSalasMemoria(),
                new RepositorioReservasMemoria(),
                List.of(new NotificadorEstudiante(), new RegistroEventos()));
        Reserva primera = fachada.realizarReserva(
                new Estudiante("E001", "Laura"), LocalDate.now().plusDays(7),
                LocalTime.of(14, 0), 2, 4, new SeleccionPorCapacidad()).orElseThrow();
        primera.cancelar();
        System.out.println("Después de cancelar: " + primera.getNombreEstado());
        try {
            primera.confirmar();
            throw new AssertionError("La confirmación de una cancelada debería rechazarse.");
        } catch (IllegalStateException ex) {
            System.out.println("Rechazo esperado: " + ex.getMessage());
        }
        Reserva segunda = fachada.realizarReserva(
                new Estudiante("E004", "Samuel"), LocalDate.now().plusDays(10),
                LocalTime.of(11, 0), 1, 2, new SeleccionPorCapacidad()).orElseThrow();
        segunda.finalizar();
        System.out.println("Después de finalizar: " + segunda.getNombreEstado());
        try {
            segunda.cancelar();
            throw new AssertionError("La cancelación de una finalizada debería rechazarse.");
        } catch (IllegalStateException ex) {
            System.out.println("Rechazo esperado: " + ex.getMessage());
        }
    }
}
