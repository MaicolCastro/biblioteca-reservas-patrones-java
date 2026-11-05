package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ESTACIÓN 5: PRUEBA DE FACADE ===");
        RepositorioSalas salas = new RepositorioSalasMemoria();
        RepositorioReservasMemoria reservas = new RepositorioReservasMemoria();
        ReservaFacade fachada = new ReservaFacade(salas, reservas);
        var resultado = fachada.realizarReserva(
                new Estudiante("E001", "Laura"), LocalDate.now().plusDays(7),
                LocalTime.of(14, 0), 2, 4, new SeleccionPorCapacidad());
        System.out.println("Resultado: " + resultado.orElseThrow());
        System.out.println("Reservas guardadas: " + reservas.obtenerTodas().size());
        if (resultado.orElseThrow().getNombreEstado().equals("Confirmada")
                && reservas.obtenerTodas().size() == 1) {
            System.out.println("P06 APROBADA: reserva registrada y confirmada.");
        } else {
            throw new AssertionError("Fachada no confirmó y guardó la reserva.");
        }
    }
}
