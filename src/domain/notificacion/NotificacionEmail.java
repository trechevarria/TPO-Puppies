package domain.notificacion;

import domain.adopcion.Visita;
import domain.cliente.Cliente;

public class NotificacionEmail implements NotificadorVisita {

    @Override
    public void notificar(Visita visita, Cliente cliente) {
        System.out.println("[Email] Recordatorio (" + visita.getDiasAnticipacion() + " dias antes) a "
                + cliente.getEmail()
                + " - Visita: " + visita.getDiaVisita() + " " + visita.getRangoHorario());
    }
}
