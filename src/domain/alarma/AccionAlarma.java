package domain.alarma;

import domain.enums.TipoAccion;

public class AccionAlarma {

    private TipoAccion tipo;
    private boolean completada;
    private String comentario;

    public AccionAlarma(TipoAccion tipo) {
        this.tipo = tipo;
        this.completada = false;
        this.comentario = "";
    }

    public void marcarCompletada(String comentario) {
        this.completada = true;
        this.comentario = comentario;
    }

    public TipoAccion getTipo() { return tipo; }
    public boolean isCompletada() { return completada; }
    public String getComentario() { return comentario; }
}
