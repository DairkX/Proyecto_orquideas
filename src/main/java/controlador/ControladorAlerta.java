package controlador;

import modelo.Alerta;
import dao.AlertaDAO;
import conexion.ConectarBD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ControladorAlerta {

    private AlertaDAO alertaDAO;

    public ControladorAlerta() {
        // Conectamos al modelo (AlertaDAO)
        try {
            // Usar el método correcto para obtener la conexión
            Connection conn = ConectarBD.obtenerConexion();
            alertaDAO = new AlertaDAO(conn);  // Pasar la conexión al DAO
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para verificar condiciones y generar alertas automáticamente
    public void verificarYGenerarAlertas() {
        // Aquí obtienes las condiciones de la base de datos o las que sean necesarias
        // Por ejemplo, condiciones como "temperatura mayor a 30°C"
        
        // Suponiendo que tenemos una condición que se cumple para generar una alerta:
        boolean condicionCumplida = verificarCondicion();  // Método ficticio

        if (condicionCumplida) {
            // Crear alerta con datos automáticos, como fecha y hora actual
            Alerta alerta = crearAlertaAutomatica();
            
            // Insertar la alerta en la base de datos
            if (alertaDAO.insertarAlerta(alerta)) {
                System.out.println("Alerta generada y guardada automáticamente.");
            } else {
                System.out.println("Error al generar la alerta.");
            }
        }
    }

    // Método que "verifica" la condición (puedes adaptarlo según tu lógica)
    private boolean verificarCondicion() {
        // Aquí verificas si la condición se cumple, como por ejemplo:
        // - ¿La temperatura ha superado el umbral de 30°C?
        // - ¿Se ha alcanzado un nivel crítico de humedad?
        // Si es así, devuelve true
        return true; // Solo para ilustración
    }

    // Método que crea la alerta automáticamente
    private Alerta crearAlertaAutomatica() {
        // Aquí usas la fecha y la hora actuales (o lo que desees)
        Date fecha = new Date(System.currentTimeMillis());
        Time hora = new Time(System.currentTimeMillis());

        // Asumimos que el ID de la condición es 1 (deberías definir tu lógica)
        int idCondicion = 1;
        String mensaje = "Condición crítica alcanzada";

        return new Alerta(0, idCondicion, mensaje, fecha, hora);
    }
     public List<Alerta> obtenerAlertas() {
        return alertaDAO.obtenerAlertas(); // Llamar al método del DAO
    }
}
