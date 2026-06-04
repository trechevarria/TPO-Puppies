package application.service;

import application.dto.alarma.AlarmaCreateDTO;
import application.dto.alarma.AlarmaDTO;
import application.dto.alarma.AlarmaUpdateDTO;
import application.dto.alarma.AtenderAlarmaDTO;
import java.util.List;

public interface IAlarmaService {
    AlarmaDTO crearAlarma(AlarmaCreateDTO dto);
    AlarmaDTO actualizarAlarma(int id, AlarmaUpdateDTO dto);
    boolean eliminarAlarma(int id);
    AlarmaDTO obtenerAlarma(int id);
    List<AlarmaDTO> listarAlarmasPorAnimal(int animalId);
    void dispararAlarma(int id);
    boolean atenderAlarma(int id, AtenderAlarmaDTO dto);
}
