package infrastructure;

import domain.autenticacion.IServicioExterno;
import domain.usuario.Usuario;
import domain.usuario.Veterinario;
import domain.usuario.Visitador;
import java.util.HashMap;
import java.util.Map;

public class ServicioExternoMock implements IServicioExterno {

    private final Map<Integer, Usuario> usuarios = new HashMap<>();

    public ServicioExternoMock registrar(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
        return this;
    }

    @Override
    public Usuario obtenerUsuario(int id) {
        Usuario usuario = usuarios.get(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado en modulo de seguridad: " + id);
        }
        return usuario;
    }

    public static ServicioExternoMock conDatosDePrueba() {
        ServicioExternoMock mock = new ServicioExternoMock();
        mock.registrar(new Veterinario(1, "Carlos", "Martinez", "vet@mail.com", "MP-123"));
        mock.registrar(new Visitador(2, "Laura", "Gomez", "laura@mail.com", "CABA Norte"));
        return mock;
    }
}
