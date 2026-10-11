package co.edu.unicauca.saberpro;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.unicauca.saberpro.model.Pregunta;
import co.edu.unicauca.saberpro.pipeline.*;
import co.edu.unicauca.saberpro.repository.*;
import co.edu.unicauca.saberpro.service.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PreguntaServiceTest {

    private IPreguntaService service;

    private static final List<String> CUATRO =
            Arrays.asList("A. Uno", "B. Dos", "C. Tres", "D. Cuatro");

    @BeforeEach
    void setUp() {
        ValidadorPipeline pipeline = new ValidadorPipeline();
        pipeline.addFiltro(new FiltroEnunciado());
        pipeline.addFiltro(new FiltroOpciones());
        service = new PreguntaServiceImpl(new PreguntaRepositoryImpl(), pipeline);
    }

    @Test
    void registraPreguntaValida() {
        Pregunta p = new Pregunta(1, "¿Enunciado válido?", CUATRO, "A. Uno", "Borrador");
        assertTrue(service.registrarPregunta(p));
        assertEquals(1, service.obtenerTodas().size());
    }

    @Test
    void rechazaEnunciadoVacio() {
        Pregunta p = new Pregunta(2, "   ", CUATRO, "A. Uno", "Borrador");
        assertFalse(service.registrarPregunta(p));
        assertEquals(0, service.obtenerTodas().size());
    }

    @Test
    void rechazaPreguntaConTresOpciones() {
        Pregunta p = new Pregunta(3, "¿Enunciado?", Arrays.asList("A", "B", "C"), "A", "Borrador");
        assertFalse(service.registrarPregunta(p));
    }

    @Test
    void opcionesNulasSeInicializanYSeRechazan() {
        Pregunta p = new Pregunta(4, "¿Enunciado?", null, "A", "Borrador");
        assertNotNull(p.getOpciones());
        assertFalse(service.registrarPregunta(p));
    }
}
