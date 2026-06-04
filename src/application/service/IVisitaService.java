package application.service;

import application.dto.visita.EncuestaDTO;
import application.dto.visita.VisitaCreateDTO;
import application.dto.visita.VisitaDTO;
import application.dto.visita.VisitaUpdateDTO;
import java.time.LocalDate;
import java.util.List;

public interface IVisitaService {
    int crearVisita(VisitaCreateDTO dto);
    boolean actualizarVisita(int id, VisitaUpdateDTO dto);
    boolean eliminarVisita(int id);
    VisitaDTO obtenerVisita(int id);
    List<VisitaDTO> listarVisitasPorAdopcion(int adopcionId);
    boolean completarEncuesta(int id, EncuestaDTO dto);
    void procesarRecordatoriosVisitas(LocalDate hoy);
}
