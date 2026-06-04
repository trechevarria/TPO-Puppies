package application.repository;

import domain.animal.Animal;
import java.util.List;
import java.util.Optional;

public interface AnimalRepository {
    Animal save(Animal animal);
    Animal update(Animal animal);
    boolean delete(int id);
    Optional<Animal> findById(int id);
    List<Animal> findAll();
}
