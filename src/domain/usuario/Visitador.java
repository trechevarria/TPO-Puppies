package domain.usuario;

public class Visitador extends Usuario {

    private String apellido;
    private String zonaCobertura;
    private int cantidadVisitasAsignadas;

    public Visitador(int id, String nombre, String apellido, String email, String zonaCobertura) {
        super(id, nombre, email);
        this.apellido = apellido;
        this.zonaCobertura = zonaCobertura;
        this.cantidadVisitasAsignadas = 0;
    }

    public String getApellido() { return apellido; }
    public String getZonaCobertura() { return zonaCobertura; }
    public int getCantidadVisitasAsignadas() { return cantidadVisitasAsignadas; }

    public void setZonaCobertura(String zonaCobertura) { this.zonaCobertura = zonaCobertura; }
    public void setCantidadVisitasAsignadas(int cantidad) { this.cantidadVisitasAsignadas = cantidad; }
}
