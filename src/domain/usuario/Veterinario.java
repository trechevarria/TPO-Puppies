package domain.usuario;

public class Veterinario extends Usuario {

    private String apellido;
    private String matricula;

    public Veterinario(int id, String nombre, String apellido, String email, String matricula) {
        super(id, nombre, email);
        this.apellido = apellido;
        this.matricula = matricula;
    }

    public String getApellido() { return apellido; }
    public String getMatricula() { return matricula; }

    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}
