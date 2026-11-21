package modelo;

public class Usuario {

    private String idEstudiante;
    private String nombre;
    private String correoInstitucional;
    private String campus;
    private double indiceConfianza;
    private int devolucionesPendientes;
    private int reportesNegativos;
    private String estadoPerfil; // "HABILITADO" o "SUSPENDIDO_POR_MORA"

    public Usuario(String idEstudiante, String nombre, String correoInstitucional, String campus) {
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.correoInstitucional = correoInstitucional;
        this.campus = campus;
        this.indiceConfianza = 100.0;
        this.devolucionesPendientes = 0;
        this.reportesNegativos = 0;
        this.estadoPerfil = "HABILITADO";
    }

    // Módulo 1: Control de Elegibilidad y Bloqueo de Perfil
    public boolean verificarElegibilidad() {
        if (devolucionesPendientes > 0 || indiceConfianza < 60.0 || reportesNegativos > 2) {
            this.estadoPerfil = "SUSPENDIDO_POR_MORA";
            return false;
        }
        this.estadoPerfil = "HABILITADO";
        return true;
    }

    // Mateo y Keira actualicen el perfil desde sus módulos
    public void registrarDevolucionPendiente() {
        this.devolucionesPendientes++;
        verificarElegibilidad();
    }

    public void liberarDevolucionPendiente() {
        if (this.devolucionesPendientes > 0) {
            this.devolucionesPendientes--;
        }
        verificarElegibilidad();
    }

    public void agregarReporteNegativo() {
        this.reportesNegativos++;
        verificarElegibilidad();
    }

    // Getters y Setters
    public String getIdEstudiante() {
        return idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public String getCampus() {
        return campus;
    }

    public double getIndiceConfianza() {
        return indiceConfianza;
    }

    public void setIndiceConfianza(double indiceConfianza) {
        this.indiceConfianza = indiceConfianza;
        verificarElegibilidad();
    }

    public int getDevolucionesPendientes() {
        return devolucionesPendientes;
    }

    public int getReportesNegativos() {
        return reportesNegativos;
    }

    public String getEstadoPerfil() {
        return estadoPerfil;
    }

    public void setEstadoPerfil(String estadoPerfil) {
        this.estadoPerfil = estadoPerfil;
    }

    @Override
    public String toString() {
        return "ID: " + idEstudiante + " | Nombre: " + nombre + " | Campus: " + campus
                + " | Confianza: " + indiceConfianza + "% | Estado: " + estadoPerfil;
    }
}
