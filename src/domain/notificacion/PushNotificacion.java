package domain.notificacion;

import domain.alarma.Alarma;
import domain.usuario.Veterinario;
import java.util.List;

public class PushNotificacion implements NotificadorAlarma {

    @Override
    public void notificar(Alarma alarma, List<Veterinario> veterinarios) {
        for (Veterinario vet : veterinarios) {
            System.out.println("[Push] Alerta a Dr/a " + vet.getNombre()
                    + " - Alarma disparada para animal id: " + alarma.getAnimal().getId()
                    + " | Acciones: " + alarma.getAcciones().size());
        }
    }
}
