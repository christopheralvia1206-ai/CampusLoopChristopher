package negocio;

import java.util.ArrayList;
import java.util.Scanner;
import modelo.Calificacion;
import modelo.Usuario;

public class GestorReputacion {
    private ArrayList<Calificacion> listaCalificaciones;

    public GestorReputacion() {
        this.listaCalificaciones = new ArrayList<>();
    }

    public ArrayList<Calificacion> getListaCalificaciones() {
        return listaCalificaciones;
    }

    // Método 1: Registrar calificación y aplicar reporte negativo si corresponde
    public String calificar(String idCalificacion, String idPrestamo, Usuario evaluador,
                            Usuario evaluado, int puntaje, String etiqueta) {
        if (evaluador == null || evaluado == null) {
            return "Error: El evaluador y el evaluado deben ser estudiantes válidos.";
        }
        if (evaluador == evaluado) {
            return "Error: Un estudiante no puede calificar a sí mismo.";
        }
        if (idPrestamo == null || idPrestamo.trim().isEmpty()) {
            return "Error: Debe ingresar el código de un préstamo cerrado válido.";
        }

        Calificacion nuevaCalificacion = new Calificacion(
                idCalificacion, idPrestamo, evaluador, evaluado, puntaje, etiqueta
        );
        listaCalificaciones.add(nuevaCalificacion);

        if (nuevaCalificacion.esEtiquetaNegativa()) {
            evaluado.agregarReporteNegativo();
        }

        String insignia = recalcularConfianzaEInsignia(evaluado);
        return "Calificación registrada. Insignia de " + evaluado.getNombre()
                + ": " + insignia + " | Estado perfil: " + evaluado.getEstadoPerfil();
    }

    // Método 2: Recalcular índice de confianza (0% a 100%) y devolver insignia
    public String recalcularConfianzaEInsignia(Usuario evaluado) {
        if (evaluado == null) {
            return "Sancionado";
        }

        int sumaPuntajes = 0;
        int cantidad = 0;

        for (Calificacion c : listaCalificaciones) {
            if (c.getEvaluado() == evaluado) {
                sumaPuntajes += c.getPuntaje();
                cantidad++;
            }
        }

        double nuevoPorcentaje;
        if (cantidad == 0) {
            nuevoPorcentaje = 100.0;
        } else {
            double promedio = (double) sumaPuntajes / cantidad;
            nuevoPorcentaje = Math.round(((promedio / 5.0) * 100.0) * 100.0) / 100.0;
        }

        if (nuevoPorcentaje < 0.0) {
            nuevoPorcentaje = 0.0;
        } else if (nuevoPorcentaje > 100.0) {
            nuevoPorcentaje = 100.0;
        }

        evaluado.setIndiceConfianza(nuevoPorcentaje);

        if (nuevoPorcentaje >= 80.0) {
            return "Confiable";
        } else if (nuevoPorcentaje >= 60.0) {
            return "En Observación";
        } else {
            return "Sancionado";
        }
    }

    public void listarCalificaciones() {
        if (listaCalificaciones.isEmpty()) {
            System.out.println("No hay calificaciones registradas todavía.");
            return;
        }
        System.out.println("\n--- HISTORIAL DE CALIFICACIONES ---");
        for (Calificacion c : listaCalificaciones) {
            System.out.println(c);
        }
    }

    // Método 3: Submenú del Módulo 4
    public void mostrarMenuModulo4(Scanner sc, GestorInventario gestorInv) {
        int opcion = -1;
        do {
            System.out.println("\n--- REPUTACIÓN Y CALIFICACIONES ---");
            System.out.println("1. Registrar calificación tras devolución");
            System.out.println("2. Recalcular y consultar confianza e insignia");
            System.out.println("3. Listar todas las calificaciones");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Código de calificación (ej. CAL-01): ");
                    String idCal = sc.nextLine().trim();
                    System.out.print("Código del préstamo cerrado (ej. PRE-01): ");
                    String idPrest = sc.nextLine().trim();

                    System.out.print("ID Banner/Estudiante del evaluador: ");
                    Usuario evaluador = gestorInv.buscarUsuarioPorId(sc.nextLine().trim());
                    System.out.print("ID Banner/Estudiante del evaluado: ");
                    Usuario evaluado = gestorInv.buscarUsuarioPorId(sc.nextLine().trim());

                    if (evaluador == null || evaluado == null) {
                        System.out.println("Error: Uno o ambos estudiantes no están registrados.");
                        break;
                    }

                    System.out.print("Puntaje (1 a 5): ");
                    int puntaje;
                    try {
                        puntaje = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: El puntaje debe ser un número entero del 1 al 5.");
                        break;
                    }

                    System.out.println("Etiquetas sugeridas: Puntual | Objeto con daños | Cobró más de lo acordado");
                    System.out.print("Ingrese la etiqueta: ");
                    String etiqueta = sc.nextLine().trim();

                    System.out.println(calificar(idCal, idPrest, evaluador, evaluado, puntaje, etiqueta));
                    break;

                case 2:
                    System.out.print("Ingrese ID Banner/Estudiante a consultar: ");
                    Usuario consulta = gestorInv.buscarUsuarioPorId(sc.nextLine().trim());
                    if (consulta != null) {
                        String insignia = recalcularConfianzaEInsignia(consulta);
                        System.out.println(consulta + " | Insignia: " + insignia);
                    } else {
                        System.out.println("Estudiante no encontrado.");
                    }
                    break;

                case 3:
                    listarCalificaciones();
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
