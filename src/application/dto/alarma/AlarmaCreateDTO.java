package application.dto.alarma;

import domain.enums.TipoAccion;
import java.util.List;

public class AlarmaCreateDTO {
    public int animalId;
    public int periodicidadDias;
    public List<TipoAccion> acciones;
}
