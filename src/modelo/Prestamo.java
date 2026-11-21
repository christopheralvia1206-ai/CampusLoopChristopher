package modelo;

public class Prestamo {
    private String idPrestamo;
    private Usuario usuarioSolicitante; // Tipo de dato: tu clase Usuario
    private Articulo articulo;          // Tipo de dato: tu clase Articulo
    private int horasPactadas;
    private double tarifaTotal;
    private String estado;              // "EN_CURSO" o "CERRADO"

    // Constructor
    public Prestamo(String idPrestamo, Usuario usuarioSolicitante, Articulo articulo, int horasPactadas, double tarifaTotal) {
        this.idPrestamo = idPrestamo;
        this.usuarioSolicitante = usuarioSolicitante;
        this.articulo = articulo;
        this.horasPactadas = horasPactadas;
        this.tarifaTotal = tarifaTotal;
        this.estado = "EN_CURSO"; // Se inicializa en curso al crearse
    }

    // Getters y Setters (necesarios para que GestorPrestamos pueda leerlos y cambiarlos)
    public String getIdPrestamo() {
        return idPrestamo;
    }

    public Usuario getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public int getHorasPactadas() {
        return horasPactadas;
    }

    public double getTarifaTotal() {
        return tarifaTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}