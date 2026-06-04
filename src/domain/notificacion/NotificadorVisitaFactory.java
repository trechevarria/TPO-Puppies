package domain.notificacion;

import domain.enums.TipoNotificacion;

/**
 * Factory para Strategy de recordatorios (OCP: nuevos canales sin modificar servicios).
 */
public final class NotificadorVisitaFactory {

    private NotificadorVisitaFactory() {
    }

    public static NotificadorVisita crear(TipoNotificacion tipo) {
        if (tipo == null) {
            return new NotificacionEmail();
        }
        return switch (tipo) {
            case SMS -> new NotificacionSMS();
            case WHATSAPP -> new NotificacionWhatsApp();
            case EMAIL -> new NotificacionEmail();
        };
    }
}
