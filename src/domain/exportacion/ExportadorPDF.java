package domain.exportacion;

import domain.historial.HistoriaClinica;

public class ExportadorPDF implements Exportador {

    @Override
    public void exportar(HistoriaClinica historiaClinica) {
        System.out.println("[PDF] Exportando historia clinica del animal: " + historiaClinica.getAnimalCodigo());
        System.out.println("[PDF] Evoluciones: " + historiaClinica.getEvoluciones().size());
        System.out.println("[PDF] Chequeos: " + historiaClinica.getChequeos().size());
        System.out.println("[PDF] Visitas domicilio: " + historiaClinica.getVisitasDomicilio().size());
    }
}
