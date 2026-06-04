package domain.notificacion;

import domain.alarma.Alarma;
import domain.usuario.Veterinario;
import java.util.List;

public interface NotificadorAlarma {
    void notificar(Alarma alarma, List<Veterinario> veterinarios);
}
