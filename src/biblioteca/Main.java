package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ESTACIÓN 3: PRUEBAS DE BUILDER ===");

        // 1. Prueba de construcción válida
        System.out.println("--- 1. Construcción válida ---");
        Reserva reserva = new ReservaBuilder()
                .estudiante(new Estudiante("E001", "Laura"))
                .fecha(LocalDate.now().plusDays(7))
                .horaInicio(LocalTime.of(14, 0))
                .duracionHoras(2)
                .cantidadPersonas(4)
                .sala(new Sala("B", "Sala B", 4))
                .build();
        System.out.println(reserva);

        // 2. Prueba negativa: estudiante omitido
        System.out.println("\n--- 2. Prueba negativa: estudiante omitido ---");
        try {
            new ReservaBuilder()
                    .fecha(LocalDate.now().plusDays(7))
                    .horaInicio(LocalTime.of(14, 0))
                    .duracionHoras(2)
                    .cantidadPersonas(4)
                    .sala(new Sala("B", "Sala B", 4))
                    .build();
            System.out.println("ERROR: No debió permitir la construcción.");
        } catch (IllegalStateException ex) {
            System.out.println("Rechazo esperado por estudiante omitido: " + ex.getMessage());
        }

        // 3. Prueba negativa: capacidad de sala insuficiente
        System.out.println("\n--- 3. Prueba negativa: capacidad insuficiente ---");
        try {
            new ReservaBuilder()
                    .estudiante(new Estudiante("E001", "Laura"))
                    .fecha(LocalDate.now().plusDays(7))
                    .horaInicio(LocalTime.of(14, 0))
                    .duracionHoras(2)
                    .cantidadPersonas(5)
                    .sala(new Sala("B", "Sala B", 4))
                    .build();
            System.out.println("ERROR: No debió permitir la construcción.");
        } catch (IllegalStateException ex) {
            System.out.println("Rechazo esperado por capacidad insuficiente: " + ex.getMessage());
        }
    }
}
