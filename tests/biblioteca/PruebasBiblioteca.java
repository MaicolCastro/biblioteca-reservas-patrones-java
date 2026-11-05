package biblioteca;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Pruebas académicas reproducibles sin dependencias externas (P01 a P11). */
public class PruebasBiblioteca {
    private static int aprobadas = 0;
    private static int fallidas = 0;

    @FunctionalInterface
    private interface Comprobacion { void ejecutar() throws Exception; }

    private static void verificar(String id, String descripcion, Comprobacion test) {
        try {
            test.ejecutar();
            aprobadas++;
            System.out.println(id + " APROBADA | " + descripcion);
        } catch (Throwable error) {
            fallidas++;
            System.out.println(id + " FALLIDA  | " + descripcion);
            error.printStackTrace(System.out);
        }
    }

    private static void exigir(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }

    private static void rechaza(Class<? extends Throwable> esperado, Runnable accion) {
        try {
            accion.run();
        } catch (Throwable error) {
            exigir(esperado.isInstance(error), "Tipo de excepción inesperado: " + error);
            exigir(error.getMessage() != null && !error.getMessage().isBlank(),
                    "La excepción debe tener un mensaje claro.");
            return;
        }
        throw new AssertionError("Se esperaba un rechazo de tipo " + esperado.getSimpleName());
    }

    private static ReservaBuilder baseBuilder(int personas, Sala sala) {
        return new ReservaBuilder()
                .estudiante(new Estudiante("E001", "Laura"))
                .fecha(LocalDate.now().plusDays(7))
                .horaInicio(LocalTime.of(14, 0))
                .duracionHoras(2)
                .cantidadPersonas(personas)
                .sala(sala);
    }

    private static Reserva nuevaReserva() {
        return baseBuilder(4, new Sala("B", "Sala B", 4)).build();
    }

    private static List<Sala> salas() {
        return new RepositorioSalasMemoria().obtenerDisponibles();
    }

    private static ReservaFacade fachada(RepositorioReservasMemoria repo,
                                         List<ObservadorReserva> observadores) {
        return new ReservaFacade(new RepositorioSalasMemoria(), repo, observadores);
    }

    private static Optional<Reserva> solicitar(ReservaFacade facade, int personas,
                                               EstrategiaSeleccionSala estrategia) {
        return facade.realizarReserva(new Estudiante("E002", "Andrés"),
                LocalDate.now().plusDays(8), LocalTime.of(10, 0),
                2, personas, estrategia);
    }

