package domain.autenticacion;

import domain.usuario.Usuario;

public interface IAutenticacion {
    Usuario obtenerUsuario(int id);
}
