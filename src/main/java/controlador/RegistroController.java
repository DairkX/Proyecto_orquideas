package controlador;

import conexion.ConectarBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class RegistroController {

    // Método para registrar un nuevo usuario
    public boolean registrarUsuario(String email, String nombreUsuario, String password) {
        // Primero verificamos si el email ya existe en la base de datos
        String checkEmailSql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        
        try (Connection conn = ConectarBD.obtenerConexion();
             PreparedStatement checkStmt = conn.prepareStatement(checkEmailSql)) {
            
            // Establecemos el parámetro email
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            
            // Verificamos si el email ya está registrado
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("El correo electrónico ya está registrado.");
                return false; // El email ya existe
            }

            // Si el email no existe, procedemos con el registro del usuario
            String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt()); // Encriptamos la contraseña
            String insertSql = "INSERT INTO usuarios (nombreUsuario, email, password) VALUES (?, ?, ?)";
            
            // Insertamos el nuevo usuario
            try (PreparedStatement stmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nombreUsuario);
                stmt.setString(2, email);
                stmt.setString(3, passwordHash);

                int filasAfectadas = stmt.executeUpdate();

                // Si la inserción fue exitosa, recuperamos el ID generado
                if (filasAfectadas > 0) {
                    try (ResultSet rsGeneratedKeys = stmt.getGeneratedKeys()) {
                        if (rsGeneratedKeys.next()) {
                            int idUsuarioGenerado = rsGeneratedKeys.getInt(1);
                            System.out.println("ID generado: " + idUsuarioGenerado);
                        }
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En caso de error en la base de datos
        }
        return false;
    }
}
