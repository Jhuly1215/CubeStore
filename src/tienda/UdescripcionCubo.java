package tienda;

import Modelos.Catalogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class UdescripcionCubo extends JPanel {
    private JTextField txNombre;
    private JTextField txPrecio;
    private JTextField txMarca;
    private JTextField txAlto;
    private JTextField txAncho;
    private JTextField txLargo;
    private JTextField txCodigo, txTipo;
    private JLabel lblFoto;
    private JLabel lblAñadirCubo;
    private JComboBox<Integer> cboxCantidad;
    private JButton btnCarrito;
    
    private int usuarioId;
    /**
     * Constructor que recibe un producto del tipo Cubo desde el modelo Catalogo.
     *
     * @param producto Instancia del modelo Catalogo que contiene los datos del cubo.
     */
    public UdescripcionCubo(Catalogo producto, int usuarioId) {
    	
    	this.usuarioId=usuarioId;
        setBackground(Color.WHITE);
        setLayout(null);
        setBounds(0, 0, 1000, 540);
        Font f = new Font("Bahnschrift", Font.PLAIN, 20);

        // Etiquetas para los campos
        JLabel lblCodigo = new JLabel("Código:");
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

        // Campos de texto
        txCodigo = new JTextField();
        txCodigo.setEditable(false);
        txCodigo.setBounds(500, 113, 410, 35);
        add(txCodigo);

        txNombre = new JTextField();
        txNombre.setEditable(false);
        txNombre.setBounds(500, 172, 410, 35);
        add(txNombre);

        txPrecio = new JTextField();
        txPrecio.setEditable(false);
        txPrecio.setBounds(500, 231, 169, 35);
        add(txPrecio);

        txMarca = new JTextField();
        txMarca.setEditable(false);
        txMarca.setBounds(500, 293, 410, 35);
        add(txMarca);

        txAlto = new JTextField();
        txAlto.setEditable(false);
        txAlto.setBounds(500, 356, 80, 35);
        add(txAlto);

        txAncho = new JTextField();
        txAncho.setEditable(false);
        txAncho.setBounds(667, 356, 80, 35);
        add(txAncho);

        txLargo = new JTextField();
        txLargo.setEditable(false);
        txLargo.setBounds(830, 356, 80, 35);
        add(txLargo);

        txTipo = new JTextField();
        txTipo.setEditable(false);
        txTipo.setBounds(760, 237, 150, 30);
        add(txTipo);

        // Configuración de los campos con datos del producto
        txCodigo.setText(String.valueOf(producto.getCodigo()));
        txNombre.setText(producto.getNombre());
        txPrecio.setText(String.valueOf(producto.getPrecio()));
        txMarca.setText(producto.getMarca());
        txAlto.setText(String.valueOf(producto.getAlto()));
        txAncho.setText(String.valueOf(producto.getAncho()));
        txLargo.setText(String.valueOf(producto.getLargo()));
        txTipo.setText(String.valueOf(producto.getIdSubtipo())); // Usa un mapeo si tienes nombres específicos para los tipos

        // Imagen del producto
        lblFoto = new JLabel();
        lblFoto.setIcon(new ImageIcon(producto.getRuta())); // Usa la ruta desde el objeto Catalogo
        lblFoto.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        lblFoto.setBounds(64, 113, 250, 300);
        add(lblFoto);

        // Título
        lblAñadirCubo = new JLabel("Descripción:");
        lblAñadirCubo.setFont(new Font("Bahnschrift", Font.PLAIN, 40));
        lblAñadirCubo.setBounds(62, 37, 494, 50);
        add(lblAñadirCubo);

        // Botón para regresar
        JButton btnBack = new JButton(">");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UProductos p = new UProductos(usuarioId);
                removeAll();
                p.setVisible(true);
                revalidate();
                repaint();
            }
        });
        btnBack.setBounds(915, 40, 50, 50);
        add(btnBack);

        // ComboBox para cantidad
        cboxCantidad = new JComboBox<>();
        cboxCantidad.setBounds(582, 477, 50, 36);
        add(cboxCantidad);

        for (int i = 1; i <= 5; i++) {
            cboxCantidad.addItem(i);
        }

        JLabel lblCantidad = new JLabel("Cantidad: ");
        lblCantidad.setFont(new Font("Bahnschrift", Font.PLAIN, 17));
        lblCantidad.setHorizontalAlignment(SwingConstants.LEFT);
        lblCantidad.setBounds(465, 484, 91, 18);
        add(lblCantidad);
        
        btnCarrito = new JButton("Añadir al carrito");
        btnCarrito.setBounds(730, 470, 180, 50);
        add(btnCarrito);
        // Botón "Añadir al carrito" (sin funcionalidad por ahora)
        btnCarrito.addActionListener(e -> {
            int cantidad = (int) cboxCantidad.getSelectedItem();
            if (cantidad > 0) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Carrito.txt", true))) {
                    writer.write("Código: " + producto.getCodigo() + "\n");
                    writer.write("Nombre: " + producto.getNombre() + "\n");
                    writer.write("Precio: " + producto.getPrecio() + "\n");
                    writer.write("Cantidad: " + cantidad + "\n");
                    writer.write("Costo: " + (producto.getPrecio() * cantidad) + "\n");
                    writer.write("------------------------------\n");
                    JOptionPane.showMessageDialog(this, "Producto añadido al carrito.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una cantidad mayor a 0.");
            }
        });

    }
}
