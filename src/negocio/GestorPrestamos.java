package negocio;

import modelo.Prestamo;
import modelo.Usuario;
import modelo.Articulo;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorPrestamos {
    // Agregación: lista de préstamos realizados
    private ArrayList<Prestamo> listaPrestamos;

    public GestorPrestamos() {
        this.listaPrestamos = new ArrayList<>();
    }

    // Método auxiliar para buscar un préstamo por su ID
    public Prestamo buscarPrestamoPorId(String idPrestamo) {
        for (Prestamo p : listaPrestamos) {
            if (p.getIdPrestamo().equalsIgnoreCase(idPrestamo)) {
                return p;
            }
        }
        return null;
    }

    // Método 1: Crear préstamo
    public boolean crearPrestamo(String idPrestamo, Usuario usuario, Articulo articulo, int horasPactadas) {
        if (usuario.verificarElegibilidad() && articulo.getEstadoDisponibilidad().equals("DISPONIBLE")) {
            double tarifaTotal = articulo.getTarifaBase() * horasPactadas;
            Prestamo nuevoPrestamo = new Prestamo(idPrestamo, usuario, articulo, horasPactadas, tarifaTotal);

            // El constructor de Prestamo ya lo deja en "EN_CURSO"
            articulo.setEstadoDisponibilidad("PRESTADO");
            usuario.registrarDevolucionPendiente();

            listaPrestamos.add(nuevoPrestamo);
            return true;
        }
        return false;
    }

    // Método 2: Devolver y cobrar mora
    public double devolverYConbrarMora(Prestamo prestamo, int horasReales) {
        double tarifaBase = prestamo.getTarifaTotal();
        double recargo = 0.0;

        // Si se pasó de las horas pactadas, se cobra recargo por cada hora extra
        if (horasReales > prestamo.getHorasPactadas()) {
            int horasExtra = horasReales - prestamo.getHorasPactadas();
            // Recargo del 50% adicional sobre la tarifa base por hora extra (ajustable según regla)
            recargo = horasExtra * (prestamo.getArticulo().getTarifaBase() * 1.5);
        }

        double totalFinal = tarifaBase + recargo;

        prestamo.setEstado("CERRADO");
        prestamo.getArticulo().setEstadoDisponibilidad("DISPONIBLE");
        prestamo.getUsuarioSolicitante().liberarDevolucionPendiente();

        return totalFinal;
    }

    // Método 3: Submenú de consola (estilo GestorInventario)
    public void mostrarMenuModulo2(Scanner sc, GestorInventario gestorInv) {
        int opcion = -1;
        do {
            System.out.println("\n--- MÓDULO 2: GESTIÓN DE PRÉSTAMOS Y DEVOLUCIONES ---");
            System.out.println("1. Crear nuevo préstamo");
            System.out.println("2. Registrar devolución y cobrar mora");
            System.out.println("3. Listar préstamos registrados");
            System.out.println("0. Regresar al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("ID del préstamo (ej. PRES-01): ");
                    String idPres = sc.nextLine();

                    System.out.print("ID del estudiante solicitante: ");
                    String idEst = sc.nextLine();
                    Usuario solicitante = gestorInv.buscarUsuarioPorId(idEst);

                    if (solicitante == null) {
                        System.out.println("Error: Estudiante no encontrado.");
                        break;
                    }
                    if (!solicitante.verificarElegibilidad()) {
                        System.out.println("Acceso denegado: El estudiante tiene estado " + solicitante.getEstadoPerfil());
                        break;
                    }

                    System.out.print("Código del artículo a prestar (ej. ART-01): ");
                    String idArt = sc.nextLine();
                    Articulo art = gestorInv.buscarArticuloPorId(idArt); // Asegúrate de tener este getter/buscador en GestorInventario

                    if (art == null) {
                        System.out.println("Error: Artículo no encontrado.");
                        break;
                    }
                    if (!art.getEstadoDisponibilidad().equals("DISPONIBLE")) {
                        System.out.println("Error: El artículo no está disponible (Estado actual: " + art.getEstadoDisponibilidad() + ").");
                        break;
                    }

                    System.out.print("Horas pactadas de préstamo: ");
                    int horas = Integer.parseInt(sc.nextLine());

                    if (crearPrestamo(idPres, solicitante, art, horas)) {
                        System.out.println("Préstamo registrado con éxito en estado EN_CURSO.");
                    } else {
                        System.out.println("No se pudo procesar el préstamo.");
                    }
                    break;

                case 2:
                    System.out.print("Ingrese el ID del préstamo a devolver: ");
                    String idDevolucion = sc.nextLine();
                    Prestamo prestamoActivo = buscarPrestamoPorId(idDevolucion);

                    if (prestamoActivo == null) {
                        System.out.println("Error: Préstamo no encontrado.");
                        break;
                    }
                    if (prestamoActivo.getEstado().equals("CERRADO")) {
                        System.out.println("Aviso: Este préstamo ya se encuentra CERRADO.");
                        break;
                    }

                    System.out.print("Ingrese las horas reales que se demoró el estudiante: ");
                    int horasReales = Integer.parseInt(sc.nextLine());

                    double totalCobrar = devolverYConbrarMora(prestamoActivo, horasReales);
                    System.out.println("Devolución registrada. Estado: CERRADO.");
                    System.out.println("Total a cobrar (incluyendo mora si aplica): $" + totalCobrar);
                    break;

                case 3:
                    if (listaPrestamos.isEmpty()) {
                        System.out.println("No hay préstamos registrados.");
                    } else {
                        for (Prestamo p : listaPrestamos) {
                            System.out.println("ID: " + p.getIdPrestamo()
                                    + " | Usuario: " + p.getUsuarioSolicitante().getNombre()
                                    + " | Artículo: " + p.getArticulo().getNombre()
                                    + " | Estado: " + p.getEstado()
                                    + " | Tarifa base: $" + p.getTarifaTotal());
                        }
                    }
                    break;

                case 0:
                    System.out.println("Regresando al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
}