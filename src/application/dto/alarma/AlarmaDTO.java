package application.dto.alarma;

import domain.enums.TipoAccion;
import java.time.LocalDate;
import java.util.List;

public class AlarmaDTO {
    public int id;
    public int animalId;
    public int periodicidadDias;
    public List<TipoAccion> acciones;
    public LocalDate proximaEjecucion;
    public boolean activa;
}
