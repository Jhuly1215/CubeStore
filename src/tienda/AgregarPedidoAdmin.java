package tienda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import Controladores.ConexionDB;

public class AgregarPedidoAdmin extends JFrame {

    private JComboBox<String> cboxUsuarios;
    private JComboBox<String> cboxFiltroProductos;
    private JTable tableProductos;
    private JTable tableSeleccionados;
    private DefaultTableModel modeloProductos;
    private DefaultTableModel modeloSeleccionados;
    private JLabel lblTotal;
    private double total = 0.0;

    public AgregarPedidoAdmin() {
        setTitle("Agregar Pedido (Administrador)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Panel Superior
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelSuperior.setBackground(new Color(30, 34, 35));

        JLabel lblTitulo = new JLabel("Agregar Pedido");
        lblTitulo.setFont(new Font("Bahnschrift", Font.BOLD, 25));
        lblTitulo.setForeground(Color.WHITE);
        panelSuperior.add(lblTitulo);

        panelSuperior.add(new JLabel("Usuario:"));
        cboxUsuarios = new JComboBox<>();
        cargarUsuarios();
        panelSuperior.add(cboxUsuarios);

        getContentPane().add(panelSuperior, BorderLayout.NORTH);

        // Panel Central
        JPanel panelCentro = new JPanel(new GridLayout(2, 1, 10, 10));

        // Productos
        modeloProductos = new DefaultTableModel(new String[] { "ID", "Nombre", "Precio", "Stock", "Tipo", "Subtipo" }, 0);
        tableProductos = new JTable(modeloProductos);

        JScrollPane scrollProductos = new JScrollPane(tableProductos);
        scrollProductos.setBorder(BorderFactory.createTitledBorder("Productos Disponibles"));

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.add(new JLabel("Filtrar por:"));
        cboxFiltroProductos = new JComboBox<>(new String[] { "Todos", "Cubo", "Mod", "Accesorio" });
        cboxFiltroProductos.addActionListener(e -> cargarProductos());
        panelFiltro.add(cboxFiltroProductos);

        JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.add(panelFiltro, BorderLayout.NORTH);
        panelProductos.add(scrollProductos, BorderLayout.CENTER);

        panelCentro.add(panelProductos);

        // Productos Seleccionados
        modeloSeleccionados = new DefaultTableModel(new String[] { "ID", "Nombre", "Precio", "Cantidad", "Costo" }, 0);
        tableSeleccionados = new JTable(modeloSeleccionados);

        JScrollPane scrollSeleccionados = new JScrollPane(tableSeleccionados);
        scrollSeleccionados.setBorder(BorderFactory.createTitledBorder("Productos Seleccionados"));
        panelCentro.add(scrollSeleccionados);

        getContentPane().add(panelCentro, BorderLayout.CENTER);

        // Panel Inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        lblTotal = new JLabel("Total: Bs. 0.00");
        lblTotal.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        panelInferior.add(lblTotal);

        JButton btnAgregarProducto = new JButton("Agregar Producto");
        btnAgregarProducto.addActionListener(this::agregarProducto);
        panelInferior.add(btnAgregarProducto);

        JButton btnRealizarPedido = new JButton("Realizar Pedido");
        btnRealizarPedido.addActionListener(this::realizarPedido);
        panelInferior.add(btnRealizarPedido);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelInferior.add(btnCancelar);

        getContentPane().add(panelInferior, BorderLayout.SOUTH);

        cargarProductos();
    }

    private void cargarUsuarios() {
        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sql = "SELECT usuario FROM usuario";
            ResultSet rs = conexion.createStatement().executeQuery(sql);

            while (rs.next()) {
                int idUsuario = rs.getInt("usuario");
                cboxUsuarios.addItem(""+idUsuario);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarProductos() {
        modeloProductos.setRowCount(0);
        String filtro = (String) cboxFiltroProductos.getSelectedItem();
        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sql = """
                    SELECT c.codigo, c.nombre, c.precio, c.stock, t.tipo AS tipo_nombre, s.subtipo AS subtipo_nombre
                    FROM catalogo c
                    LEFT JOIN tipo t ON c.idtipo = t.idtipo
                    LEFT JOIN subtipo s ON c.idsubtipo = s.idsubtipo
                    """;

            if (!filtro.equals("Todos")) {
                sql += " WHERE t.tipo = ?";
            }

            PreparedStatement stmt = conexion.prepareStatement(sql);
            if (!filtro.equals("Todos")) {
                stmt.setString(1, filtro);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                String tipo = rs.getString("tipo_nombre");
                String subtipo = rs.getString("subtipo_nombre");

                modeloProductos.addRow(new Object[] { id, nombre, precio, stock, tipo, subtipo });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProducto(ActionEvent e) {
        int filaSeleccionada = tableProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idProducto = (int) modeloProductos.getValueAt(filaSeleccionada, 0);
        String nombreProducto = (String) modeloProductos.getValueAt(filaSeleccionada, 1);
        double precio = (double) modeloProductos.getValueAt(filaSeleccionada, 2);
        int stock = (int) modeloProductos.getValueAt(filaSeleccionada, 3);

        String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad:");
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad > stock || cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida o superior al stock.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double costo = cantidad * precio;
            modeloSeleccionados.addRow(new Object[] { idProducto, nombreProducto, precio, cantidad, costo });
            total += costo;
            lblTotal.setText("Total: Bs. " + total);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarPedido(ActionEvent e) {
        if (cboxUsuarios.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para realizar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (modeloSeleccionados.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String usuario = (String) cboxUsuarios.getSelectedItem();
        int idUsuario = Integer.parseInt(usuario.split(" - ")[0]);
        Date fechaActual = new Date();

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            conexion.setAutoCommit(false); 

            String sqlPedido = "INSERT INTO pedido (fecha, total, usuario) VALUES (?, ?, ?)";
            var stmtPedido = conexion.prepareStatement(sqlPedido, new String[] { "idpedido" });
            stmtPedido.setDate(1, new java.sql.Date(fechaActual.getTime()));
            stmtPedido.setDouble(2, total);
            stmtPedido.setInt(3, idUsuario);
            stmtPedido.executeUpdate();

            ResultSet rs = stmtPedido.getGeneratedKeys();
            rs.next();
            int idPedido = rs.getInt(1);

  
            String sqlDetalle = "INSERT INTO detalle_pedido (idpedido, codigo, cantidad) VALUES (?, ?, ?)";
            String sqlActualizarStock = "UPDATE catalogo SET stock = stock - ? WHERE codigo = ?";

            var stmtDetalle = conexion.prepareStatement(sqlDetalle);
            var stmtActualizarStock = conexion.prepareStatement(sqlActualizarStock);


            for (int i = 0; i < modeloSeleccionados.getRowCount(); i++) {
                int idProducto = (int) modeloSeleccionados.getValueAt(i, 0);
                int cantidad = (int) modeloSeleccionados.getValueAt(i, 3);

            
                stmtDetalle.setInt(1, idPedido);
                stmtDetalle.setInt(2, idProducto);
                stmtDetalle.setInt(3, cantidad);
                stmtDetalle.executeUpdate();

                
                stmtActualizarStock.setInt(1, cantidad);
                stmtActualizarStock.setInt(2, idProducto);
                stmtActualizarStock.executeUpdate();
            }

            conexion.commit(); 
            JOptionPane.showMessageDialog(this, "Pedido registrado con éxito.");
            
         
            UFTodosPedidos todosPedidosFrame = new UFTodosPedidos();
            todosPedidosFrame.setVisible(true);
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al realizar el pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            try (Connection conexion = ConexionDB.obtenerConexion()) {
                conexion.rollback(); 
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AgregarPedidoAdmin frame = new AgregarPedidoAdmin();
            frame.setVisible(true);
        });
    }
}
