package infrastructure;

import application.repository.AnimalRepository;
import domain.animal.Animal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAnimalRepository implements AnimalRepository {

    private final Map<Integer, Animal> store = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public Animal save(Animal animal) {
        if (animal.getId() == 0) {
            animal.setId(counter.getAndIncrement());
            animal.setCodigo("A-" + animal.getId());
        }
        store.put(animal.getId(), animal);
        return animal;
    }

    @Override
    public Animal update(Animal animal) {
        store.put(animal.getId(), animal);
        return animal;
    }

    @Override
    public boolean delete(int id) {
        return store.remove(id) != null;
    }

    @Override
    public Optional<Animal> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(store.values());
    }
}
