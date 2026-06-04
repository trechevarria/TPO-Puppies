package domain.cliente;

import domain.enums.Ocupacion;

/** Adoptante del diagrama (Cliente / Adoptante). */
public class Cliente {

    private static final int MAX_ADOPCIONES = 2;

    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String direccion;
    private String email;
    private String telefono;
    private String estadoCivil;
    private Ocupacion ocupacion;
    private boolean tieneOtrasMascotas;
    private int cantidadDeAdopciones;

    public Cliente(int id, String nombre, String apellido, String dni, String direccion,
                   String email, String telefono, String estadoCivil, Ocupacion ocupacion,
                   boolean tieneOtrasMascotas) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.estadoCivil = estadoCivil;
        this.ocupacion = ocupacion;
        this.tieneOtrasMascotas = tieneOtrasMascotas;
        this.cantidadDeAdopciones = 0;
    }

    public boolean puedeAdoptar() {
        return cantidadDeAdopciones < MAX_ADOPCIONES;
    }

    public void incrementarAdopciones() {
        cantidadDeAdopciones++;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getDni() { return dni; }
    public String getDireccion() { return direccion; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getEstadoCivil() { return estadoCivil; }
    public Ocupacion getOcupacion() { return ocupacion; }
    public boolean isTieneOtrasMascotas() { return tieneOtrasMascotas; }
    public int getCantidadDeAdopciones() { return cantidadDeAdopciones; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setDni(String dni) { this.dni = dni; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }
    public void setOcupacion(Ocupacion ocupacion) { this.ocupacion = ocupacion; }
    public void setTieneOtrasMascotas(boolean tieneOtrasMascotas) {
        this.tieneOtrasMascotas = tieneOtrasMascotas;
    }
}
