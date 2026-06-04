package domain.ficha;

import domain.encuesta.Encuesta;
import domain.usuario.Visitador;
import domain.usuario.Veterinario;

/** Registro de visita a domicilio post-adopcion (enunciado + diagrama). */
public class VisitaDomicilio extends FichaMedica {

    private final Visitador visitador;
    private final Encuesta encuesta;
    private final boolean continuarVisitas;

    public VisitaDomicilio(Visitador visitador, Encuesta encuesta, boolean continuarVisitas) {
        super("Visita domicilio", null);
        this.visitador = visitador;
        this.encuesta = encuesta;
        this.continuarVisitas = continuarVisitas;
    }

    @Override
    public Veterinario getVeterinario() {
        return null;
    }

    public Visitador getVisitador() { return visitador; }
    public Encuesta getEncuesta() { return encuesta; }
    public boolean isContinuarVisitas() { return continuarVisitas; }
}
