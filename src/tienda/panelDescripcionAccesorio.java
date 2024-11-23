package tienda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import Controladores.ControladorCatalogo;
import Controladores.ConexionDB;
import Modelos.Catalogo;
import java.sql.Connection;

public class panelDescripcionAccesorio extends JPanel {
    private JTextField txNombre, txPrecio, txTamanio, txStock, txCodigo;
    private JLabel lblFoto;
    private JButton btnSubirImagen, btnSave, btnBack;
    private Catalogo accesorioActual;
    private String rutaImagenActual;

    public panelDescripcionAccesorio(Catalogo accesorio) {
        this.accesorioActual = accesorio;
        this.rutaImagenActual = accesorio.getRuta();

        setLayout(null);
        setBounds(0, 0, 1000, 540);
        setBackground(Color.WHITE);

        Font font = new Font("Bahnschrift", Font.PLAIN, 20);

        // Etiquetas
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setFont(font);
        lblCodigo.setBounds(402, 50, 88, 20);
        add(lblCodigo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(font);
        lblNombre.setBounds(402, 100, 88, 20);
        add(lblNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(font);
        lblPrecio.setBounds(402, 150, 88, 20);
        add(lblPrecio);

        JLabel lblTamanio = new JLabel("Tamaño:");
        lblTamanio.setFont(font);
        lblTamanio.setBounds(402, 200, 88, 20);
        add(lblTamanio);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setFont(font);
        lblStock.setBounds(402, 250, 88, 20);
        add(lblStock);

        // Campos de texto
        txCodigo = new JTextField();
        txCodigo.setEditable(false);
        txCodigo.setBounds(500, 50, 200, 30);
        txCodigo.setText(String.valueOf(accesorio.getCodigo()));
        add(txCodigo);

        txNombre = new JTextField(accesorio.getNombre());
        txNombre.setBounds(500, 100, 200, 30);
        add(txNombre);

        txPrecio = new JTextField(String.valueOf(accesorio.getPrecio()));
        txPrecio.setBounds(500, 150, 200, 30);
        add(txPrecio);

        txTamanio = new JTextField(String.valueOf(accesorio.getTamano()));
        txTamanio.setBounds(500, 200, 200, 30);
        add(txTamanio);

        txStock = new JTextField(String.valueOf(accesorio.getStock()));
        txStock.setBounds(500, 246, 97, 30);
        if (accesorio.getStock() < 5) {
            txStock.setBackground(Color.red); // Cambiar el color de fondo a rojo si el stock es menor a 5
        }
        add(txStock);

        // Imagen
        lblFoto = new JLabel();
        lblFoto.setBounds(50, 100, 250, 250);
        lblFoto.setIcon(new ImageIcon(accesorio.getRuta()));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblFoto);

        btnSubirImagen = new JButton("Subir Imagen");
        btnSubirImagen.setBounds(50, 380, 150, 30);
        btnSubirImagen.addActionListener(e -> seleccionarImagen());
        add(btnSubirImagen);

        // Botón Guardar
        btnSave = new JButton("Guardar");
        btnSave.setBounds(500, 450, 120, 30);
        btnSave.addActionListener(e -> guardarCambios());
        add(btnSave);

        // Botón Regresar
        btnBack = new JButton("Regresar");
        btnBack.setBounds(650, 450, 120, 30);
        btnBack.addActionListener(e -> regresar());
        add(btnBack);
    }

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de Imagen", "jpg", "jpeg", "png"));
        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            rutaImagenActual = archivoSeleccionado.getAbsolutePath();
            lblFoto.setIcon(new ImageIcon(rutaImagenActual));
        }
    }

    private void guardarCambios() {
        try {
            String nombre = txNombre.getText();
            double precio = Double.parseDouble(txPrecio.getText());
            double tamano = Double.parseDouble(txTamanio.getText());
            int stock = Integer.parseInt(txStock.getText());
            
            if (stock < 5) {
                txStock.setBackground(Color.red);
            } else {
                txStock.setBackground(Color.WHITE);
            }
            
            try (Connection conexion = ConexionDB.obtenerConexion()) {
                ControladorCatalogo controlador = new ControladorCatalogo(conexion);
                boolean actualizado = controlador.actualizarAccesorio(
                        accesorioActual.getCodigo(), nombre, precio, tamano, stock, rutaImagenActual
                );

                if (actualizado) {
                    JOptionPane.showMessageDialog(this, "Accesorio actualizado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el accesorio.", "Error", JOptionPane.ERROR_MESSAGE);
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
        JFProducts jfProducts = new JFProducts();
        removeAll();
        jfProducts.setVisible(true);
        revalidate();
        repaint();
    }
}
