package tienda;

import javax.swing.JPanel;


import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controladores.ConexionDB;
import Controladores.ControladorCatalogo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
//import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class RegistroProductoCubo extends JPanel {
	private JTextField txNombre;
	private JTextField txPrecio;
	private JTextField txMarca;
	private JTextField txAlto;
	private JTextField txAncho;
	private JTextField txLargo;
	private JLabel lblFoto;
	private JButton btnSubirImagen;
	private JButton btnSave;
	private JLabel lblAñadirCubo;
	private JTextField txCodigo;
	private String archivo="Cubos.txt";
	private JFrame parentFrame;
	private String rutaImagen = "";
	JComboBox cboxTipo;
	JComboBox cboxSubtipo;
	private static JFProducts jfProductsInstance;
	private JTextField txStock;
	private String rutaImagenActual;

	public RegistroProductoCubo() {
        setLayout(null);
        setBounds(0, 0, 1000, 580);
        Font f = new Font("Bahnschrift", Font.PLAIN, 20);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(f);
        lblNombre.setBounds(395, 62, 100, 30);
        add(lblNombre);

        txNombre = new JTextField();
        txNombre.setBounds(500, 60, 410, 35);
        add(txNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(f);
        lblPrecio.setBounds(395, 123, 100, 30);
        add(lblPrecio);

        txPrecio = new JTextField();
        txPrecio.setBounds(500, 121, 150, 35);
        add(txPrecio);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setFont(f);
        lblMarca.setBounds(395, 191, 100, 30);
        add(lblMarca);

        txMarca = new JTextField();
        txMarca.setBounds(500, 189, 410, 35);
        add(txMarca);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setFont(f);
        lblTipo.setBounds(395, 256, 100, 30);
        add(lblTipo);

        cboxTipo = new JComboBox<>(new String[] { "Cubo", "Mod" });
        cboxTipo.setBounds(500, 254, 150, 35);
        cboxTipo.addActionListener(e -> actualizarSubtipos());
        add(cboxTipo);

        JLabel lblSubtipo = new JLabel("Subtipo:");
        lblSubtipo.setFont(f);
        lblSubtipo.setBounds(675, 256, 100, 30);
        add(lblSubtipo);

        cboxSubtipo = new JComboBox<>();
        cboxSubtipo.setBounds(762, 254, 150, 35);
        actualizarSubtipos();
        add(cboxSubtipo);

        JLabel lblAlto = new JLabel("Alto:");
        lblAlto.setFont(f);
        lblAlto.setBounds(436, 315, 59, 30);
        add(lblAlto);

        txAlto = new JTextField();
        txAlto.setBounds(497, 313, 80, 35);
        add(txAlto);

        txAncho = new JTextField();
        txAncho.setBounds(664, 313, 80, 35);
        add(txAncho);

        txLargo = new JTextField();
        txLargo.setBounds(832, 313, 80, 35);
        add(txLargo);

        lblFoto = new JLabel("Imagen");
        lblFoto.setBounds(50, 45, 250, 300);
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblFoto);

        btnSubirImagen = new JButton("Subir Imagen");
        btnSubirImagen.setBounds(50, 378, 150, 40);
        btnSubirImagen.addActionListener(e -> seleccionarImagen());
        add(btnSubirImagen);

        btnSave = new JButton("Guardar");
        btnSave.setBounds(610, 395, 200, 40);
        btnSave.addActionListener(e -> guardarProducto());
        add(btnSave);
        
        JLabel lblAncho = new JLabel("Ancho:");
        lblAncho.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        lblAncho.setBounds(587, 315, 69, 30);
        add(lblAncho);
        
        JLabel lblLargo = new JLabel("Largo:");
        lblLargo.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        lblLargo.setBounds(765, 315, 69, 30);
        add(lblLargo);
        
        txStock = new JTextField();
        txStock.setText("");
        txStock.setBounds(795, 121, 115, 35);
        add(txStock);
        
        JLabel lblStock = new JLabel("Stock:");
        lblStock.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        lblStock.setBounds(716, 123, 59, 30);
        add(lblStock);
        
        JButton btnBack = new JButton(">");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFProducts p= new JFProducts();
				removeAll();
				p.setVisible(true);
				revalidate();
				repaint();
				
			}
		});
		btnBack.setBounds(920, 30, 50, 50);
		add(btnBack);
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

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de Imagen", "jpg", "jpeg", "png", "gif", "jfif"));

        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            // Obtener la ruta del archivo seleccionado
            File archivoSeleccionado = fileChooser.getSelectedFile();
            rutaImagenActual = archivoSeleccionado.getPath();
            rutaImagen = archivoSeleccionado.getPath(); // Actualizamos rutaImagen también
            lblFoto.setIcon(new ImageIcon(rutaImagen));
        }
    }


    private void guardarProducto() {
        try {
            // Validar datos
            String nombre = txNombre.getText();
            String marca = txMarca.getText();
            double precio = Double.parseDouble(txPrecio.getText());
            double alto = Double.parseDouble(txAlto.getText());
            double ancho = Double.parseDouble(txAncho.getText());
            double largo = Double.parseDouble(txLargo.getText());
            int stock = Integer.parseInt(txStock.getText());
            String tipo = (String) cboxTipo.getSelectedItem();
            String subtipo = (String) cboxSubtipo.getSelectedItem();

            if (rutaImagen.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor sube una imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Guardar en la base de datos
            try (Connection conexion = ConexionDB.obtenerConexion()) {
                ControladorCatalogo controlador = new ControladorCatalogo(conexion);
                controlador.insertarProducto(nombre, marca, precio, rutaImagen, alto, ancho, largo, stock, tipo, subtipo);
                JOptionPane.showMessageDialog(this, "Producto registrado exitosamente.");
                limpiarCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limpiarCampos() {
        txNombre.setText("");
        txPrecio.setText("");
        txMarca.setText("");
        txAlto.setText("");
        txAncho.setText("");
        txLargo.setText("");
        txStock.setText("");
        cboxTipo.setSelectedIndex(0);
        actualizarSubtipos();
        lblFoto.setIcon(null);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RegistroProductoCubo frame = new RegistroProductoCubo();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
