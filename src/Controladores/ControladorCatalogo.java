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
                    int stock = rs.getInt("stock");
                    int idSubtipo = rs.getInt("idsubtipo");
                    double alto = rs.getDouble("alto");
                    double ancho = rs.getDouble("ancho");
                    double largo = rs.getDouble("largo");
                    double tamano = rs.getDouble("tamano");

                    Catalogo producto;

                    // Crear objeto `Catalogo` según el tipo
                    if (idTipo == 1 || idTipo == 2) { // Ejemplo: 1 = Cubos, 2 = Mods
                        producto = new Catalogo(codigo, nombre, precio, marca, ruta, idTipo,stock,idSubtipo, alto, ancho, largo);
                    } else if (idTipo == 3) { // Ejemplo: 3 = Accesorios
                        producto = new Catalogo(codigo, nombre, precio, marca, ruta, idTipo,stock, tamano);
                    } else {
                        // Otros tipos de productos genéricos
                        producto = new Catalogo(codigo, nombre, precio, marca, ruta, idTipo,stock);
                    }

                    catalogo.add(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return catalogo;
    }
    
    /**
     * Obtiene el stock actual de un producto.
     *
     * @param codigoProducto Código del producto.
     * @return Cantidad de stock disponible o -1 si el producto no existe.
     */
    public int obtenerStock(int codigoProducto) {
        String consulta = "SELECT stock FROM catalogo WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, codigoProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Devuelve -1 si no se encuentra el producto
    }

    /**
     * Reduce el stock de un producto en la base de datos.
     *
     * @param codigoProducto Código del producto.
     * @param cantidad       Cantidad a reducir.
     * @return true si el stock fue reducido exitosamente, false si no hay suficiente stock o hubo un error.
     */
    public boolean reducirStock(int codigoProducto, int cantidad) {
        String consulta = "UPDATE catalogo SET stock = stock - ? WHERE codigo = ? AND stock >= ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, codigoProducto);
            ps.setInt(3, cantidad); // Validar que haya suficiente stock
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Incrementa el stock de un producto en la base de datos.
     *
     * @param codigoProducto Código del producto.
     * @param cantidad       Cantidad a incrementar.
     * @return true si el stock fue incrementado exitosamente, false si hubo un error.
     */
    public boolean incrementarStock(int codigoProducto, int cantidad) {
        String consulta = "UPDATE catalogo SET stock = stock + ? WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, codigoProducto);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
