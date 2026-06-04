package application.controller;

import application.dto.animal.AnimalCreateDTO;
import application.dto.animal.AnimalDTO;
import application.dto.animal.AnimalUpdateDTO;
import application.service.IAnimalService;
import java.util.List;

public class AnimalController {

    private final IAnimalService animalService;

    public AnimalController(IAnimalService animalService) {
        this.animalService = animalService;
    }

    public AnimalDTO CreateAnimal(AnimalCreateDTO dto) {
        return animalService.crearAnimal(dto);
    }

    public AnimalDTO UpdateAnimal(int id, AnimalUpdateDTO dto) {
        return animalService.actualizarAnimal(id, dto);
    }

    public boolean DeleteAnimal(int id) {
        return animalService.eliminarAnimal(id);
    }

    public AnimalDTO GetAnimal(int id) {
        return animalService.obtenerAnimal(id);
    }

    public List<AnimalDTO> GetAnimales() {
        return animalService.listarAnimales();
    }
}
