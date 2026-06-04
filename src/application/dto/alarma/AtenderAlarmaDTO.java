package application.dto.alarma;

import domain.enums.TipoAccion;
import java.util.List;

public class AtenderAlarmaDTO {
    public int veterinarioId;
    public String comentario;
    public boolean tratamientoFinalizado;
    public List<TipoAccion> accionesCompletadas;
}
