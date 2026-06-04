package application.dto.alarma;

import domain.enums.TipoAccion;
import java.util.List;

public class AlarmaUpdateDTO {
    public int periodicidadDias;
    public List<TipoAccion> acciones;
}
