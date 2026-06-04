package domain.autenticacion;

import domain.usuario.Usuario;

// Adapter: adapta el servicio externo de seguridad a la interfaz interna del sistema
// Permite que el sistema no dependa del modulo externo directamente (DIP + Indirection GRASP)
public class AdapterAutenticacion implements IAutenticacion {

    private final IServicioExterno servicio;

    public AdapterAutenticacion(IServicioExterno servicio) {
        this.servicio = servicio;
    }

    @Override
    public Usuario obtenerUsuario(int id) {
        return servicio.obtenerUsuario(id);
    }
}
