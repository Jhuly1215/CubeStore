package tienda;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controladores.ConexionDB;
import java.awt.event.ActionListener;

public class UFTodosPedidos extends JFrame {

    private JTable tablePedidos;
    private JTable tableDetalles;
    private DefaultTableModel modeloPedidos;
    private DefaultTableModel modeloDetalles;

    public UFTodosPedidos() {
        setTitle("Gestión de Pedidos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        getContentPane().setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(30, 34, 35));
        panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JLabel lblTitulo = new JLabel("Gestión de Pedidos");
        lblTitulo.setFont(new Font("Bahnschrift", Font.BOLD, 25));
        lblTitulo.setForeground(Color.WHITE);
        panelSuperior.add(lblTitulo);
        getContentPane().add(panelSuperior, BorderLayout.NORTH);
        
        

        JPanel panelCentro = new JPanel(new GridLayout(2, 1, 10, 10));

        // Tabla de Pedidos
        modeloPedidos = new DefaultTableModel(new String[] { "ID Pedido", "Fecha", "Total", "ID Usuario" }, 0);
        tablePedidos = new JTable(modeloPedidos);
        tablePedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPedidos = new JScrollPane(tablePedidos);
        scrollPedidos.setBorder(BorderFactory.createTitledBorder("Pedidos"));
        panelCentro.add(scrollPedidos);

        // Tabla de Detalles de Pedido
        modeloDetalles = new DefaultTableModel(new String[] { "ID Detalle", "ID Pedido", "ID Producto", "Nombre Producto", "Cantidad" }, 0);

        tableDetalles = new JTable(modeloDetalles);

        JScrollPane scrollDetalles = new JScrollPane(tableDetalles);
        scrollDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Pedido"));
        panelCentro.add(scrollDetalles);

        getContentPane().add(panelCentro, BorderLayout.CENTER);

        // Botón Cargar Datos
        JPanel panelInferior = new JPanel();
        JButton btnCargarDatos = new JButton("Cargar Datos");
        btnCargarDatos.addActionListener(this::cargarDatos);
        panelInferior.add(btnCargarDatos);

        JButton btnActualizarDetalle = new JButton("Actualizar Detalle");
        btnActualizarDetalle.addActionListener(this::actualizarDetalle);
        panelInferior.add(btnActualizarDetalle);
        
        JButton btnNuevoPedido = new JButton("NuevoPedido");
        btnNuevoPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                AgregarPedidoAdmin jfProducts = new AgregarPedidoAdmin(); 
                jfProducts.setVisible(true);
            }
        });
        panelInferior.add(btnNuevoPedido);
        
        JButton btnBack = new JButton("Regresar");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                JFProducts jfProducts = new JFProducts(); 
                jfProducts.setVisible(true);
            }
        });
        panelInferior.add(btnBack);


        getContentPane().add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarDatos(ActionEvent e) {
        modeloPedidos.setRowCount(0); 
        modeloDetalles.setRowCount(0); 

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sqlPedidos = "SELECT idpedido, fecha, total, usuario FROM pedido";
            ResultSet rsPedidos = conexion.createStatement().executeQuery(sqlPedidos);

            while (rsPedidos.next()) {
                int idPedido = rsPedidos.getInt("idpedido");
                String fecha = rsPedidos.getDate("fecha").toString();
                double total = rsPedidos.getDouble("total");
                int idUsuario = rsPedidos.getInt("usuario");

                modeloPedidos.addRow(new Object[] { idPedido, fecha, total, idUsuario });
            }

            tablePedidos.getSelectionModel().addListSelectionListener(event -> {
                if (!event.getValueIsAdjusting() && tablePedidos.getSelectedRow() != -1) {
                    int idPedidoSeleccionado = (int) modeloPedidos.getValueAt(tablePedidos.getSelectedRow(), 0);
                    cargarDetalles(idPedidoSeleccionado);
                }
            });

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDetalles(int idPedido) {
        modeloDetalles.setRowCount(0);
        try (Connection conexion = ConexionDB.obtenerConexion()) {
            
            String sqlDetalles = "SELECT dp.cdetallepedido, dp.idpedido, dp.codigo, p.nombre AS producto_nombre, dp.cantidad " +
                                 "FROM detalle_pedido dp " +
                                 "JOIN catalogo p ON dp.codigo = p.codigo " +
                                 "WHERE dp.idpedido = ?";
            var stmt = conexion.prepareStatement(sqlDetalles);
            stmt.setInt(1, idPedido);
            ResultSet rsDetalles = stmt.executeQuery();

            while (rsDetalles.next()) {
                int idDetalle = rsDetalles.getInt("cdetallepedido");
                int idPedidoDetalle = rsDetalles.getInt("idpedido");
                int idProducto = rsDetalles.getInt("codigo");
                String nombreProducto = rsDetalles.getString("producto_nombre");
                int cantidad = rsDetalles.getInt("cantidad");

                modeloDetalles.addRow(new Object[] { idDetalle, idPedidoDetalle, idProducto, nombreProducto, cantidad });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar detalles: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void actualizarDetalle(ActionEvent e) {
        int filaSeleccionada = tableDetalles.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un detalle para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            int idDetalle = (int) modeloDetalles.getValueAt(filaSeleccionada, 0);
            int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Nueva cantidad:"));

            String sqlActualizar = "UPDATE detalle_pedido SET cantidad = ? WHERE cdetallepedido = ?";
            var stmt = conexion.prepareStatement(sqlActualizar);
            stmt.setInt(1, nuevaCantidad);
            stmt.setInt(2, idDetalle);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Detalle actualizado correctamente.");
                modeloDetalles.setValueAt(nuevaCantidad, filaSeleccionada, 3);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el detalle.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            UFTodosPedidos frame = new UFTodosPedidos();
            frame.setVisible(true);
        });
    }
}
