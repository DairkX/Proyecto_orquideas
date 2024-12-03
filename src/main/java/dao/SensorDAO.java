/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Sensor;

public class SensorDAO {
    private Connection connection;

    // Constructor de la clase, pasando la conexión a la base de datos
    public SensorDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar un nuevo sensor en la base de datos
    public void insertarSensor(Sensor sensor) {
        String query = "INSERT INTO sensores (tipoSensor) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, sensor.getTipoSensor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener un sensor por su ID
    public Sensor obtenerSensorPorId(int idSensor) {
        Sensor sensor = null;
        String query = "SELECT * FROM sensores WHERE idSensor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idSensor);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sensor = new Sensor(rs.getInt("idSensor"), rs.getString("tipoSensor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensor;
    }

    // Método para obtener todos los sensores
    public List<Sensor> obtenerTodosLosSensores() {
        List<Sensor> sensores = new ArrayList<>();
        String query = "SELECT * FROM sensores";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Sensor sensor = new Sensor(rs.getInt("idSensor"), rs.getString("tipoSensor"));
                sensores.add(sensor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensores;
    }
}
