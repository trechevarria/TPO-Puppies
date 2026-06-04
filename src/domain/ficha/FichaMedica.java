package domain.ficha;

import domain.usuario.Veterinario;
import java.time.LocalDate;

/**
 * Clase base del diagrama: registros polimorficos de la historia clinica.
 */
public abstract class FichaMedica {

    private int id;
    private LocalDate fecha;
    private String descripcion;
    private Veterinario veterinario;

    protected FichaMedica(String descripcion, Veterinario veterinario) {
        this.fecha = LocalDate.now();
        this.descripcion = descripcion;
        this.veterinario = veterinario;
    }

    public int getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }
    public Veterinario getVeterinario() { return veterinario; }

    public void setId(int id) { this.id = id; }
}
