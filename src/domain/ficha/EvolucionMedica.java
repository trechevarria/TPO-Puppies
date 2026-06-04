package domain.ficha;

import domain.usuario.Veterinario;

/** Evolucion medica / tratamiento (enunciado). */
public class EvolucionMedica extends FichaMedica {

    private boolean enTratamiento;

    public EvolucionMedica(Veterinario veterinario, boolean enTratamiento, String descripcion) {
        super(descripcion, veterinario);
        this.enTratamiento = enTratamiento;
    }

    public boolean isEnTratamiento() { return enTratamiento; }

    public void setEnTratamiento(boolean enTratamiento) { this.enTratamiento = enTratamiento; }
}
