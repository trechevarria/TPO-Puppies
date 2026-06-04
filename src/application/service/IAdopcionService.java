package application.service;

import application.dto.adopcion.AdopcionCreateDTO;
import application.dto.adopcion.AdopcionDTO;
import java.util.List;

public interface IAdopcionService {
    AdopcionDTO crearAdopcion(AdopcionCreateDTO dto);
    boolean eliminarAdopcion(int id);
    AdopcionDTO obtenerAdopcion(int id);
    List<AdopcionDTO> listarAdopcionesPorCliente(int clienteId);
}
