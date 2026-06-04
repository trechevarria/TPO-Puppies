package domain.historial;

import domain.exportacion.Exportador;
import domain.ficha.ChequeoRutina;
import domain.ficha.EvolucionMedica;
import domain.ficha.FichaMedica;
import domain.ficha.VisitaDomicilio;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Composicion 1-1 con Animal (diagrama). Agrupa todas las fichas medicas.
 */
public class HistoriaClinica {

    private int id;
    private final String animalCodigo;
    private final List<FichaMedica> fichas;

    public HistoriaClinica(String animalCodigo) {
        this.animalCodigo = animalCodigo;
        this.fichas = new ArrayList<>();
    }

    public void agregarFicha(FichaMedica ficha) {
        fichas.add(ficha);
    }

    public void agregarChequeo(ChequeoRutina chequeo) {
        agregarFicha(chequeo);
    }

    public void agregarVisitaDomicilio(VisitaDomicilio visita) {
        agregarFicha(visita);
    }

    public boolean tieneAtencionActivaEnCurso() {
        return fichas.stream()
                .filter(ChequeoRutina.class::isInstance)
                .map(ChequeoRutina.class::cast)
                .anyMatch(c -> !c.isTratamientoFinalizado());
    }

    public List<EvolucionMedica> getEvoluciones() {
        return fichas.stream()
                .filter(EvolucionMedica.class::isInstance)
                .map(EvolucionMedica.class::cast)
                .collect(Collectors.toList());
    }

    public List<VisitaDomicilio> getVisitasDomicilio() {
        return fichas.stream()
                .filter(VisitaDomicilio.class::isInstance)
                .map(VisitaDomicilio.class::cast)
                .collect(Collectors.toList());
    }

    public List<ChequeoRutina> getChequeos() {
        return fichas.stream()
                .filter(ChequeoRutina.class::isInstance)
                .map(ChequeoRutina.class::cast)
                .collect(Collectors.toList());
    }

    public void exportar(Exportador exportador) {
        exportador.exportar(this);
    }

    public int getId() { return id; }
    public String getAnimalCodigo() { return animalCodigo; }
    public List<FichaMedica> getFichas() { return fichas; }

    public void setId(int id) { this.id = id; }
}
