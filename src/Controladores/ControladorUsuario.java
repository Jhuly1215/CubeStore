package Controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ControladorUsuario {
    private Connection conexion;

    // Constructor que recibe la conexión a la base de datos
    public ControladorUsuario(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param ci        CI del usuario (debe ser único).
     * @param contrasena Contraseña del usuario.
     * @return true si el registro fue exitoso, false en caso contrario.
     */
    public boolean registrarUsuario(int ci, String contrasena) {
        String query = "INSERT INTO usuario (usuario, contrasena) VALUES (?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, ci);
            stmt.setString(2, contrasena);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Autentica un usuario verificando su CI y contraseña.
     *
     * @param ci        CI del usuario.
     * @param contrasena Contraseña del usuario.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public boolean autenticarUsuario(int ci, String contrasena) {
        String query = "SELECT * FROM usuario WHERE usuario = ? AND contrasena = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, ci);
            stmt.setString(2, contrasena);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Si hay un resultado, las credenciales son válidas
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
