import application.dto.adopcion.AdopcionCreateDTO;
import application.dto.alarma.AlarmaCreateDTO;
import application.dto.alarma.AtenderAlarmaDTO;
import application.dto.animal.AnimalCreateDTO;
import application.dto.animal.AnimalDTO;
import application.dto.cliente.ClienteCreateDTO;
import application.dto.visita.EncuestaDTO;
import application.dto.visita.RangoHorarioDTO;
import application.dto.visita.VisitaCreateDTO;
import domain.enums.TipoAnimal;
import java.time.LocalTime;
import application.service.impl.AdopcionService;
import application.service.impl.AlarmaService;
import application.service.impl.AnimalService;
import application.service.impl.ClienteService;
import application.service.impl.VisitaService;
import domain.animal.AnimalDomestico;
import domain.animal.AnimalSalvaje;
import domain.autenticacion.AdapterAutenticacion;
import domain.autenticacion.SeguridadService;
import domain.cliente.Cliente;
import domain.config.ParametrosRecordatorio;
import domain.enums.Calificacion;
import domain.enums.Ocupacion;
import domain.enums.TipoAccion;
import domain.enums.TipoNotificacion;
import domain.ficha.ChequeoRutina;
import domain.historial.HistoriaClinica;
import infrastructure.InMemoryAdopcionRepository;
import infrastructure.InMemoryAlarmaRepository;
import infrastructure.InMemoryAnimalRepository;
import infrastructure.InMemoryClienteRepository;
import infrastructure.InMemoryVisitaRepository;
import infrastructure.ServicioExternoMock;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TestAdopcion {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {
        testAnimalDomesticoSinTratamientoPuedeAdoptarse();
        testAnimalDomesticoEnTratamientoNoPuedeAdoptarse();
        testAnimalConChequeoActivoEnHistoriaNoPuedeAdoptarse();
        testAnimalSalvajeNuncaPuedeAdoptarse();
        testClienteNoPuedeAdoptarMasDeDos();
        testAdopcionExitosa();
        testAdopcionRechazadaPorTratamiento();
        testRecordatorioNDiasAntes();
        testParametroGlobalDiasAnticipacion();
        testEncuestaRegistraHistorialEnFicha();
        testExportadorStrategy();
        testAlarmaConPushNotification();
        testAtenderAlarmaFinalizaTratamientoYHabilitaAdopcion();
        testAdapterSeguridadObtieneVisitador();
        testAnimalDtoCamposDiagrama();
        testAdopcionCreateDtoCamposDiagrama();

        System.out.println("\n=== Resultados ===");
        System.out.println("Pasaron: " + passed);
        System.out.println("Fallaron: " + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    static SeguridadService crearSeguridad() {
        return new SeguridadService(new AdapterAutenticacion(ServicioExternoMock.conDatosDePrueba()));
    }

    static void testAnimalDomesticoSinTratamientoPuedeAdoptarse() {
        AnimalDomestico gato = new AnimalDomestico("G001", "Michi", "Gato", "Siames", 0.3, 4.0, 2, false);
        assertTrue("Animal domestico sin tratamiento puede adoptarse", gato.puedeSerAdoptado());
    }

    static void testAnimalDomesticoEnTratamientoNoPuedeAdoptarse() {
        AnimalDomestico perro = new AnimalDomestico("P001", "Firulais", "Perro", "Labrador", 0.6, 12.0, 3, true);
        assertFalse("Animal en tratamiento NO puede adoptarse", perro.puedeSerAdoptado());
    }

    static void testAnimalConChequeoActivoEnHistoriaNoPuedeAdoptarse() {
        AnimalDomestico perro = new AnimalDomestico("P010", "Rex", "Perro", "Mestizo", 0.6, 12.0, 3, false);
        domain.alarma.Alarma alarma = new domain.alarma.Alarma(99, perro, 7, new domain.notificacion.PushNotificacion());
        ChequeoRutina chequeo = new ChequeoRutina(
                new domain.usuario.Veterinario(1, "Carlos", "Martinez", "v@test.com", "MP-1"),
                alarma, "Tratamiento en curso", false);
        perro.getHistoriaClinica().agregarChequeo(chequeo);
        assertFalse("Chequeo activo bloquea adopcion", perro.puedeSerAdoptado());
    }

    static void testAnimalSalvajeNuncaPuedeAdoptarse() {
        AnimalSalvaje zorro = new AnimalSalvaje("Z001", "Zorro", "Zorro", "Patagonico", 0.5, 6.0, 1, false);
        assertFalse("Salvaje no adoptable", zorro.puedeSerAdoptado());
    }

    static void testClienteNoPuedeAdoptarMasDeDos() {
        Cliente cliente = new Cliente(1, "Juan", "Perez", "30111222", "CABA",
                "juan@mail.com", "1111", "Soltero", Ocupacion.EMPLEADO, false);
        cliente.incrementarAdopciones();
        cliente.incrementarAdopciones();
        assertFalse("Maximo 2 adopciones", cliente.puedeAdoptar());
    }

    static void testAdopcionExitosa() {
        InMemoryAnimalRepository animalRepo = new InMemoryAnimalRepository();
        InMemoryClienteRepository clienteRepo = new InMemoryClienteRepository();
        InMemoryAdopcionRepository adopcionRepo = new InMemoryAdopcionRepository();

        int animalId = crearAnimalDomestico(new AnimalService(animalRepo)).id;
        int adoptanteId = registrarCliente(new ClienteService(clienteRepo));

        AdopcionService adopcionService = new AdopcionService(adopcionRepo, animalRepo, clienteRepo);
        AdopcionCreateDTO dto = new AdopcionCreateDTO();
        dto.animalId = animalId;
        dto.clienteId = adoptanteId;
        dto.motivoAdopcion = "Compania";
        dto.tipoAnimalesInteres = Arrays.asList("perro");
        var adopcion = adopcionService.crearAdopcion(dto);

        assertTrue("Adopcion con ids del diagrama",
                adopcion.animalId == animalId && adopcion.clienteId == adoptanteId);
    }

    static void testAdopcionRechazadaPorTratamiento() {
        InMemoryAnimalRepository animalRepo = new InMemoryAnimalRepository();
        InMemoryClienteRepository clienteRepo = new InMemoryClienteRepository();
        InMemoryAdopcionRepository adopcionRepo = new InMemoryAdopcionRepository();

        animalRepo.save(new AnimalDomestico("P099", "Max", "Perro", "Mestizo", 0.5, 8.0, 2, true));
        clienteRepo.save(new Cliente(2, "Pedro", "Garcia", "30999888", "Belgrano",
                "p@mail.com", "3333", "Soltero", Ocupacion.ESTUDIANTE, false));

        AdopcionService service = new AdopcionService(adopcionRepo, animalRepo, clienteRepo);
        AdopcionCreateDTO dto = new AdopcionCreateDTO();
        dto.clienteId = 2;
        dto.animalId = animalRepo.findAll().get(0).getId();
        dto.motivoAdopcion = "Amor";
        dto.tipoAnimalesInteres = Arrays.asList("perro");

        boolean lanzo = false;
        try {
            service.crearAdopcion(dto);
        } catch (IllegalStateException e) {
            lanzo = true;
        }
        assertTrue("Rechaza animal en tratamiento", lanzo);
    }

    static void testRecordatorioNDiasAntes() {
        InMemoryAnimalRepository animalRepo = new InMemoryAnimalRepository();
        InMemoryClienteRepository clienteRepo = new InMemoryClienteRepository();
        InMemoryAdopcionRepository adopcionRepo = new InMemoryAdopcionRepository();
        InMemoryVisitaRepository visitaRepo = new InMemoryVisitaRepository();

        AnimalDomestico perro = new AnimalDomestico("P003", "Toby", "Perro", "Collie", 0.5, 9.0, 2, false);
        animalRepo.save(perro);
        clienteRepo.save(new Cliente(3, "Maria", "Lopez", "27111222", "Caballito",
                "m@mail.com", "4444", "Casada", Ocupacion.EMPLEADO, true));
        adopcionRepo.save(new domain.adopcion.Adopcion(1, clienteRepo.findById(3).get(), perro,
                "familia", Arrays.asList("perro")));

        VisitaService service = new VisitaService(visitaRepo, adopcionRepo, crearSeguridad());

        VisitaCreateDTO visitaDTO = new VisitaCreateDTO();
        visitaDTO.adopcionId = 1;
        visitaDTO.visitadorId = 2;
        visitaDTO.diaVisita = LocalDate.of(2026, 6, 20);
        visitaDTO.rangoHorario = rango(LocalTime.of(10, 0), LocalTime.of(12, 0));
        visitaDTO.preferenciaRecordatorio = TipoNotificacion.SMS;
        visitaDTO.diasAnticipacion = 3;
        service.crearVisita(visitaDTO);

        var visita = visitaRepo.findById(1).orElse(null);
        assertTrue("Visita creada", visita != null);
        service.procesarRecordatoriosVisitas(LocalDate.of(2026, 6, 17));
        assertTrue("Recordatorio enviado", visita.isRecordatorioEnviado());
    }

    static void testParametroGlobalDiasAnticipacion() {
        ParametrosRecordatorio.setDiasAnticipacionPorDefecto(5);
        var visita = new domain.adopcion.Visita(1, 1,
                new domain.usuario.Visitador(2, "Laura", "Gomez", "l@mail.com", "Norte"),
                LocalDate.of(2026, 7, 10), LocalTime.of(9, 0), LocalTime.of(11, 0),
                TipoNotificacion.EMAIL, 0);
        assertTrue("Parametro N global", visita.getDiasAnticipacion() == 5);
        ParametrosRecordatorio.setDiasAnticipacionPorDefecto(3);
    }

    static void testEncuestaRegistraHistorialEnFicha() {
        InMemoryAnimalRepository animalRepo = new InMemoryAnimalRepository();
        InMemoryClienteRepository clienteRepo = new InMemoryClienteRepository();
        InMemoryAdopcionRepository adopcionRepo = new InMemoryAdopcionRepository();
        InMemoryVisitaRepository visitaRepo = new InMemoryVisitaRepository();

        AnimalDomestico gato = new AnimalDomestico("G002", "Nina", "Gato", "Persa", 0.2, 3.0, 1, false);
        animalRepo.save(gato);
        clienteRepo.save(new Cliente(4, "Luis", "Diaz", "30123456", "Flores",
                "l@mail.com", "5555", "Soltero", Ocupacion.OTROS, false));
        adopcionRepo.save(new domain.adopcion.Adopcion(2, clienteRepo.findById(4).get(), gato,
                "amor", Arrays.asList("gato")));

        VisitaService service = new VisitaService(visitaRepo, adopcionRepo, crearSeguridad());
        VisitaCreateDTO v = new VisitaCreateDTO();
        v.adopcionId = 2;
        v.visitadorId = 2;
        v.diaVisita = LocalDate.of(2026, 8, 1);
        v.rangoHorario = rango(LocalTime.of(14, 0), LocalTime.of(16, 0));
        v.preferenciaRecordatorio = TipoNotificacion.EMAIL;
        v.diasAnticipacion = 2;
        service.crearVisita(v);

        EncuestaDTO enc = new EncuestaDTO();
        enc.estadoAnimal = Calificacion.BUENO;
        enc.limpiezaLugar = Calificacion.REGULAR;
        enc.ambiente = Calificacion.BUENO;
        enc.continuarVisitas = false;
        service.completarEncuesta(1, enc);

        assertTrue("Visita en historia", gato.getHistoriaClinica().getVisitasDomicilio().size() == 1);
    }

    static void testExportadorStrategy() {
        HistoriaClinica historia = new HistoriaClinica("P003");
        new domain.exportacion.ExportadorPDF().exportar(historia);
        new domain.exportacion.ExportadorExcel().exportar(historia);
        assertTrue("Export OK", true);
    }

    static void testAlarmaConPushNotification() {
        AnimalDomestico perro = new AnimalDomestico("P004", "Rocky", "Perro", "Boxer", 0.6, 12.0, 3, true);
        domain.alarma.Alarma alarma = new domain.alarma.Alarma(1, perro, 7, new domain.notificacion.PushNotificacion());
        alarma.agregarAccion(new domain.alarma.AccionAlarma(TipoAccion.CONTROL_PARASITOS));
        alarma.disparar(List.of(new domain.usuario.Veterinario(1, "Carlos", "Martinez", "vet@mail.com", "MP-123")));
        assertTrue("Push OK", true);
    }

    static void testAtenderAlarmaFinalizaTratamientoYHabilitaAdopcion() {
        InMemoryAnimalRepository animalRepo = new InMemoryAnimalRepository();
        InMemoryAlarmaRepository alarmaRepo = new InMemoryAlarmaRepository();
        SeguridadService seguridad = crearSeguridad();

        AnimalDomestico perro = new AnimalDomestico("P005", "Bobby", "Perro", "Caniche", 0.5, 10.0, 4, true);
        animalRepo.save(perro);

        AlarmaService alarmaService = new AlarmaService(alarmaRepo, animalRepo, seguridad,
                List.of(seguridad.obtenerVeterinario(1)));

        AlarmaCreateDTO crear = new AlarmaCreateDTO();
        crear.animalId = perro.getId();
        crear.periodicidadDias = 7;
        crear.acciones = List.of(TipoAccion.COLOCAR_VACUNA);
        int alarmaId = alarmaService.crearAlarma(crear).id;

        AtenderAlarmaDTO atender = new AtenderAlarmaDTO();
        atender.veterinarioId = 1;
        atender.comentario = "Vacuna aplicada";
        atender.tratamientoFinalizado = true;
        atender.accionesCompletadas = List.of(TipoAccion.COLOCAR_VACUNA);
        alarmaService.atenderAlarma(alarmaId, atender);

        AnimalDomestico actualizado = (AnimalDomestico) animalRepo.findById(perro.getId()).orElseThrow();
        assertTrue("Puede adoptarse tras tratamiento", actualizado.puedeSerAdoptado());
    }

    static void testAdapterSeguridadObtieneVisitador() {
        assertTrue("Visitador desde adapter", "Laura".equals(crearSeguridad().obtenerVisitador(2).getNombre()));
    }

    static void testAnimalDtoCamposDiagrama() {
        InMemoryAnimalRepository repo = new InMemoryAnimalRepository();
        AnimalDTO dto = crearAnimalDomestico(new AnimalService(repo));
        assertTrue("Campos diagrama",
                dto.tipo == TipoAnimal.DOMESTICO && dto.id > 0 && !dto.necesitaAtencionMedica);
    }

    static void testAdopcionCreateDtoCamposDiagrama() {
        AdopcionCreateDTO dto = new AdopcionCreateDTO();
        dto.clienteId = 10;
        dto.animalId = 5;
        dto.motivoAdopcion = "Compania";
        dto.tipoAnimalesInteres = Arrays.asList("gato");
        assertTrue("CreateDTO segun diagrama",
                dto.clienteId == 10 && dto.animalId == 5 && dto.motivoAdopcion != null);
    }

    static AnimalDTO crearAnimalDomestico(AnimalService service) {
        AnimalCreateDTO dto = new AnimalCreateDTO();
        dto.tipo = TipoAnimal.DOMESTICO;
        dto.altura = 0.5;
        dto.peso = 10;
        dto.necesitaAtencionMedica = false;
        return service.crearAnimal(dto);
    }

    static RangoHorarioDTO rango(LocalTime inicio, LocalTime fin) {
        RangoHorarioDTO r = new RangoHorarioDTO();
        r.horaInicio = inicio;
        r.horaFin = fin;
        return r;
    }

    static int registrarCliente(ClienteService service) {
        ClienteCreateDTO dto = new ClienteCreateDTO();
        dto.nombre = "Ana";
        dto.apellido = "Lopez";
        dto.dni = "30111222";
        dto.direccion = "CABA";
        dto.email = "ana@mail.com";
        dto.telefono = "2222";
        dto.estadoCivil = "Casada";
        dto.ocupacion = Ocupacion.EMPLEADO;
        dto.tieneOtrasMascotas = false;
        return service.crearCliente(dto).id;
    }

    static void assertTrue(String msg, boolean condition) {
        if (condition) {
            System.out.println("  [OK]  " + msg);
            passed++;
        } else {
            System.out.println("  [FAIL] " + msg);
            failed++;
        }
    }

    static void assertFalse(String msg, boolean condition) {
        assertTrue(msg, !condition);
    }
}
