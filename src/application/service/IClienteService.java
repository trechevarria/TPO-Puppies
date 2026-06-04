package application.service;

import application.dto.cliente.ClienteCreateDTO;
import application.dto.cliente.ClienteDTO;
import application.dto.cliente.ClienteUpdateDTO;
import java.util.List;

public interface IClienteService {
    ClienteDTO crearCliente(ClienteCreateDTO dto);
    ClienteDTO actualizarCliente(int id, ClienteUpdateDTO dto);
    boolean eliminarCliente(int id);
    ClienteDTO obtenerCliente(int id);
    List<ClienteDTO> listarClientes();
}
