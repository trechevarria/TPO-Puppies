package domain.exportacion;

import domain.historial.HistoriaClinica;

// Strategy: nuevos formatos sin modificar HistoriaClinica (OCP)
public interface Exportador {
    void exportar(HistoriaClinica historiaClinica);
}
