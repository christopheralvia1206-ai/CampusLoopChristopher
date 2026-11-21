package modelo;

public class Calificacion {
    private String idCalificacion;
    private String idPrestamo;
    private Usuario evaluador;
    private Usuario evaluado;
    private int puntaje; // 1 a 5
    private String etiqueta; // Ej.: "Puntual", "Objeto con daños", "Cobró más de lo acordado"

    public Calificacion(String idCalificacion, String idPrestamo, Usuario evaluador,
                        Usuario evaluado, int puntaje, String etiqueta) {
        this.idCalificacion = idCalificacion;
        this.idPrestamo = idPrestamo;
        this.evaluador = evaluador;
        this.evaluado = evaluado;
        setPuntaje(puntaje);
        this.etiqueta = etiqueta;
    }

    public String getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(String idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Usuario getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(Usuario evaluador) {
        this.evaluador = evaluador;
    }

    public Usuario getEvaluado() {
        return evaluado;
    }

    public void setEvaluado(Usuario evaluado) {
        this.evaluado = evaluado;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        if (puntaje < 1) {
            this.puntaje = 1;
        } else if (puntaje > 5) {
            this.puntaje = 5;
        } else {
            this.puntaje = puntaje;
        }
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public boolean esEtiquetaNegativa() {
        if (etiqueta == null) {
            return false;
        }
        String limpia = etiqueta.trim().toLowerCase();
        return limpia.equals("objeto con daños")
                || limpia.equals("objeto con danos")
                || limpia.equals("cobró más de lo acordado")
                || limpia.equals("cobro mas de lo acordado");
    }

    @Override
    public String toString() {
        return "Calificación [" + idCalificacion + "] | Préstamo: " + idPrestamo
                + " | Puntaje: " + puntaje + "/5 | Etiqueta: " + etiqueta;
    }
}
