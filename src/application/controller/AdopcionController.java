package application.controller;

import application.dto.adopcion.AdopcionCreateDTO;
import application.dto.adopcion.AdopcionDTO;
import application.service.IAdopcionService;
import java.util.List;

public class AdopcionController {

    private final IAdopcionService adopcionService;

    public AdopcionController(IAdopcionService adopcionService) {
        this.adopcionService = adopcionService;
    }

    public AdopcionDTO CreateAdopcion(AdopcionCreateDTO dto) {
        return adopcionService.crearAdopcion(dto);
    }

    public boolean DeleteAdopcion(int id) {
        return adopcionService.eliminarAdopcion(id);
    }

    public AdopcionDTO GetAdopcion(int id) {
        return adopcionService.obtenerAdopcion(id);
    }

    public List<AdopcionDTO> GetAdopcionesPorCliente(int clienteId) {
        return adopcionService.listarAdopcionesPorCliente(clienteId);
    }
}
