package dao;

import modelo.CondicionAmbiental;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class CondicionAmbientalDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/tu_basededatos";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Método para agregar la condición en la base de datos
    public boolean agregarCondicion(int idSensor, float valor) {
        String sql = "INSERT INTO condicionesambientales (idSensor, fechaHora, valor) VALUES (?, NOW(), ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSensor);
            stmt.setFloat(2, valor);

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

