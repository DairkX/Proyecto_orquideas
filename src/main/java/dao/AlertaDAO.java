package dao;

import modelo.Alerta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO {

    private Connection conn;

    public AlertaDAO(Connection conn) {
        this.conn = conn;
    }

    // Método para insertar una nueva alerta en la base de datos
    public boolean insertarAlerta(Alerta alerta) {
        String sql = "INSERT INTO alertas (idCondicion, mensaje, fecha, hora) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, alerta.getIdCondicion());
            stmt.setString(2, alerta.getMensaje());
            stmt.setDate(3, alerta.getFecha());
            stmt.setTime(4, alerta.getHora());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar la alerta: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener todas las alertas de la base de datos
    public List<Alerta> obtenerAlertas() {
        List<Alerta> alertas = new ArrayList<>();
        String sql = "SELECT * FROM alertas";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idAlerta = rs.getInt("idAlerta");
                int idCondicion = rs.getInt("idCondicion");
                String mensaje = rs.getString("mensaje");
                Date fecha = rs.getDate("fecha");
                Time hora = rs.getTime("hora");

                Alerta alerta = new Alerta(idAlerta, idCondicion, mensaje, fecha, hora);
                alertas.add(alerta);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las alertas: " + e.getMessage());
        }
        return alertas;
    }

    // Método para eliminar una alerta de la base de datos
    public boolean eliminarAlerta(int idAlerta) {
        String sql = "DELETE FROM alertas WHERE idAlerta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAlerta);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar la alerta: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar una alerta
    public boolean actualizarAlerta(Alerta alerta) {
        String sql = "UPDATE alertas SET idCondicion = ?, mensaje = ?, fecha = ?, hora = ? WHERE idAlerta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, alerta.getIdCondicion());
            stmt.setString(2, alerta.getMensaje());
            stmt.setDate(3, alerta.getFecha());
            stmt.setTime(4, alerta.getHora());
            stmt.setInt(5, alerta.getIdAlerta());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar la alerta: " + e.getMessage());
            return false;
        }
    }
}
