package application.dto.adopcion;

import java.time.LocalDate;
import java.util.List;

public class AdopcionDTO {
    public int id;
    public int clienteId;
    public int animalId;
    public String motivoAdopcion;
    public List<String> tipoAnimalesInteres;
    public LocalDate fechaAdopcion;
}
