package application.service;

import application.dto.animal.AnimalCreateDTO;
import application.dto.animal.AnimalDTO;
import application.dto.animal.AnimalUpdateDTO;
import java.util.List;

public interface IAnimalService {
    AnimalDTO crearAnimal(AnimalCreateDTO dto);
    AnimalDTO actualizarAnimal(int id, AnimalUpdateDTO dto);
    boolean eliminarAnimal(int id);
    AnimalDTO obtenerAnimal(int id);
    List<AnimalDTO> listarAnimales();
}
