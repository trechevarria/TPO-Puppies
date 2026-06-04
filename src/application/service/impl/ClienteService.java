package application.service.impl;

import application.dto.cliente.ClienteCreateDTO;
import application.dto.cliente.ClienteDTO;
import application.dto.cliente.ClienteUpdateDTO;
import application.repository.ClienteRepository;
import application.service.IClienteService;
import domain.cliente.Cliente;
import java.util.List;
import java.util.stream.Collectors;

public class ClienteService implements IClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteDTO crearCliente(ClienteCreateDTO dto) {
        Cliente cliente = new Cliente(
                clienteRepository.nextId(),
                dto.nombre,
                dto.apellido,
                dto.dni != null ? dto.dni : "",
                dto.direccion != null ? dto.direccion : "",
                dto.email,
                dto.telefono,
                dto.estadoCivil,
                dto.ocupacion,
                dto.tieneOtrasMascotas);
        clienteRepository.save(cliente);
        return toClienteDTO(cliente);
    }

    public ClienteDTO actualizarCliente(int id, ClienteUpdateDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));
        cliente.setNombre(dto.nombre);
        cliente.setApellido(dto.apellido);
        if (dto.dni != null) {
            cliente.setDni(dto.dni);
        }
        if (dto.direccion != null) {
            cliente.setDireccion(dto.direccion);
        }
        cliente.setEmail(dto.email);
        cliente.setTelefono(dto.telefono);
        cliente.setEstadoCivil(dto.estadoCivil);
        cliente.setOcupacion(dto.ocupacion);
        cliente.setTieneOtrasMascotas(dto.tieneOtrasMascotas);
        clienteRepository.update(cliente);
        return toClienteDTO(cliente);
    }

    public boolean eliminarCliente(int id) {
        return clienteRepository.delete(id);
    }

    public ClienteDTO obtenerCliente(int id) {
        return clienteRepository.findById(id)
                .map(this::toClienteDTO)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));
    }

    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(this::toClienteDTO)
                .collect(Collectors.toList());
    }

    private ClienteDTO toClienteDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.id = cliente.getId();
        dto.nombre = cliente.getNombre();
        dto.apellido = cliente.getApellido();
        dto.dni = cliente.getDni();
        dto.direccion = cliente.getDireccion();
        dto.email = cliente.getEmail();
        dto.telefono = cliente.getTelefono();
        dto.estadoCivil = cliente.getEstadoCivil();
        dto.ocupacion = cliente.getOcupacion();
        dto.tieneOtrasMascotas = cliente.isTieneOtrasMascotas();
        dto.cantidadAdopciones = cliente.getCantidadDeAdopciones();
        return dto;
    }
}
