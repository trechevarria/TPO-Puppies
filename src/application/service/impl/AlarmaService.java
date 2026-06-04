package application.service.impl;

import application.dto.alarma.AlarmaCreateDTO;
import application.dto.alarma.AlarmaDTO;
import application.dto.alarma.AlarmaUpdateDTO;
import application.dto.alarma.AtenderAlarmaDTO;
import application.repository.AlarmaRepository;
import application.repository.AnimalRepository;
import application.service.IAlarmaService;
import domain.alarma.AccionAlarma;
import domain.alarma.Alarma;
import domain.animal.Animal;
import domain.autenticacion.SeguridadService;
import domain.ficha.ChequeoRutina;
import domain.notificacion.PushNotificacion;
import domain.usuario.Veterinario;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlarmaService implements IAlarmaService {

    private final AlarmaRepository alarmaRepository;
    private final AnimalRepository animalRepository;
    private final SeguridadService seguridadService;
    private final List<Veterinario> veterinariosRegistrados;

    public AlarmaService(AlarmaRepository alarmaRepository,
                         AnimalRepository animalRepository,
                         SeguridadService seguridadService,
                         List<Veterinario> veterinariosRegistrados) {
        this.alarmaRepository = alarmaRepository;
        this.animalRepository = animalRepository;
        this.seguridadService = seguridadService;
        this.veterinariosRegistrados = new ArrayList<>(veterinariosRegistrados);
    }

    public AlarmaDTO crearAlarma(AlarmaCreateDTO dto) {
        Animal animal = animalRepository.findById(dto.animalId)
                .orElseThrow(() -> new IllegalArgumentException("Animal no encontrado: " + dto.animalId));

        if (dto.acciones == null || dto.acciones.isEmpty()) {
            throw new IllegalArgumentException("La alarma debe tener al menos una accion.");
        }

        Alarma alarma = new Alarma(alarmaRepository.nextId(), animal, dto.periodicidadDias, new PushNotificacion());
        dto.acciones.forEach(tipo -> alarma.agregarAccion(new AccionAlarma(tipo)));
        alarmaRepository.save(alarma);
        return toAlarmaDTO(alarma);
    }

    public AlarmaDTO actualizarAlarma(int id, AlarmaUpdateDTO dto) {
        Alarma alarma = alarmaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alarma no encontrada: " + id));
        alarma.setPeriodicidadDias(dto.periodicidadDias);
        if (dto.acciones != null && !dto.acciones.isEmpty()) {
            alarma.setAcciones(dto.acciones.stream().map(AccionAlarma::new).collect(Collectors.toList()));
        }
        alarmaRepository.update(alarma);
        return toAlarmaDTO(alarma);
    }

    public boolean eliminarAlarma(int id) {
        return alarmaRepository.delete(id);
    }

    public AlarmaDTO obtenerAlarma(int id) {
        return alarmaRepository.findById(id)
                .map(this::toAlarmaDTO)
                .orElseThrow(() -> new IllegalArgumentException("Alarma no encontrada: " + id));
    }

    public List<AlarmaDTO> listarAlarmasPorAnimal(int animalId) {
        return alarmaRepository.findByAnimalId(animalId).stream()
                .map(this::toAlarmaDTO)
                .collect(Collectors.toList());
    }

    public void dispararAlarma(int id) {
        Alarma alarma = alarmaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alarma no encontrada: " + id));
        alarma.disparar(veterinariosRegistrados);
        alarmaRepository.update(alarma);
    }

    public boolean atenderAlarma(int id, AtenderAlarmaDTO dto) {
        Alarma alarma = alarmaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alarma no encontrada: " + id));

        Veterinario vet = seguridadService.obtenerVeterinario(dto.veterinarioId);

        alarma.getAcciones().stream()
                .filter(a -> dto.accionesCompletadas.contains(a.getTipo()))
                .forEach(a -> a.marcarCompletada(dto.comentario));

        ChequeoRutina chequeo = new ChequeoRutina(vet, alarma, dto.comentario, dto.tratamientoFinalizado);
        alarma.getAnimal().getHistoriaClinica().agregarChequeo(chequeo);

        Animal animal = alarma.getAnimal();
        animal.setNecesitaAtencionMedica(!dto.tratamientoFinalizado);
        animalRepository.update(animal);

        alarmaRepository.update(alarma);
        return true;
    }

    private AlarmaDTO toAlarmaDTO(Alarma alarma) {
        AlarmaDTO dto = new AlarmaDTO();
        dto.id = alarma.getId();
        dto.animalId = alarma.getAnimal().getId();
        dto.periodicidadDias = alarma.getPeriodicidadDias();
        dto.acciones = alarma.getAcciones().stream()
                .map(AccionAlarma::getTipo)
                .collect(Collectors.toList());
        dto.proximaEjecucion = alarma.getProximaEjecucion();
        dto.activa = alarma.isActiva();
        return dto;
    }
}
