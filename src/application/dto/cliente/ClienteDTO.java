package application.dto.cliente;

import domain.enums.Ocupacion;

public class ClienteDTO {
    public int id;
    public String nombre;
    public String apellido;
    public String dni;
    public String direccion;
    public String email;
    public String telefono;
    public String estadoCivil;
    public Ocupacion ocupacion;
    public boolean tieneOtrasMascotas;
    public int cantidadAdopciones;
}
