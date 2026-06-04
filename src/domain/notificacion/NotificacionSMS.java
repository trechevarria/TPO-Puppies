package domain.notificacion;

import domain.adopcion.Visita;
import domain.cliente.Cliente;

public class NotificacionSMS implements NotificadorVisita {

    @Override
    public void notificar(Visita visita, Cliente cliente) {
        System.out.println("[SMS] Recordatorio (" + visita.getDiasAnticipacion() + " dias antes) a "
                + cliente.getNombreApellido()
                + " - Visita: " + visita.getDiaVisita() + " " + visita.getRangoHorario());
    }
}
