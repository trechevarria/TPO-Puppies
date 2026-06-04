package domain.notificacion;

import domain.adopcion.Visita;
import domain.cliente.Cliente;

// Strategy: permite agregar nuevos canales de notificacion sin modificar Adopcion (OCP)
public interface NotificadorVisita {
    void notificar(Visita visita, Cliente cliente);
}
