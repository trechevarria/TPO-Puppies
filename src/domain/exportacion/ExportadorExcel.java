package domain.exportacion;

import domain.historial.HistoriaClinica;

public class ExportadorExcel implements Exportador {

    @Override
    public void exportar(HistoriaClinica historiaClinica) {
        System.out.println("[Excel] Exportando historia clinica del animal: " + historiaClinica.getAnimalCodigo());
        System.out.println("[Excel] Total fichas: " + historiaClinica.getFichas().size());
    }
}
