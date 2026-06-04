package application.repository;

import domain.cliente.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente save(Cliente cliente);
    Cliente update(Cliente cliente);
    boolean delete(int id);
    Optional<Cliente> findById(int id);
    List<Cliente> findAll();
    int nextId();
}
