package domain.alarma;

import domain.animal.Animal;
import domain.notificacion.NotificadorAlarma;
import domain.usuario.Veterinario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Alarma {

    private int id;
    private Animal animal;
    private int periodicidadDias;
    private List<AccionAlarma> acciones;
    private LocalDate proximaEjecucion;
    private boolean activa;
    private NotificadorAlarma notificador;

    public Alarma(int id, Animal animal, int periodicidadDias, NotificadorAlarma notificador) {
        this.id = id;
        this.animal = animal;
        this.periodicidadDias = periodicidadDias;
        this.acciones = new ArrayList<>();
        this.proximaEjecucion = LocalDate.now().plusDays(periodicidadDias);
        this.activa = true;
        this.notificador = notificador;
    }

    public void agregarAccion(AccionAlarma accion) {
        acciones.add(accion);
    }

    // Dispara la alarma y notifica a todos los veterinarios via push notification
    public void disparar(List<Veterinario> veterinarios) {
        if (!activa) return;
        notificador.notificar(this, veterinarios);
        proximaEjecucion = LocalDate.now().plusDays(periodicidadDias);
    }

    public int getId() { return id; }
    public Animal getAnimal() { return animal; }
    public int getPeriodicidadDias() { return periodicidadDias; }
    public List<AccionAlarma> getAcciones() { return acciones; }
    public LocalDate getProximaEjecucion() { return proximaEjecucion; }
    public boolean isActiva() { return activa; }

    public void setPeriodicidadDias(int dias) {
        this.periodicidadDias = dias;
        this.proximaEjecucion = LocalDate.now().plusDays(dias);
    }
    public void setAcciones(List<AccionAlarma> acciones) { this.acciones = acciones; }
}
