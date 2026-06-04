package application.controller;

import application.dto.alarma.AlarmaCreateDTO;
import application.dto.alarma.AlarmaDTO;
import application.dto.alarma.AlarmaUpdateDTO;
import application.dto.alarma.AtenderAlarmaDTO;
import application.service.IAlarmaService;
import java.util.List;

public class AlarmaController {

    private final IAlarmaService alarmaService;

    public AlarmaController(IAlarmaService alarmaService) {
        this.alarmaService = alarmaService;
    }

    public AlarmaDTO CreateAlarma(AlarmaCreateDTO dto) {
        return alarmaService.crearAlarma(dto);
    }

    public AlarmaDTO UpdateAlarma(int id, AlarmaUpdateDTO dto) {
        return alarmaService.actualizarAlarma(id, dto);
    }

    public boolean DeleteAlarma(int id) {
        return alarmaService.eliminarAlarma(id);
    }

    public AlarmaDTO GetAlarma(int id) {
        return alarmaService.obtenerAlarma(id);
    }

    public List<AlarmaDTO> GetAlarmasPorAnimal(int animalId) {
        return alarmaService.listarAlarmasPorAnimal(animalId);
    }

    public void DispararAlarma(int id) {
        alarmaService.dispararAlarma(id);
    }

    public boolean AtenderAlarma(int id, AtenderAlarmaDTO dto) {
        return alarmaService.atenderAlarma(id, dto);
    }
}
