package domain.encuesta;

import domain.enums.Calificacion;

public class Encuesta {

    private Calificacion calificacionGeneral;
    private Calificacion calificacionLimpieza;
    private Calificacion calificacionAmbiente;

    public Encuesta(Calificacion calificacionGeneral,
                    Calificacion calificacionLimpieza,
                    Calificacion calificacionAmbiente) {
        this.calificacionGeneral = calificacionGeneral;
        this.calificacionLimpieza = calificacionLimpieza;
        this.calificacionAmbiente = calificacionAmbiente;
    }

    public Calificacion getCalificacionGeneral() { return calificacionGeneral; }
    public Calificacion getCalificacionLimpieza() { return calificacionLimpieza; }
    public Calificacion getCalificacionAmbiente() { return calificacionAmbiente; }
}
