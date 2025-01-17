package controlador;

import conexion.ConectarBD;
import conexion.ConectarBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import util.UsuarioSession;

public class LoginController {

    // Método para autenticar un usuario
    public Usuario autenticar(String email, String password) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE email = ?"; // Cambié 'correo' por 'email'

        // Usamos try-with-resources para manejo automático de recursos
        try (Connection conn = ConectarBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Recupera la contraseña cifrada almacenada en la base de datos
                    String passwordHash = rs.getString("password");

                    // Verifica la contraseña proporcionada contra la cifrada
                    if (BCrypt.checkpw(password, passwordHash)) {
                        usuario = new Usuario();
                        usuario.setIdUsuario(rs.getInt("idUsuario"));
                        usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                        usuario.setEmail(rs.getString("email"));
                        usuario.setPassword(passwordHash); // Guarda el hash, no la contraseña en texto plano

                        // Guarda el correo del usuario en la sesión
                        UsuarioSession.setCorreoUsuarioActual(email);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
