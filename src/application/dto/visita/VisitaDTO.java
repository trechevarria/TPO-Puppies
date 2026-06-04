package application.dto.visita;

import domain.enums.TipoNotificacion;
import java.time.LocalDate;

public class VisitaDTO {
    public int id;
    public int adopcionId;
    public int visitadorId;
    public LocalDate diaVisita;
    public RangoHorarioDTO rangoHorario;
    public TipoNotificacion preferenciaRecordatorio;
    public int diasAnticipacion;
    public boolean continuarVisitas;
    /** Presente cuando la visita ya tiene encuesta completada. */
    public EncuestaDTO encuesta;
}
