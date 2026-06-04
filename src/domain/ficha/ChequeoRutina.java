package domain.ficha;

import domain.alarma.Alarma;
import domain.usuario.Veterinario;

/** Chequeos y atencion de alarmas (controles de rutina). */
public class ChequeoRutina extends FichaMedica {

    private final Alarma alarma;
    private final String comentario;
    private final boolean tratamientoFinalizado;

    public ChequeoRutina(Veterinario veterinario, Alarma alarma, String comentario, boolean tratamientoFinalizado) {
        super(comentario, veterinario);
        this.alarma = alarma;
        this.comentario = comentario;
        this.tratamientoFinalizado = tratamientoFinalizado;
    }

    public Alarma getAlarma() { return alarma; }
    public String getComentario() { return comentario; }
    public boolean isTratamientoFinalizado() { return tratamientoFinalizado; }
}
