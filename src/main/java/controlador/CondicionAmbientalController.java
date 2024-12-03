package controlador;

import dao.CondicionAmbientalDAO;

public class CondicionAmbientalController {

    private CondicionAmbientalDAO dao;

    public CondicionAmbientalController() {
        dao = new CondicionAmbientalDAO();
    }

    // Método para agregar la condición ambiental
    public boolean agregarCondicion(String tipoSensor, float valor) {
        int idSensor = obtenerIdSensor(tipoSensor);
        return dao.agregarCondicion(idSensor, valor);
    }

    private int obtenerIdSensor(String tipoSensor) {
        if ("Temperatura".equals(tipoSensor)) {
            return 1; // ID para temperatura
        } else if ("Humedad".equals(tipoSensor)) {
            return 2; // ID para humedad
        }
        return -1; // Valor inválido
    }
}
