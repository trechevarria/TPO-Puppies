package application.controller;

import application.dto.cliente.ClienteCreateDTO;
import application.dto.cliente.ClienteDTO;
import application.dto.cliente.ClienteUpdateDTO;
import application.service.IClienteService;
import java.util.List;

public class ClienteController {

    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public ClienteDTO CreateCliente(ClienteCreateDTO dto) {
        return clienteService.crearCliente(dto);
    }

    public ClienteDTO UpdateCliente(int id, ClienteUpdateDTO dto) {
        return clienteService.actualizarCliente(id, dto);
    }

    public boolean DeleteCliente(int id) {
        return clienteService.eliminarCliente(id);
    }

    public ClienteDTO GetCliente(int id) {
        return clienteService.obtenerCliente(id);
    }

    public List<ClienteDTO> GetClientes() {
        return clienteService.listarClientes();
    }
}
