package co.edu.unicauca.saberpro;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.unicauca.saberpro.model.Pregunta;
import co.edu.unicauca.saberpro.pipeline.*;
import co.edu.unicauca.saberpro.repository.PreguntaRepositoryImpl;
import co.edu.unicauca.saberpro.service.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsultaPorEstadoTest {

    private IPreguntaService service;
    private static final List<String> OPC = Arrays.asList("A", "B", "C", "D");

    @BeforeEach
    void setUp() {
        ValidadorPipeline pipeline = new ValidadorPipeline();
        pipeline.addFiltro(new FiltroEnunciado());
        pipeline.addFiltro(new FiltroOpciones());
        service = new PreguntaServiceImpl(new PreguntaRepositoryImpl(), pipeline);
        service.registrarPregunta(new Pregunta(1, "¿Uno?", OPC, "A", "Borrador"));
        service.registrarPregunta(new Pregunta(2, "¿Dos?", OPC, "A", "Publicada"));
        service.registrarPregunta(new Pregunta(3, "¿Tres?", OPC, "A", "Borrador"));
    }

    @Test
    void filtraBorradores() {
        List<Pregunta> r = service.obtenerPorEstado("Borrador");
        assertEquals(2, r.size());
        assertTrue(r.stream().allMatch(p -> "Borrador".equals(p.getEstado())));
    }

    @Test
    void filtraPublicadas() {
        List<Pregunta> r = service.obtenerPorEstado("Publicada");
        assertEquals(1, r.size());
        assertEquals(2, r.get(0).getId());
    }

    @Test
    void estadoInexistenteRetornaListaVacia() {
        assertTrue(service.obtenerPorEstado("Archivada").isEmpty());
    }
}
