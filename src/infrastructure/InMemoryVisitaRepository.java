package infrastructure;

import application.repository.VisitaRepository;
import domain.adopcion.Visita;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryVisitaRepository implements VisitaRepository {

    private final Map<Integer, Visita> store = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public Visita save(Visita visita) {
        store.put(visita.getId(), visita);
        return visita;
    }

    @Override
    public Visita update(Visita visita) {
        store.put(visita.getId(), visita);
        return visita;
    }

    @Override
    public boolean delete(int id) {
        return store.remove(id) != null;
    }

    @Override
    public Optional<Visita> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Visita> findByAdopcionId(int adopcionId) {
        return store.values().stream()
                .filter(v -> v.getAdopcionId() == adopcionId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Visita> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public int nextId() {
        return counter.getAndIncrement();
    }
}
