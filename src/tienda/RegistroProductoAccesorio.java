package tienda;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import Controladores.ConexionDB;
import Controladores.ControladorCatalogo;

public class RegistroProductoAccesorio extends JPanel {

    private JTextField txNombre;
    private JTextField txPrecio;
    private JTextField txMarca;
    private JLabel lblFoto;
    private JButton btnSubirImagen;
    private JButton btnSave;
    private JLabel lblAñadirMod;
    private JTextField txStock;
    private JTextField txTamanio;
    private String rutaImagenActual;

    public RegistroProductoAccesorio() {
        setLayout(null);
        setBounds(0, 0, 1000, 580);
        Font f = new Font("Bahnschrift", Font.PLAIN, 20);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setFont(f);
        lblStock.setBounds(409, 357, 88, 20);
        add(lblStock);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(f);
        lblNombre.setBounds(395, 179, 88, 20);
        add(lblNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(f);
        lblPrecio.setBounds(409, 240, 71, 25);
        add(lblPrecio);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setFont(f);
        lblMarca.setBounds(409, 302, 71, 25);
        add(lblMarca);

        JLabel lblTamanio = new JLabel("Tamaño:");
        lblTamanio.setFont(f);
        lblTamanio.setBounds(698, 240, 88, 25);
        add(lblTamanio);

        txStock = new JTextField();
        txStock.setColumns(10);
        txStock.setBounds(500, 350, 147, 35);
        add(txStock);

        txNombre = new JTextField();
        txNombre.setBounds(500, 172, 410, 35);
        add(txNombre);
        txNombre.setColumns(10);

        txPrecio = new JTextField();
        txPrecio.setColumns(10);
        txPrecio.setBounds(500, 231, 169, 35);
        add(txPrecio);

        txMarca = new JTextField();
        txMarca.setColumns(10);
        txMarca.setBounds(500, 293, 410, 35);
        add(txMarca);

        txTamanio = new JTextField();
        txTamanio.setBounds(795, 231, 110, 35);
        add(txTamanio);
        txTamanio.setColumns(10);

        lblFoto = new JLabel("imagen");
        lblFoto.setIcon(new ImageIcon());
        lblFoto.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        lblFoto.setBounds(64, 113, 250, 300);
        add(lblFoto);

        btnSubirImagen = new JButton("Subir imagen");
        btnSubirImagen.addActionListener(e -> seleccionarImagen());
        btnSubirImagen.setBounds(194, 440, 120, 40);
        add(btnSubirImagen);

        btnSave = new JButton("Guardar");
        btnSave.addActionListener(e -> guardarAccesorioEnBD());
        btnSave.setBounds(510, 435, 400, 50);
        add(btnSave);

        lblAñadirMod = new JLabel("Nuevo accesorio/lubricante:");
        lblAñadirMod.setFont(new Font("Bahnschrift", Font.PLAIN, 40));
        lblAñadirMod.setBounds(62, 37, 671, 50);
        add(lblAñadirMod);

        JButton btnBack = new JButton(">");
        btnBack.addActionListener(e -> regresar());
        btnBack.setBounds(915, 40, 50, 50);
        add(btnBack);
    }

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de Imagen", "jpg", "jpeg", "png", "gif", "jfif"));

        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            // Obtener la ruta del archivo seleccionado
            rutaImagenActual = fileChooser.getSelectedFile().getPath();
            lblFoto.setIcon(new ImageIcon(rutaImagenActual));
        }
    }

    private void guardarAccesorioEnBD() {
        try {
            String nombre = txNombre.getText();
            double precio = Double.parseDouble(txPrecio.getText());
            String marca = txMarca.getText();
            double tamano = Double.parseDouble(txTamanio.getText());
            int stock = Integer.parseInt(txStock.getText());

            if (rutaImagenActual == null || rutaImagenActual.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conexion = ConexionDB.obtenerConexion()) {
                ControladorCatalogo controlador = new ControladorCatalogo(conexion);
                boolean registrado = controlador.insertarProductoAccesorio(nombre, precio, marca, tamano, stock, rutaImagenActual);

                if (registrado) {
                    JOptionPane.showMessageDialog(this, "Accesorio registrado correctamente.");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar el accesorio.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void regresar() {
        JFProducts p = new JFProducts();
        removeAll();
        p.setVisible(true);
        revalidate();
        repaint();
    }

    private void limpiarCampos() {
        txStock.setText("");
        txNombre.setText("");
        txMarca.setText("");
        txPrecio.setText("");
        txTamanio.setText("");
        lblFoto.setIcon(new ImageIcon());
        rutaImagenActual = null;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFrame frame = new JFrame();
                frame.setContentPane(new RegistroProductoAccesorio());
                frame.setSize(1000, 580);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
