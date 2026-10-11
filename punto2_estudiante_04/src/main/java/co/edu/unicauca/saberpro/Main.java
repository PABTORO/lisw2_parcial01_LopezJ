package co.edu.unicauca.saberpro;

import co.edu.unicauca.saberpro.model.Pregunta;
import co.edu.unicauca.saberpro.pipeline.*;
import co.edu.unicauca.saberpro.repository.*;
import co.edu.unicauca.saberpro.service.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== BANCO DE PREGUNTAS SABER PRO ===");

        IPreguntaRepository repository = new PreguntaRepositoryImpl();
        ValidadorPipeline pipeline = new ValidadorPipeline();
        pipeline.addFiltro(new FiltroEnunciado());
        pipeline.addFiltro(new FiltroOpciones());
        IPreguntaService service = new PreguntaServiceImpl(repository, pipeline);

        Pregunta p1 = new Pregunta(
            1,
            "¿Cuál es el objetivo principal del patrón Pipes and Filters?",
            Arrays.asList("A. Dividir proceso en etapas", "B. Crear BD", "C. Usar MVC", "D. Ninguna"),
            "A. Dividir proceso en etapas",
            "Borrador"
        );
        Pregunta p2 = new Pregunta(
            2,
            "¿Qué principio SOLID invierte las dependencias?",
            Arrays.asList("A. SRP", "B. OCP", "C. DIP", "D. LSP"),
            "C. DIP",
            "Publicada"
        );

        System.out.println(service.registrarPregunta(p1) ? " Registrada pregunta 1 (Borrador)" : " Falló pregunta 1");
        System.out.println(service.registrarPregunta(p2) ? " Registrada pregunta 2 (Publicada)" : " Falló pregunta 2");
        System.out.println(" Total en banco: " + service.obtenerTodas().size());

        // PUNTO 2: demostración de la consulta filtrada por estado
        //Agregue el método 'List<Pregunta> obtenerPorEstado(String estado)' en 'IPreguntaService' y su implementación en 'PreguntaServiceImpl'.
        //  Modifique 'Main.java' para registrar dos preguntas (una en estado 'Borrador' y otra 'Publicada') 
        // y demostrar la consulta filtrada por estado. 
        System.out.println("\n--- Preguntas en estado 'Borrador' ---");
        service.obtenerPorEstado("Borrador").forEach(p -> System.out.println(" #" + p.getId() + " " + p.getEnunciado()));
        System.out.println("--- Preguntas en estado 'Publicada' ---");
        service.obtenerPorEstado("Publicada").forEach(p -> System.out.println(" #" + p.getId() + " " + p.getEnunciado()));
    }
}
