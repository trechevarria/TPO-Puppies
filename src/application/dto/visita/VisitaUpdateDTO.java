package application.dto.visita;

import domain.enums.TipoNotificacion;
import java.time.LocalDate;

public class VisitaUpdateDTO {
    public LocalDate diaVisita;
    public RangoHorarioDTO rangoHorario;
    public TipoNotificacion preferenciaRecordatorio;
    public int diasAnticipacion;
}