    private static String capturar(Runnable accion) {
        PrintStream original = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream temporal = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(temporal);
            accion.run();
        } finally {
            System.setOut(original);
        }
        return bytes.toString(StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS P01–P11 — BIBLIOTECA RESERVAS ===");

        verificar("P01", "Builder construye una reserva válida", () -> {
            Reserva reserva = nuevaReserva();
            exigir(reserva.getCodigo().startsWith("R-"), "No generó código de reserva.");
            exigir("Pendiente".equals(reserva.getNombreEstado()), "No inicia Pendiente.");
            exigir(reserva.getCantidadPersonas() == 4, "Cantidad incorrecta.");
        });

        verificar("P02", "Builder rechaza ausencia de estudiante", () -> {
            rechaza(IllegalStateException.class, () -> new ReservaBuilder()
                    .fecha(LocalDate.now().plusDays(7))
                    .horaInicio(LocalTime.of(14, 0))
                    .duracionHoras(2)
                    .cantidadPersonas(4)
                    .sala(new Sala("B", "Sala B", 4))
                    .build());
        });

        verificar("P03", "Strategy por capacidad selecciona Sala B para cuatro", () -> {
            Sala seleccionada = new SeleccionPorCapacidad().seleccionar(salas(), 4).orElseThrow();
            exigir("B".equals(seleccionada.getCodigo()), "Debió seleccionar B.");
        });

        verificar("P04", "Strategy por preferencia selecciona Sala C para cuatro", () -> {
            Sala seleccionada = new SeleccionSalaPreferida("C")
                    .seleccionar(salas(), 4).orElseThrow();
            exigir("C".equals(seleccionada.getCodigo()), "Debió seleccionar C.");
        });

        verificar("P05", "Preferida B para siete personas retorna vacío", () -> {
            exigir(new SeleccionSalaPreferida("B").seleccionar(salas(), 7).isEmpty(),
                    "Sala B no tiene capacidad suficiente.");
        });

        verificar("P06", "Facade guarda y confirma una reserva", () -> {
            RepositorioReservasMemoria repo = new RepositorioReservasMemoria();
            Reserva reserva = solicitar(fachada(repo, List.of()), 4,
                    new SeleccionPorCapacidad()).orElseThrow();
            exigir("Confirmada".equals(reserva.getNombreEstado()), "No confirmó.");
            exigir(repo.obtenerTodas().size() == 1, "No guardó exactamente una reserva.");
            exigir(repo.obtenerTodas().get(0) == reserva, "Repositorio guardó otra reserva.");
        });

        verificar("P07", "Observer notifica a ambos al confirmar", () -> {
            String salida = capturar(() -> solicitar(fachada(
                    new RepositorioReservasMemoria(),
                    List.of(new NotificadorEstudiante(), new RegistroEventos())),
                    4, new SeleccionPorCapacidad()).orElseThrow());
            exigir(salida.contains("Notificación a Andrés"), "Falta notificación al estudiante.");
            exigir(salida.contains("Evento registrado:"), "Falta registro de evento.");
            exigir(salida.contains("Pendiente a Confirmada"), "Transición no registrada.");
        });

        verificar("P08", "Cancelación de confirmada cambia estado y notifica", () -> {
            Reserva[] resultado = new Reserva[1];
            String salida = capturar(() -> {
                Reserva reserva = solicitar(fachada(new RepositorioReservasMemoria(),
                        List.of(new NotificadorEstudiante(), new RegistroEventos())),
                        4, new SeleccionPorCapacidad()).orElseThrow();
                resultado[0] = reserva;
                reserva.cancelar();
            });
            exigir("Cancelada".equals(resultado[0].getNombreEstado()), "No quedó Cancelada.");
            exigir(salida.contains("Notificación a Andrés")
                            && salida.contains("Evento registrado:")
                            && salida.contains("Confirmada a Cancelada"),
                    "No notificó correctamente el cambio de cancelación.");
        });

        verificar("P09", "Confirmar cancelada se rechaza sin notificar", () -> {
            Reserva reserva = nuevaReserva();
            List<String> eventos = new ArrayList<>();
            reserva.agregarObservador((r, anterior, nuevo) -> eventos.add(anterior + ">" + nuevo));
            reserva.confirmar();
            reserva.cancelar();
            int antes = eventos.size();
            rechaza(IllegalStateException.class, reserva::confirmar);
            exigir(eventos.size() == antes, "Una transición inválida emitió notificación.");
            exigir("Cancelada".equals(reserva.getNombreEstado()), "Estado cambió tras rechazo.");
        });

        verificar("P10", "Solicitud para diez no crea reserva", () -> {
            RepositorioReservasMemoria repo = new RepositorioReservasMemoria();
            String salida = capturar(() -> exigir(solicitar(
                    fachada(repo, List.of()), 10, new SeleccionPorCapacidad()).isEmpty(),
                    "No debió seleccionar una sala."));
            exigir(repo.obtenerTodas().isEmpty(), "Se registró una reserva inexistente.");
            exigir(salida.contains("No hay una sala disponible"), "Falta mensaje de falta de sala.");
        });

        verificar("P11", "Finalizar confirmada cambia estado y notifica", () -> {
            Reserva[] resultado = new Reserva[1];
            String salida = capturar(() -> {
                Reserva reserva = solicitar(fachada(new RepositorioReservasMemoria(),
                        List.of(new NotificadorEstudiante(), new RegistroEventos())),
                        4, new SeleccionPorCapacidad()).orElseThrow();
                resultado[0] = reserva;
                reserva.finalizar();
            });
            exigir("Finalizada".equals(resultado[0].getNombreEstado()), "No quedó Finalizada.");
            exigir(salida.contains("Notificación a Andrés")
                            && salida.contains("Evento registrado:")
                            && salida.contains("Confirmada a Finalizada"),
                    "No notificó correctamente la finalización.");
            int[] eventos = {0};
            resultado[0].agregarObservador((r, a, n) -> eventos[0]++);
            rechaza(IllegalStateException.class, resultado[0]::cancelar);
            exigir(eventos[0] == 0, "Cancelación de finalizada emitió evento.");
        });

        System.out.printf("RESULTADO: %d APROBADAS / %d FALLIDAS / %d TOTAL%n",
                aprobadas, fallidas, aprobadas + fallidas);
        if (fallidas != 0 || aprobadas != 11) {
            System.exit(1);
        }
    }
}
