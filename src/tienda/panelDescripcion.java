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

public class panelDescripcion extends JPanel {

    private JTextField txNombre, txPrecio, txMarca, txAlto, txAncho, txLargo, txCodigo, txStock;
    private JLabel lblFoto;
    private JButton btnSubirImagen, btnSave, btnBack;
    private Catalogo productoActual;
    private String rutaImagenActual;
    JComboBox cboxTipo, cboxSubtipo;
    public panelDescripcion(Catalogo producto) {
        this.productoActual = producto;
        this.rutaImagenActual = producto.getRuta();

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

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setFont(font);
        lblMarca.setBounds(402, 200, 88, 20);
        add(lblMarca);

        JLabel lblAlto = new JLabel("Alto:");
        lblAlto.setFont(font);
        lblAlto.setBounds(402, 350, 88, 20);
        add(lblAlto);

        JLabel lblAncho = new JLabel("Ancho:");
        lblAncho.setFont(font);
        lblAncho.setBounds(532, 350, 88, 20);
        add(lblAncho);

        JLabel lblLargo = new JLabel("Largo:");
        lblLargo.setFont(font);
        lblLargo.setBounds(685, 350, 88, 20);
        add(lblLargo);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setFont(font);
        lblStock.setBounds(611, 154, 88, 20);
        add(lblStock);
        
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setFont(font);
        lblTipo.setBounds(402, 256, 52, 30);
        add(lblTipo);

        cboxTipo = new JComboBox<>(new String[] { "Cubo", "Mod" });
        cboxTipo.setBounds(500, 254, 150, 35);
        cboxTipo.addActionListener(e -> actualizarSubtipos());
        add(cboxTipo);

        JLabel lblSubtipo = new JLabel("Subtipo:");
        lblSubtipo.setFont(font);
        lblSubtipo.setBounds(660, 256, 100, 30);
        add(lblSubtipo);

        cboxSubtipo = new JComboBox<>();
        cboxSubtipo.setBounds(732, 254, 150, 35);
        actualizarSubtipos();
        add(cboxSubtipo);

        
        // Campos de texto
        txCodigo = new JTextField();
        txCodigo.setEditable(false);
        txCodigo.setBounds(500, 50, 200, 30);
        txCodigo.setText(String.valueOf(producto.getCodigo()));
        add(txCodigo);

        txNombre = new JTextField(producto.getNombre());
        txNombre.setBounds(500, 100, 200, 30);
        add(txNombre);

        txPrecio = new JTextField(String.valueOf(producto.getPrecio()));
        txPrecio.setBounds(500, 150, 81, 30);
        add(txPrecio);

        txMarca = new JTextField(producto.getMarca());
        txMarca.setBounds(500, 200, 200, 30);
        add(txMarca);

        txAlto = new JTextField(String.valueOf(producto.getAlto()));
        txAlto.setBounds(461, 346, 61, 30);
        add(txAlto);

        txAncho = new JTextField(String.valueOf(producto.getAncho()));
        txAncho.setBounds(614, 346, 61, 30);
        add(txAncho);

        txLargo = new JTextField(String.valueOf(producto.getLargo()));
        txLargo.setBounds(762, 346, 61, 30);
        add(txLargo);

        txStock = new JTextField(String.valueOf(producto.getStock()));
        txStock.setBounds(685, 146, 97, 30);
        if (producto.getStock() < 5) {
            txStock.setBackground(Color.RED); // Cambiar el color de fondo a rojo si el stock es menor a 5
        }
        add(txStock);


        // Imagen
        lblFoto = new JLabel();
        lblFoto.setBounds(50, 100, 250, 250);
        lblFoto.setIcon(new ImageIcon(producto.getRuta()));
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
    private void actualizarSubtipos() {
        cboxSubtipo.removeAllItems();
        if (cboxTipo.getSelectedItem().equals("Cubo")) {
            cboxSubtipo.addItem("2x2");
            cboxSubtipo.addItem("3x3");
            cboxSubtipo.addItem("4x4");
            cboxSubtipo.addItem("5x5");
            cboxSubtipo.addItem("6x6");
            cboxSubtipo.addItem("7x7");
            cboxSubtipo.addItem("Pyraminx");
            cboxSubtipo.addItem("Megaminx");
            cboxSubtipo.addItem("Square-1");
            cboxSubtipo.addItem("Clock");
        } else if (cboxTipo.getSelectedItem().equals("Mod")) {
            cboxSubtipo.addItem("Pyraminx");
            cboxSubtipo.addItem("Megaminx");
            cboxSubtipo.addItem("Square-1");
            cboxSubtipo.addItem("Clock");
        }
    }

    private void guardarCambios() {
        try {
            String nombre = txNombre.getText();
            double precio = Double.parseDouble(txPrecio.getText());
            String marca = txMarca.getText();
            double alto = Double.parseDouble(txAlto.getText());
            double ancho = Double.parseDouble(txAncho.getText());
            double largo = Double.parseDouble(txLargo.getText());
            int stock = Integer.parseInt(txStock.getText());
            
            
            if (stock < 5) {
                txStock.setBackground(Color.RED);
            } else {
                txStock.setBackground(Color.WHITE);
            }
            // Mapear el tipo a su código correspondiente
            String tipoSeleccionado = (String) cboxTipo.getSelectedItem();
            int tipo = tipoSeleccionado.equals("Cubo") ? 1 : tipoSeleccionado.equals("Mod") ? 2 : 3;

            // Mapear el subtipo a su código correspondiente
            String subtipoSeleccionado = (String) cboxSubtipo.getSelectedItem();
            int subtipo = mapearSubtipoACodigo(subtipoSeleccionado);

            try (Connection conexion = ConexionDB.obtenerConexion()) {
                ControladorCatalogo controlador = new ControladorCatalogo(conexion);
                boolean actualizado = controlador.actualizarProducto(
                        productoActual.getCodigo(), nombre, marca, precio, alto, ancho, largo, stock, rutaImagenActual, tipo, subtipo
                );

                if (actualizado) {
                    JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private int mapearSubtipoACodigo(String subtipo) {
        switch (subtipo) {
            case "2x2": return 1;
            case "3x3": return 2;
            case "4x4": return 3;
            case "5x5": return 4;
            case "6x6": return 5;
            case "7x7": return 6;
            case "Pyraminx": return 7;
            case "Megaminx": return 8;
            case "Square-1": return 9;
            case "Clock": return 10;
            default: return 0;
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
