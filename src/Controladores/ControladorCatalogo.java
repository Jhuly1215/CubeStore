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
    
    
    public void insertarProducto(String nombre, String marca, double precio, String rutaImagen, 
            double alto, double ancho, double largo, int stock, 
            String tipo, String subtipo) throws SQLException {
	String query = "INSERT INTO catalogo (nombre, precio, marca, ruta, alto, ancho, largo, stock, idtipo, idsubtipo) " +
	  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conexion.prepareStatement(query)) {
			stmt.setString(1, nombre);
			stmt.setDouble(2, precio);
			stmt.setString(3, marca);
			stmt.setString(4, rutaImagen);
			stmt.setDouble(5, alto);
			stmt.setDouble(6, ancho);
			stmt.setDouble(7, largo);
			stmt.setInt(8, stock);
			
			// Aquí puedes obtener los IDs de tipo y subtipo desde la base de datos
			int idTipo = obtenerIdTipo(tipo); // Implementa este método
			int idSubtipo = obtenerIdSubtipo(subtipo); // Implementa este método
			stmt.setInt(9, idTipo);
			stmt.setInt(10, idSubtipo);
			
			stmt.executeUpdate();
		}
	}

	private int obtenerIdTipo(String tipo) throws SQLException {
		String query = "SELECT idtipo FROM tipo WHERE tipo = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(query)) {
			stmt.setString(1, tipo);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
				return rs.getInt("idtipo");
				}
			}
		}
		throw new SQLException("Tipo no encontrado: " + tipo);
	}

	private int obtenerIdSubtipo(String subtipo) throws SQLException {
		String query = "SELECT idsubtipo FROM subtipo WHERE subtipo = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(query)) {
			stmt.setString(1, subtipo);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
				return rs.getInt("idsubtipo");
				}
			}
		}
		throw new SQLException("Subtipo no encontrado: " + subtipo);
	}

	public boolean actualizarProducto(int codigo, String nombre, String marca, double precio, double alto, double ancho, double largo, int stock, String rutaImagen, int tipo, int subtipo) {
	    String sql = "UPDATE catalogo SET nombre = ?, marca = ?, precio = ?, alto = ?, ancho = ?, largo = ?, stock = ?, ruta = ?, idtipo = ?, idsubtipo = ? WHERE codigo = ?";

	    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
	        pstmt.setString(1, nombre);
	        pstmt.setString(2, marca);
	        pstmt.setDouble(3, precio);
	        pstmt.setDouble(4, alto);
	        pstmt.setDouble(5, ancho);
	        pstmt.setDouble(6, largo);
	        pstmt.setInt(7, stock);
	        pstmt.setString(8, rutaImagen);
	        pstmt.setInt(9, tipo);
	        pstmt.setInt(10, subtipo);
	        pstmt.setInt(11, codigo);

	        int filasAfectadas = pstmt.executeUpdate();
	        return filasAfectadas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public boolean actualizarAccesorio(
	        int codigo, String nombre, double precio, double tamano, int stock, String rutaImagen) throws SQLException {

	    String query = "UPDATE catalogo SET nombre = ?, precio = ?, tamano = ?, stock = ?, ruta = ?, idtipo = 3 WHERE codigo = ?";

	    try (PreparedStatement stmt = conexion.prepareStatement(query)) {
	        stmt.setString(1, nombre);
	        stmt.setDouble(2, precio);
	        stmt.setDouble(3, tamano);
	        stmt.setInt(4, stock);
	        stmt.setString(5, rutaImagen);
	        stmt.setInt(6, codigo);

	        return stmt.executeUpdate() > 0;
	    }
	}
	public boolean insertarProductoAccesorio(String nombre, double precio, String marca, double tamanno, int stock, String ruta) throws SQLException {
	    String query = "INSERT INTO catalogo (nombre, precio, marca, tamano, stock, ruta, idtipo, idsubtipo) VALUES (?, ?, ?, ?, ?, ?, 3, NULL)";

	    try (PreparedStatement stmt = conexion.prepareStatement(query)) {
	        stmt.setString(1, nombre);
	        stmt.setDouble(2, precio);
	        stmt.setString(3, marca);
	        stmt.setDouble(4, tamanno); // Usamos 'alto' para guardar el tamaño.
	        stmt.setInt(5, stock);
	        stmt.setString(6, ruta);
	        return stmt.executeUpdate() > 0;
	    }
	}





}
