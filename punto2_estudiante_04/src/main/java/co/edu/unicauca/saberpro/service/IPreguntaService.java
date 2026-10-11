package co.edu.unicauca.saberpro.service;

import co.edu.unicauca.saberpro.model.Pregunta;
import java.util.List;
// Cargamos las interfaces desde preguntaService
//Agregue el método 'List<Pregunta> obtenerPorEstado(String estado)' en 'IPreguntaService' y su implementación en 'PreguntaServiceImpl'. 
public interface IPreguntaService {
    boolean registrarPregunta(Pregunta pregunta);
    List<Pregunta> obtenerTodas();
    List<Pregunta> obtenerPorEstado(String estado);
}
