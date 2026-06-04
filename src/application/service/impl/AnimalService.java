package application.service.impl;

import application.dto.animal.AnimalCreateDTO;
import application.dto.animal.AnimalDTO;
import application.dto.animal.AnimalUpdateDTO;
import application.repository.AnimalRepository;
import application.service.IAnimalService;
import domain.animal.Animal;
import domain.animal.AnimalFactory;
import domain.animal.AnimalSalvaje;
import domain.enums.TipoAnimal;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalService implements IAnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public AnimalDTO crearAnimal(AnimalCreateDTO dto) {
        TipoAnimal tipo = dto.tipo != null ? dto.tipo : TipoAnimal.DOMESTICO;
        Animal animal = AnimalFactory.crear(
                tipo,
                "TMP",
                "Sin nombre",
                "General",
                "Sin especificar",
                dto.altura,
                dto.peso,
                1,
                dto.necesitaAtencionMedica);
        animalRepository.save(animal);
        return toAnimalDTO(animal);
    }

    public AnimalDTO actualizarAnimal(int id, AnimalUpdateDTO dto) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Animal no encontrado: " + id));

        if (dto.tipo != null) {
            TipoAnimal actual = animal instanceof AnimalSalvaje ? TipoAnimal.SALVAJE : TipoAnimal.DOMESTICO;
            if (dto.tipo != actual) {
                throw new IllegalArgumentException("No se puede cambiar el tipo del animal.");
            }
        }

        animal.setPeso(dto.peso);
        animal.setAltura(dto.altura);
        animal.setNecesitaAtencionMedica(dto.necesitaAtencionMedica);
        animalRepository.update(animal);
        return toAnimalDTO(animal);
    }

    public boolean eliminarAnimal(int id) {
        return animalRepository.delete(id);
    }

    public AnimalDTO obtenerAnimal(int id) {
        return animalRepository.findById(id)
                .map(this::toAnimalDTO)
                .orElseThrow(() -> new IllegalArgumentException("Animal no encontrado: " + id));
    }

    public List<AnimalDTO> listarAnimales() {
        return animalRepository.findAll().stream()
                .map(this::toAnimalDTO)
                .collect(Collectors.toList());
    }

    private AnimalDTO toAnimalDTO(Animal animal) {
        AnimalDTO dto = new AnimalDTO();
        dto.id = animal.getId();
        dto.tipo = animal instanceof AnimalSalvaje ? TipoAnimal.SALVAJE : TipoAnimal.DOMESTICO;
        dto.peso = animal.getPeso();
        dto.altura = animal.getAltura();
        dto.necesitaAtencionMedica = animal.isNecesitaAtencionMedica();
        return dto;
    }
}
