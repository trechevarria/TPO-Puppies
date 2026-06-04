package application.controller;

import application.dto.visita.EncuestaDTO;
import application.dto.visita.VisitaCreateDTO;
import application.dto.visita.VisitaDTO;
import application.dto.visita.VisitaUpdateDTO;
import application.service.IVisitaService;
import java.time.LocalDate;
import java.util.List;

public class VisitaController {

    private final IVisitaService visitaService;

    public VisitaController(IVisitaService visitaService) {
        this.visitaService = visitaService;
    }

    public int CreateVisita(VisitaCreateDTO dto) {
        return visitaService.crearVisita(dto);
    }

    public boolean UpdateVisita(int id, VisitaUpdateDTO dto) {
        return visitaService.actualizarVisita(id, dto);
    }

    public boolean DeleteVisita(int id) {
        return visitaService.eliminarVisita(id);
    }

    public VisitaDTO GetVisita(int id) {
        return visitaService.obtenerVisita(id);
    }

    public List<VisitaDTO> GetVisitasPorAdopcion(int adopcionId) {
        return visitaService.listarVisitasPorAdopcion(adopcionId);
    }

    public boolean CompletarEncuesta(int id, EncuestaDTO dto) {
        return visitaService.completarEncuesta(id, dto);
    }

    public void procesarRecordatorios(LocalDate hoy) {
        visitaService.procesarRecordatoriosVisitas(hoy);
    }
}
