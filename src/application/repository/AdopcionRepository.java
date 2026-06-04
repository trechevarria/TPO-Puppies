package application.repository;

import domain.adopcion.Adopcion;
import java.util.List;
import java.util.Optional;

public interface AdopcionRepository {
    Adopcion save(Adopcion adopcion);
    boolean delete(int id);
    Optional<Adopcion> findById(int id);
    List<Adopcion> findByClienteId(int clienteId);
    int nextId();
}
