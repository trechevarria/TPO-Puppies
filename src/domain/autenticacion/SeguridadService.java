package domain.autenticacion;

import domain.usuario.Usuario;
import domain.usuario.Veterinario;
import domain.usuario.Visitador;

public class SeguridadService {

    private final IAutenticacion autenticacion;

    public SeguridadService(IAutenticacion autenticacion) {
        this.autenticacion = autenticacion;
    }

    public Usuario obtenerUsuario(int id) {
        return autenticacion.obtenerUsuario(id);
    }

    public Veterinario obtenerVeterinario(int id) {
        Usuario usuario = obtenerUsuario(id);
        if (!(usuario instanceof Veterinario)) {
            throw new IllegalArgumentException("El usuario " + id + " no es veterinario.");
        }
        return (Veterinario) usuario;
    }

    public Visitador obtenerVisitador(int id) {
        Usuario usuario = obtenerUsuario(id);
        if (!(usuario instanceof Visitador)) {
            throw new IllegalArgumentException("El usuario " + id + " no es visitador.");
        }
        return (Visitador) usuario;
    }
}
