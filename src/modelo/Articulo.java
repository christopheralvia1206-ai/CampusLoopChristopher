package modelo;

public class Articulo {

    private String idArticulo;
    private String nombre;
    private String categoria;   // Laboratorio, Dibujo, Electrónica, Emergencia
    private String estadoFisico; // Excelente, Bueno, Regular
    private String modalidad;    // Préstamo temporal, Micropago, Regalo
    private double tarifaBase;
    private String campusBase;
    private String estadoDisponibilidad; // "DISPONIBLE" o "PRESTADO"
    private Usuario propietario; // Relación de ASOCIACIÓN con Usuario

    public Articulo(String idArticulo, String nombre, String categoria, String estadoFisico,
                    String modalidad, double tarifaBase, Usuario propietario) {
        this.idArticulo = idArticulo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.estadoFisico = estadoFisico;
        this.modalidad = modalidad;
        this.tarifaBase = tarifaBase;
        this.propietario = propietario;
        this.campusBase = propietario.getCampus();
        this.estadoDisponibilidad = "DISPONIBLE";
    }

    // Getters y Setters
    public String getIdArticulo() {
        return idArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getEstadoFisico() {
        return estadoFisico;
    }

    public String getModalidad() {
        return modalidad;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public String getCampusBase() {
        return campusBase;
    }

    public String getEstadoDisponibilidad() {
        return estadoDisponibilidad;
    }

    public void setEstadoDisponibilidad(String estadoDisponibilidad) {
        this.estadoDisponibilidad = estadoDisponibilidad;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    @Override
    public String toString() {
        return "[" + idArticulo + "] " + nombre + " (" + categoria + " - " + estadoFisico + ")"
                + " | Modalidad: " + modalidad + " ($" + tarifaBase + ")"
                + " | Campus: " + campusBase + " | Dueño: " + propietario.getNombre()
                + " | Estado: " + estadoDisponibilidad;
    }
}
