package Controladores;

import Modelos.Catalogo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorCatalogo {

    private Connection conexion;

    public ControladorCatalogo(Connection conexion) {
        this.conexion = conexion;
    }
    
    public List<Catalogo> obtenerCatalogoPorTipo(int idTipo) {
        List<Catalogo> catalogo = new ArrayList<>();
        String query = "SELECT * FROM catalogo WHERE idtipo = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, idTipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int codigo = rs.getInt("codigo");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    String marca = rs.getString("marca");
                    String ruta = rs.getString("ruta");
                    int idSubtipo = rs.getInt("idsubtipo");
                    double alto = rs.getDouble("alto");
                    double ancho = rs.getDouble("ancho");
                    double largo = rs.getDouble("largo");
                    double tamano = rs.getDouble("tamano");

                    Catalogo producto;

                    // Crear objeto `Catalogo` según el tipo
                    if (idTipo == 1 || idTipo == 2) { // Ejemplo: 1 = Cubos, 2 = Mods
                        producto = new Catalogo(codigo, nombre, precio, marca, ruta, idTipo, idSubtipo, alto, ancho, largo);
                    } else if (idTipo == 3) { // Ejemplo: 3 = Accesorios
                        producto = new Catalogo(codigo, nombre, precio, marca, ruta, idTipo, tamano);
                    } else {
                        // Otros tipos de productos genéricos
                        producto = new Catalogo(codigo, nombre, precio, marca, ruta, idTipo);
                    }

                    catalogo.add(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return catalogo;
    }
}
