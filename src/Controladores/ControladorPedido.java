package Controladores;

import Modelos.Pedido;
import Modelos.DetallePedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorPedido {
    private Connection conexion;

    // Constructor
    public ControladorPedido(Connection conexion) {
        this.conexion = conexion;
    }

    // Crear un nuevo pedido
 // Crear un nuevo pedido
 // Crear un nuevo pedido
    public int crearPedido(Modelos.Pedido nuevoPedido) throws SQLException {
        // Consulta SQL para insertar el pedido y devolver el ID generado
        String sql = "INSERT INTO pedido (fecha, total, usuario) VALUES (?, ?, ?) RETURNING idpedido";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            // Configurar los parámetros de la consulta
            stmt.setDate(1, new java.sql.Date(nuevoPedido.getFecha().getTime())); // Fecha del pedido
            stmt.setDouble(2, nuevoPedido.getTotal()); // Total del pedido
            stmt.setInt(3, nuevoPedido.getUsuario()); // ID del usuario que realiza el pedido

            // Ejecutar la consulta y obtener el ID generado
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idpedido"); // Devuelve el ID del pedido generado
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al insertar el pedido: " + e.getMessage(), e);
        }

        // Si no se genera un ID, lanzar una excepción
        throw new SQLException("Error al insertar el pedido, no se generó un ID.");
    }



    // Agregar detalles a un pedido
    public void agregarDetallePedido(DetallePedido detalle) throws SQLException {
        String sql = "INSERT INTO detalle_pedido (idpedido, codigo, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, detalle.getIdPedido());
            stmt.setInt(2, detalle.getCodigoProducto());
            stmt.setInt(3, detalle.getCantidad());
            stmt.executeUpdate();
        }
    }

    // Obtener todos los pedidos
    public List<Pedido> obtenerTodosLosPedidos() throws SQLException {
        String sql = "SELECT idpedido, fecha, total, usuario FROM pedido";
        List<Pedido> pedidos = new ArrayList<>();
        try (Statement stmt = conexion.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Pedido pedido = new Pedido(
                    rs.getInt("idpedido"),
                    rs.getDate("fecha"),
                    rs.getDouble("total"),
                    rs.getInt("usuario")
                );
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    // Obtener pedidos por usuario
    public List<Pedido> obtenerPedidosPorUsuario(int usuario) throws SQLException {
        String sql = "SELECT idpedido, fecha, total FROM pedido WHERE usuario = ?";
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido(
                    rs.getInt("idpedido"),
                    rs.getDate("fecha"),
                    rs.getDouble("total"),
                    usuario
                );
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    // Obtener detalles de un pedido
    public List<DetallePedido> obtenerDetallesDePedido(int idPedido) throws SQLException {
        String sql = "SELECT dp.cdetallepedido, dp.idpedido, dp.codigo, dp.cantidad " +
                     "FROM detalle_pedido dp " +
                     "WHERE dp.idpedido = ?";
        List<DetallePedido> detalles = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DetallePedido detalle = new DetallePedido(
                    rs.getInt("cdetallepedido"),
                    rs.getInt("idpedido"),
                    rs.getInt("codigo"),
                    rs.getInt("cantidad")
                );
                detalles.add(detalle);
            }
        }
        return detalles;
    }

    // Calcular el total de un pedido
    public double calcularTotalPedido(int idPedido) throws SQLException {
        String sql = "SELECT SUM(dp.cantidad * c.precio) AS total " +
                     "FROM detalle_pedido dp " +
                     "JOIN catalogo c ON dp.codigo = c.codigo " +
                     "WHERE dp.idpedido = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }

    // Actualizar el total de un pedido
    public void actualizarTotalPedido(int idPedido) throws SQLException {
        double total = calcularTotalPedido(idPedido);
        String sql = "UPDATE pedido SET total = ? WHERE idpedido = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDouble(1, total);
            stmt.setInt(2, idPedido);
            stmt.executeUpdate();
        }
    }

    // Eliminar un pedido (y sus detalles)
    public void eliminarPedido(int idPedido) throws SQLException {
        // Eliminar los detalles del pedido primero
        String sqlDetalles = "DELETE FROM detalle_pedido WHERE idpedido = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sqlDetalles)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        }

        // Eliminar el pedido
        String sqlPedido = "DELETE FROM pedido WHERE idpedido = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sqlPedido)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        }
    }
}
