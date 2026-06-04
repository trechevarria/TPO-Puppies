package application.repository;

import domain.alarma.Alarma;
import java.util.List;
import java.util.Optional;

public interface AlarmaRepository {
    Alarma save(Alarma alarma);
    Alarma update(Alarma alarma);
    boolean delete(int id);
    Optional<Alarma> findById(int id);
    List<Alarma> findByAnimalId(int animalId);

    int nextId();
}
