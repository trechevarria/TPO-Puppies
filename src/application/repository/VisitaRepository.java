package application.repository;

import domain.adopcion.Visita;
import java.util.List;
import java.util.Optional;

public interface VisitaRepository {
    Visita save(Visita visita);
    Visita update(Visita visita);
    boolean delete(int id);
    Optional<Visita> findById(int id);
    List<Visita> findByAdopcionId(int adopcionId);
    List<Visita> findAll();
    int nextId();
}
