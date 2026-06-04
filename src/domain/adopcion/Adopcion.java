package domain.adopcion;

import domain.animal.Animal;
import domain.cliente.Cliente;
import domain.enums.TipoNotificacion;
import domain.notificacion.NotificadorVisita;
import domain.notificacion.NotificadorVisitaFactory;
import java.time.LocalDate;
import java.util.List;

public class Adopcion {

    private int id;
    private Cliente cliente;
    private Animal animal;
    private String motivoAdopcion;
    private List<String> tipoAnimalesInteres;
    private LocalDate fechaAdopcion;

    public Adopcion(int id, Cliente cliente, Animal animal,
                    String motivoAdopcion, List<String> tipoAnimalesInteres) {
        this.id = id;
        this.cliente = cliente;
        this.animal = animal;
        this.motivoAdopcion = motivoAdopcion;
        this.tipoAnimalesInteres = tipoAnimalesInteres;
        this.fechaAdopcion = LocalDate.now();
    }

    public void enviarRecordatorio(Visita visita) {
        NotificadorVisita notificador = NotificadorVisitaFactory.crear(visita.getPreferenciaRecordatorio());
        notificador.notificar(visita, cliente);
        visita.marcarRecordatorioEnviado();
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Animal getAnimal() { return animal; }
    public String getMotivoAdopcion() { return motivoAdopcion; }
    public List<String> getTipoAnimalesInteres() { return tipoAnimalesInteres; }
    public LocalDate getFechaAdopcion() { return fechaAdopcion; }
}
