package application.service.impl;

import application.dto.adopcion.AdopcionCreateDTO;
import application.dto.adopcion.AdopcionDTO;
import application.repository.AdopcionRepository;
import application.repository.AnimalRepository;
import application.repository.ClienteRepository;
import application.service.IAdopcionService;
import domain.adopcion.Adopcion;
import domain.animal.Animal;
import domain.cliente.Cliente;
import java.util.List;
import java.util.stream.Collectors;

public class AdopcionService implements IAdopcionService {

    private final AdopcionRepository adopcionRepository;
    private final AnimalRepository animalRepository;
    private final ClienteRepository clienteRepository;

    public AdopcionService(AdopcionRepository adopcionRepository,
                           AnimalRepository animalRepository,
                           ClienteRepository clienteRepository) {
        this.adopcionRepository = adopcionRepository;
        this.animalRepository = animalRepository;
        this.clienteRepository = clienteRepository;
    }

    public AdopcionDTO crearAdopcion(AdopcionCreateDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + dto.clienteId));

        Animal animal = animalRepository.findById(dto.animalId)
                .orElseThrow(() -> new IllegalArgumentException("Animal no encontrado: " + dto.animalId));

        if (!cliente.puedeAdoptar()) {
            throw new IllegalStateException("El cliente ya alcanzo el maximo de 2 adopciones.");
        }
        if (!animal.puedeSerAdoptado()) {
            throw new IllegalStateException(
                    "El animal no puede ser adoptado (salvaje o bajo tratamiento medico).");
        }

        Adopcion adopcion = new Adopcion(
                adopcionRepository.nextId(),
                cliente,
                animal,
                dto.motivoAdopcion,
                dto.tipoAnimalesInteres);

        cliente.incrementarAdopciones();
        clienteRepository.update(cliente);
        adopcionRepository.save(adopcion);
        return toAdopcionDTO(adopcion);
    }

    public boolean eliminarAdopcion(int id) {
        return adopcionRepository.delete(id);
    }

    public AdopcionDTO obtenerAdopcion(int id) {
        return adopcionRepository.findById(id)
                .map(this::toAdopcionDTO)
                .orElseThrow(() -> new IllegalArgumentException("Adopcion no encontrada: " + id));
    }

    public List<AdopcionDTO> listarAdopcionesPorCliente(int clienteId) {
        return adopcionRepository.findByClienteId(clienteId).stream()
                .map(this::toAdopcionDTO)
                .collect(Collectors.toList());
    }

    private AdopcionDTO toAdopcionDTO(Adopcion adopcion) {
        AdopcionDTO dto = new AdopcionDTO();
        dto.id = adopcion.getId();
        dto.clienteId = adopcion.getCliente().getId();
        dto.animalId = adopcion.getAnimal().getId();
        dto.motivoAdopcion = adopcion.getMotivoAdopcion();
        dto.tipoAnimalesInteres = adopcion.getTipoAnimalesInteres();
        dto.fechaAdopcion = adopcion.getFechaAdopcion();
        return dto;
    }
}
