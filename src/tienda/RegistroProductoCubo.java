package tienda;

import javax.swing.JPanel;


import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.io.IOException;

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
	JComboBox cboxSubtipo;
	private static JFProducts jfProductsInstance;

	public RegistroProductoCubo() {
		setLayout(null);
		setBounds(0,0,1000,580);
		Font f=new Font("Bahnschrift", Font.PLAIN, 20);
		
		JLabel lblCodigo = new JLabel("Codigo:");
		lblCodigo.setFont(f);
		lblCodigo.setBounds(402, 120, 88, 20);
		add(lblCodigo);
		
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
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(f);
		lblTipo.setBounds(698, 240, 54, 25);
		add(lblTipo);
		
		JLabel lblAlto = new JLabel("Alto:");
		lblAlto.setFont(f);
		lblAlto.setBounds(429, 361, 54, 25);
		add(lblAlto);
		
		JLabel lblAncho = new JLabel("Ancho:");
		lblAncho.setFont(f);
		lblAncho.setBounds(598, 361, 71, 25);
		add(lblAncho);
		
		JLabel lblLargo = new JLabel("Largo:");
		lblLargo.setFont(f);
		lblLargo.setBounds(757, 361, 64, 25);
		add(lblLargo);
		
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
		
		txAlto = new JTextField();
		txAlto.setColumns(10);
		txAlto.setBounds(500, 356, 80, 35);
		add(txAlto);
		
		txAncho = new JTextField();
		txAncho.setColumns(10);
		txAncho.setBounds(667, 356, 80, 35);
		add(txAncho);
		
		txLargo = new JTextField();
		txLargo.setColumns(10);
		txLargo.setBounds(830, 356, 80, 35);
		add(txLargo);
		
		lblFoto = new JLabel("imagen");
		lblFoto.setIcon(new ImageIcon());
		lblFoto.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblFoto.setBounds(64, 113, 250, 300);
		add(lblFoto);
		
		btnSubirImagen = new JButton("Subir imagen");
		btnSubirImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de Imagen", "jpg", "jpeg", "png", "gif","jfif"));

                int seleccion = fileChooser.showOpenDialog(null);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    // Obtener la ruta del archivo seleccionado
                    String rutaArchivo = fileChooser.getSelectedFile().getPath();

                    // Mostrar la imagen en el JLabel
                    lblFoto.setIcon(new ImageIcon(rutaArchivo));
                }
			}
		});
		btnSubirImagen.setBounds(194, 440, 120, 40);
		add(btnSubirImagen);
		
		btnSave = new JButton("Guardar");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de registrar " +txCodigo.getText()+": "+ txNombre.getText()+"?", "Confirmación de registro",
                        JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                	guardarDatosEnArchivo();
                    
                    
                }
			}
		});
		btnSave.setBounds(510, 435, 400, 50);
		add(btnSave);
		
		lblAñadirCubo = new JLabel("Nuevo Cubo:");
		lblAñadirCubo.setFont(new Font("Bahnschrift", Font.PLAIN, 40));
		lblAñadirCubo.setBounds(62, 37, 252, 50);
		add(lblAñadirCubo);
		
		txCodigo = new JTextField();
		txCodigo.setColumns(10);
		txCodigo.setBounds(500, 113, 410, 35);
		add(txCodigo);
		
		cboxSubtipo = new JComboBox();
		cboxSubtipo.setBounds(759, 235, 151, 35);
		add(cboxSubtipo);
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
		btnBack.setBounds(915, 40, 50, 50);
		add(btnBack);
	}
	private void guardarDatosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            String nombre = txNombre.getText();
            String marca = txMarca.getText();
            String tipo = cboxSubtipo.getSelectedItem().toString();
            int codigo;
            double precio, alto, ancho, largo;

            // Validar que los campos numéricos contengan valores válidos
            try {
                codigo = Integer.parseInt(txCodigo.getText());
                precio = Double.parseDouble(txPrecio.getText());
                alto = Double.parseDouble(txAlto.getText());
                ancho = Double.parseDouble(txAncho.getText());
                largo = Double.parseDouble(txLargo.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos para código, precio, alto, ancho y largo.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método si hay error
            }
            String rutaImagen = lblFoto.getIcon() != null ? lblFoto.getIcon().toString() : "";

            writer.write("Codigo: " + codigo + "\n");
            writer.write("Nombre: " + nombre + "\n");
            writer.write("Precio: " + precio + "\n");
            writer.write("Marca: " + marca + "\n");
            writer.write("Ruta: " + rutaImagen + "\n");
            writer.write("Tipo: " + tipo + "\n");
            writer.write("Alto: " + alto + "\n");
            writer.write("Ancho: " + ancho + "\n");
            writer.write("Largo: " + largo + "\n");
            writer.write("------------------------------\n");

            JOptionPane.showMessageDialog(null, "Cubo registrado.");
            limpiarCampos();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para limpiar los campos después de guardar
    private void limpiarCampos() {
    	txCodigo.setText("");
    	txNombre.setText("");
        txMarca.setText("");
        txPrecio.setText("");
        txAlto.setText("");
        txAncho.setText("");
        txLargo.setText("");
        cboxSubtipo.setSelectedIndex(0);
        lblFoto.setIcon(new ImageIcon(""));
        
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
