package domain.autenticacion;

import domain.usuario.Usuario;

// Contrato del modulo de seguridad externo (equipo de seguridad)
public interface IServicioExterno {
    Usuario obtenerUsuario(int id);
}
