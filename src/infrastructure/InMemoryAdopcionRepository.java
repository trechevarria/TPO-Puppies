package infrastructure;

import application.repository.AdopcionRepository;
import domain.adopcion.Adopcion;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryAdopcionRepository implements AdopcionRepository {

    private final Map<Integer, Adopcion> store = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public Adopcion save(Adopcion adopcion) {
        store.put(adopcion.getId(), adopcion);
        return adopcion;
    }

    @Override
    public boolean delete(int id) {
        return store.remove(id) != null;
    }

    @Override
    public Optional<Adopcion> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Adopcion> findByClienteId(int clienteId) {
        return store.values().stream()
                .filter(a -> a.getCliente().getId() == clienteId)
                .collect(Collectors.toList());
    }

    @Override
    public int nextId() {
        return counter.getAndIncrement();
    }
}
