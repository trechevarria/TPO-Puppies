package domain.config;

/**
 * Parametro global N: dias de anticipacion por defecto para recordatorios de visita.
 * Valor configurable sin recompilar (enunciado: N ajustable por parametro).
 */
public final class ParametrosRecordatorio {

    private static int diasAnticipacionPorDefecto = 3;

    private ParametrosRecordatorio() {
    }

    public static int getDiasAnticipacionPorDefecto() {
        return diasAnticipacionPorDefecto;
    }

    public static void setDiasAnticipacionPorDefecto(int dias) {
        if (dias < 0) {
            throw new IllegalArgumentException("Los dias de anticipacion no pueden ser negativos.");
        }
        diasAnticipacionPorDefecto = dias;
    }
}
