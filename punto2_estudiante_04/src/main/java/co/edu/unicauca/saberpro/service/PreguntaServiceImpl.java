package co.edu.unicauca.saberpro.service;

import co.edu.unicauca.saberpro.model.Pregunta;
import co.edu.unicauca.saberpro.pipeline.ValidadorPipeline;
import co.edu.unicauca.saberpro.repository.IPreguntaRepository;
import java.util.List;
import java.util.stream.Collectors;

public class PreguntaServiceImpl implements IPreguntaService {
    private final IPreguntaRepository repository;
    private final ValidadorPipeline pipeline;

    public PreguntaServiceImpl(IPreguntaRepository repository, ValidadorPipeline pipeline) {
        this.repository = repository;
        this.pipeline = pipeline;
    }

    // CORRECCIÓN 3: la firma ahora coincide con IPreguntaService (registrarPregunta) y se agrega @Override
    @Override
    public boolean registrarPregunta(Pregunta pregunta) {
        if (pipeline.ejecutar(pregunta)) {
            return repository.guardar(pregunta);
        }
        return false;
    }

    @Override
    public List<Pregunta> obtenerTodas() {
        return repository.listar();
    }

    // PUNTO 2: consulta filtrada por estado ("Borrador", "Publicada")
    //Agregue el método 'List<Pregunta> obtenerPorEstado(String estado)' en 'IPreguntaService' y su implementación en 'PreguntaServiceImpl'.
    @Override
    public List<Pregunta> obtenerPorEstado(String estado) {
        return repository.listar().stream()
                .filter(p -> p.getEstado() != null && p.getEstado().equals(estado))
                .collect(Collectors.toList());
    }
}
