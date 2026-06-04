package infrastructure;

import application.repository.AlarmaRepository;
import domain.alarma.Alarma;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryAlarmaRepository implements AlarmaRepository {

    private final Map<Integer, Alarma> store = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public Alarma save(Alarma alarma) {
        store.put(alarma.getId(), alarma);
        return alarma;
    }

    @Override
    public Alarma update(Alarma alarma) {
        store.put(alarma.getId(), alarma);
        return alarma;
    }

    @Override
    public boolean delete(int id) {
        return store.remove(id) != null;
    }

    @Override
    public Optional<Alarma> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Alarma> findByAnimalId(int animalId) {
        return store.values().stream()
                .filter(a -> a.getAnimal().getId() == animalId)
                .collect(Collectors.toList());
    }

    @Override
    public int nextId() {
        return counter.getAndIncrement();
    }
}
