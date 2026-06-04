package application.service.impl;

import application.dto.visita.EncuestaDTO;
import application.dto.visita.RangoHorarioDTO;
import application.dto.visita.VisitaCreateDTO;
import application.dto.visita.VisitaDTO;
import application.dto.visita.VisitaUpdateDTO;
import application.repository.AdopcionRepository;
import application.repository.VisitaRepository;
import application.service.IVisitaService;
import domain.adopcion.Adopcion;
import domain.adopcion.Visita;
import domain.autenticacion.SeguridadService;
import domain.encuesta.Encuesta;
import domain.ficha.VisitaDomicilio;
import domain.usuario.Visitador;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class VisitaService implements IVisitaService {

    private final VisitaRepository visitaRepository;
    private final AdopcionRepository adopcionRepository;
    private final SeguridadService seguridadService;

    public VisitaService(VisitaRepository visitaRepository,
                         AdopcionRepository adopcionRepository,
                         SeguridadService seguridadService) {
        this.visitaRepository = visitaRepository;
        this.adopcionRepository = adopcionRepository;
        this.seguridadService = seguridadService;
    }

    public int crearVisita(VisitaCreateDTO dto) {
        adopcionRepository.findById(dto.adopcionId)
                .orElseThrow(() -> new IllegalArgumentException("Adopcion no encontrada: " + dto.adopcionId));

        if (dto.rangoHorario == null || dto.rangoHorario.horaInicio == null || dto.rangoHorario.horaFin == null) {
            throw new IllegalArgumentException("Debe indicar rango horario (horaInicio y horaFin).");
        }

        Visitador visitador = seguridadService.obtenerVisitador(dto.visitadorId);

        Visita visita = new Visita(
                visitaRepository.nextId(),
                dto.adopcionId,
                visitador,
                dto.diaVisita,
                dto.rangoHorario.horaInicio,
                dto.rangoHorario.horaFin,
                dto.preferenciaRecordatorio,
                dto.diasAnticipacion);

        visitaRepository.save(visita);
        return visita.getId();
    }

    public boolean actualizarVisita(int id, VisitaUpdateDTO dto) {
        Visita visita = visitaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visita no encontrada: " + id));
        if (dto.rangoHorario == null || dto.rangoHorario.horaInicio == null || dto.rangoHorario.horaFin == null) {
            throw new IllegalArgumentException("Debe indicar rango horario (horaInicio y horaFin).");
        }
        visita.setFecha(dto.diaVisita);
        visita.setHoraInicio(dto.rangoHorario.horaInicio);
        visita.setHoraFin(dto.rangoHorario.horaFin);
        visita.setPreferenciaRecordatorio(dto.preferenciaRecordatorio);
        visita.setDiasAnticipacion(dto.diasAnticipacion);
        visitaRepository.update(visita);
        return true;
    }

    public boolean eliminarVisita(int id) {
        return visitaRepository.delete(id);
    }

    public VisitaDTO obtenerVisita(int id) {
        return visitaRepository.findById(id)
                .map(this::toVisitaDTO)
                .orElseThrow(() -> new IllegalArgumentException("Visita no encontrada: " + id));
    }

    public List<VisitaDTO> listarVisitasPorAdopcion(int adopcionId) {
        return visitaRepository.findByAdopcionId(adopcionId).stream()
                .map(this::toVisitaDTO)
                .collect(Collectors.toList());
    }

    public boolean completarEncuesta(int id, EncuestaDTO dto) {
        Visita visita = visitaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visita no encontrada: " + id));

        Encuesta encuesta = new Encuesta(
                dto.estadoAnimal,
                dto.limpiezaLugar,
                dto.ambiente);
        visita.completarEncuesta(encuesta, dto.continuarVisitas,
                dto.continuarVisitas ? "Seguimiento activo" : "Seguimiento finalizado");

        Adopcion adopcion = adopcionRepository.findById(visita.getAdopcionId())
                .orElseThrow(() -> new IllegalArgumentException("Adopcion no encontrada"));

        VisitaDomicilio registro = new VisitaDomicilio(visita.getVisitador(), encuesta, dto.continuarVisitas);
        adopcion.getAnimal().getHistoriaClinica().agregarVisitaDomicilio(registro);

        visitaRepository.update(visita);
        return true;
    }

    public void procesarRecordatoriosVisitas(LocalDate hoy) {
        for (Visita visita : visitaRepository.findAll()) {
            if (!visita.debeEnviarRecordatorio(hoy)) {
                continue;
            }
            Adopcion adopcion = adopcionRepository.findById(visita.getAdopcionId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Adopcion no encontrada para visita: " + visita.getId()));
            adopcion.enviarRecordatorio(visita);
            visitaRepository.update(visita);
        }
    }

    private VisitaDTO toVisitaDTO(Visita visita) {
        VisitaDTO dto = new VisitaDTO();
        dto.id = visita.getId();
        dto.adopcionId = visita.getAdopcionId();
        dto.visitadorId = visita.getVisitador().getId();
        dto.diaVisita = visita.getFecha();
        dto.rangoHorario = toRangoHorarioDTO(visita.getHoraInicio(), visita.getHoraFin());
        dto.preferenciaRecordatorio = visita.getPreferenciaRecordatorio();
        dto.diasAnticipacion = visita.getDiasAnticipacion();
        dto.continuarVisitas = visita.isContinuarVisitas();
        if (visita.getEncuesta() != null) {
            dto.encuesta = toEncuestaDTO(visita.getEncuesta(), visita.isContinuarVisitas());
        }
        return dto;
    }

    private RangoHorarioDTO toRangoHorarioDTO(LocalTime inicio, LocalTime fin) {
        RangoHorarioDTO dto = new RangoHorarioDTO();
        dto.horaInicio = inicio;
        dto.horaFin = fin;
        return dto;
    }

    private EncuestaDTO toEncuestaDTO(Encuesta encuesta, boolean continuarVisitas) {
        EncuestaDTO dto = new EncuestaDTO();
        dto.estadoAnimal = encuesta.getCalificacionGeneral();
        dto.limpiezaLugar = encuesta.getCalificacionLimpieza();
        dto.ambiente = encuesta.getCalificacionAmbiente();
        dto.continuarVisitas = continuarVisitas;
        return dto;
    }
}
