package infrastructure;

import application.repository.ClienteRepository;
import domain.cliente.Cliente;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryClienteRepository implements ClienteRepository {

    private final Map<Integer, Cliente> store = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public Cliente save(Cliente cliente) {
        store.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public Cliente update(Cliente cliente) {
        store.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public boolean delete(int id) {
        return store.remove(id) != null;
    }

    @Override
    public Optional<Cliente> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public int nextId() {
        return counter.getAndIncrement();
    }
}
