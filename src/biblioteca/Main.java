package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ESTACIÓN 6: OBSERVER ===");
        RepositorioReservasMemoria reservas = new RepositorioReservasMemoria();
        ReservaFacade fachada = new ReservaFacade(
                new RepositorioSalasMemoria(), reservas,
                List.of(new NotificadorEstudiante(), new RegistroEventos()));
        Reserva reserva = fachada.realizarReserva(
                new Estudiante("E001", "Laura"), LocalDate.now().plusDays(7),
                LocalTime.of(14, 0), 2, 4, new SeleccionPorCapacidad()).orElseThrow();
        System.out.println("Después de confirmar: " + reserva.getNombreEstado());
        reserva.cancelar();
        System.out.println("Después de cancelar: " + reserva.getNombreEstado());
        System.out.println("Reservas registradas: " + reservas.obtenerTodas().size());
    }
}
