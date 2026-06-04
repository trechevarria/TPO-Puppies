package domain.adopcion;

import domain.config.ParametrosRecordatorio;
import domain.encuesta.Encuesta;
import domain.enums.TipoNotificacion;
import domain.usuario.Visitador;
import java.time.LocalDate;
import java.time.LocalTime;

public class Visita {

    private int id;
    private int adopcionId;
    private Visitador visitador;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String comentarios;
    private Encuesta encuesta;
    private TipoNotificacion preferenciaRecordatorio;
    private int diasAnticipacion;
    private boolean continuarVisitas;
    private boolean recordatorioEnviado;

    public Visita(int id, int adopcionId, Visitador visitador, LocalDate fecha,
                  LocalTime horaInicio, LocalTime horaFin,
                  TipoNotificacion preferenciaRecordatorio, int diasAnticipacion) {
        this.id = id;
        this.adopcionId = adopcionId;
        this.visitador = visitador;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.preferenciaRecordatorio = preferenciaRecordatorio;
        this.diasAnticipacion = resolverDiasAnticipacion(diasAnticipacion);
        this.continuarVisitas = true;
        this.recordatorioEnviado = false;
    }

    private static int resolverDiasAnticipacion(int dias) {
        return dias > 0 ? dias : ParametrosRecordatorio.getDiasAnticipacionPorDefecto();
    }

    public boolean debeEnviarRecordatorio(LocalDate hoy) {
        if (recordatorioEnviado || !continuarVisitas) {
            return false;
        }
        LocalDate fechaRecordatorio = fecha.minusDays(diasAnticipacion);
        return !hoy.isBefore(fechaRecordatorio) && hoy.isBefore(fecha);
    }

    public void marcarRecordatorioEnviado() {
        this.recordatorioEnviado = true;
    }

    public void completarEncuesta(Encuesta encuesta, boolean continuarVisitas, String comentarios) {
        this.encuesta = encuesta;
        this.continuarVisitas = continuarVisitas;
        this.comentarios = comentarios;
    }

    public String getRangoHorarioTexto() {
        return horaInicio + "-" + horaFin;
    }

    public int getId() { return id; }
    public int getAdopcionId() { return adopcionId; }
    public Visitador getVisitador() { return visitador; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public String getDiaVisita() { return fecha.toString(); }
    public String getRangoHorario() { return getRangoHorarioTexto(); }
    public String getComentarios() { return comentarios; }
    public Encuesta getEncuesta() { return encuesta; }
    public TipoNotificacion getPreferenciaRecordatorio() { return preferenciaRecordatorio; }
    public int getDiasAnticipacion() { return diasAnticipacion; }
    public boolean isContinuarVisitas() { return continuarVisitas; }
    public boolean isRecordatorioEnviado() { return recordatorioEnviado; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public void setPreferenciaRecordatorio(TipoNotificacion preferencia) { this.preferenciaRecordatorio = preferencia; }
    public void setDiasAnticipacion(int dias) { this.diasAnticipacion = resolverDiasAnticipacion(dias); }
}
