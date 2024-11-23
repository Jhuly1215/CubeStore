package tienda;

import Modelos.Catalogo;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class UdescripccionAccesorio extends JPanel {
    private JTextField txNombre;
    private JTextField txPrecio;
    private JTextField txMarca;
    private JTextField txTamanio;
    private JTextField txCodigo;
    private JLabel lblFoto;
    private JButton btnCarrito;
    private JComboBox<Integer> cboxCantidad;
    private int usuarioId;
    public UdescripccionAccesorio(Catalogo accesorio, int usuarioId) {
    	this.usuarioId=usuarioId;
        setLayout(null);
        setBounds(0, 0, 1000, 580);
        Font f = new Font("Bahnschrift", Font.PLAIN, 20);

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

        JLabel lblTamanio = new JLabel("Tamaño:");
        lblTamanio.setFont(f);
        lblTamanio.setBounds(698, 240, 88, 25);
        add(lblTamanio);

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

        txTamanio = new JTextField();
        txTamanio.setEditable(false);
        txTamanio.setBounds(795, 231, 110, 35);
        add(txTamanio);

        txCodigo.setText(String.valueOf(accesorio.getCodigo()));
        txNombre.setText(accesorio.getNombre());
        txPrecio.setText(String.valueOf(accesorio.getPrecio()));
        txMarca.setText(accesorio.getMarca());
        txTamanio.setText(String.valueOf(accesorio.getTamano()));

        lblFoto = new JLabel();
        lblFoto.setIcon(new ImageIcon(accesorio.getRuta()));
        lblFoto.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        lblFoto.setBounds(64, 113, 250, 300);
        add(lblFoto);

        JLabel lblTitulo = new JLabel("Detalle accesorio/lubricante:");
        lblTitulo.setFont(new Font("Bahnschrift", Font.PLAIN, 40));
        lblTitulo.setBounds(62, 37, 671, 50);
        add(lblTitulo);

        JButton btnBack = new JButton(">");
        btnBack.addActionListener(e -> {
            UProductos p = new UProductos(usuarioId);
            removeAll();
            p.setVisible(true);
            revalidate();
            repaint();
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
        // Botón "Añadir al carrito"
        btnCarrito.addActionListener(e -> {
            int cantidad = (int) cboxCantidad.getSelectedItem();
            if (cantidad > 0) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Carrito.txt", true))) {
                    writer.write("Código: " + accesorio.getCodigo() + "\n");
                    writer.write("Nombre: " + accesorio.getNombre() + "\n");
                    writer.write("Precio: " + accesorio.getPrecio() + "\n");
                    writer.write("Cantidad: " + cantidad + "\n");
                    writer.write("Costo: " + (accesorio.getPrecio() * cantidad) + "\n");
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
