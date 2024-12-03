/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import conexion.ConectarBD;
import static conexion.ConectarBD.obtenerConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Orquidea;

public class OrquideaController {

    // Método para insertar una orquídea
    public boolean insertarOrquidea(String nombreOrquidea, String especie) {
        String sql = "INSERT INTO orquideas (nombreOrquidea, especie) VALUES (?, ?)";

        try (Connection conn = ConectarBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreOrquidea);
            stmt.setString(2, especie);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0; // Si la inserción es exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todas las orquídeas
    public List<Orquidea> obtenerOrquideas() {
        List<Orquidea> orquideas = new ArrayList<>();
        String sql = "SELECT * FROM orquideas";

        try (Connection conn = ConectarBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idOrquidea = rs.getInt("idOrquidea");
                String nombreOrquidea = rs.getString("nombreOrquidea");
                String especie = rs.getString("especie");

                Orquidea orquidea = new Orquidea(idOrquidea, nombreOrquidea);
                orquideas.add(orquidea);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orquideas;
    }
public boolean editarOrquidea(int idOrquidea, String nombreOrquidea, String especie) {
    String query = "UPDATE orquideas SET nombreOrquidea = ?, especie = ? WHERE idOrquidea = ?";

    try (Connection conexion = obtenerConexion();  // Abriendo la conexión dentro del try-with-resources
         PreparedStatement stmt = conexion.prepareStatement(query)) {

        // Establecer los parámetros en el orden correcto según la consulta SQL
        stmt.setString(1, nombreOrquidea);  // Establecer el nombreOrquidea primero
        stmt.setString(2, especie);         // Establecer la especie después
        stmt.setInt(3, idOrquidea);         // Establecer el idOrquidea al final, ya que es la condición WHERE

        int filasActualizadas = stmt.executeUpdate();
        return filasActualizadas > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}