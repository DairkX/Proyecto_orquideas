package controlador;

import modelo.Sensor;
import conexion.ConectarBD;  // Importa la clase ConectarBD
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SensorController {
    private Connection connection;

    public SensorController() {
        try {
            // Obtiene la conexión utilizando ConectarBD
            this.connection = ConectarBD.obtenerConexion();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener la conexión.");
        }
    }

    // Método para agregar un nuevo sensor
    public boolean agregarSensor(Sensor sensor) {
        String sql = "INSERT INTO sensores (tipoSensor) VALUES (?)"; // Asume que la tabla 'sensores' tiene 'tipo_sensor'

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sensor.getTipoSensor());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todos los sensores desde la base de datos
    public List<Sensor> obtenerSensores() {
        List<Sensor> sensores = new ArrayList<>();
        String sql = "SELECT * FROM sensores"; // Asume que la tabla 'sensores' tiene al menos 'id' y 'tipo_sensor'

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("idSensor");
                String tipoSensor = resultSet.getString("tipoSensor");
                Sensor sensor = new Sensor(id, tipoSensor);
                sensores.add(sensor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensores;
    }

    // Método para cerrar la conexión (opcional, pero recomendable)
    public void cerrarConexion() {
        ConectarBD.cerrarConexion(connection);
    }
}
