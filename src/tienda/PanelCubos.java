package tienda;

import Modelos.Catalogo;

import javax.swing.*;
import java.awt.*;

public class PanelCubos extends JPanel {

    /**
     * Constructor que crea un panel para mostrar los detalles de un producto del tipo "Cubo".
     *
     * @param producto Instancia de la clase Catalogo que representa un cubo.
     */
    public PanelCubos(Catalogo producto) {
        setBackground(new Color(105, 105, 105));
        setLayout(null);

        Font f = new Font("Bahnschrift", Font.PLAIN, 18);

        // Imagen del producto
        JLabel lblFoto = new JLabel();
        lblFoto.setIcon(new ImageIcon(producto.getRuta())); // Ruta de la imagen desde el objeto Catalogo
        lblFoto.setBounds(71, 62, 250, 300);
        add(lblFoto);

        // Código del producto
        JLabel lblCodigo = new JLabel("C: " + producto.getCodigo());
        lblCodigo.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        lblCodigo.setBounds(44, 27, 127, 24);
        add(lblCodigo);

        // Subtipo (si está disponible)
        JLabel lblSubtipo = new JLabel(producto.getIdSubtipo() != 0 ? "Subtipo: " + producto.getIdSubtipo() : "N/A");
        lblSubtipo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSubtipo.setFont(f);
        lblSubtipo.setBounds(181, 22, 165, 32);
        add(lblSubtipo);

        // Nombre del producto
        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(new Font("Bahnschrift", Font.BOLD, 18));
        lblNombre.setBounds(46, 384, 312, 30);
        add(lblNombre);

        // Precio del producto
        JLabel lblPrecio = new JLabel("Bs. " + producto.getPrecio());
        lblPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPrecio.setForeground(new Color(128, 0, 0));
        lblPrecio.setFont(new Font("Bahnschrift", Font.BOLD, 25));
        lblPrecio.setBounds(231, 412, 115, 42);
        add(lblPrecio);

        // Fondo decorativo
        JLabel lbfondo = new JLabel();
        lbfondo.setIcon(new ImageIcon(PanelCubos.class.getResource("/imagenes/blanco(1000x700).jpg")));
        lbfondo.setBounds(20, 15, 355, 450);
        add(lbfondo);
    }
}
