package tienda;

import Modelos.Catalogo;

import javax.swing.*;
import java.awt.*;

public class PanelAccesorio extends JPanel {
    public PanelAccesorio(Catalogo accesorio) {
        setBackground(new Color(105, 105, 105));
        setLayout(null);

        Font f = new Font("Bahnschrift", Font.PLAIN, 18);

        JLabel lblFoto = new JLabel();
        lblFoto.setIcon(new ImageIcon(accesorio.getRuta()));
        lblFoto.setBounds(73, 72, 250, 300);
        add(lblFoto);

        JLabel lblCodigo = new JLabel("C: " + accesorio.getCodigo());
        lblCodigo.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        lblCodigo.setBounds(49, 37, 127, 24);
        add(lblCodigo);

        JLabel lblTamanio = new JLabel("T: " + accesorio.getTamano() + " cm");
        lblTamanio.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        lblTamanio.setBounds(268, 37, 127, 24);
        add(lblTamanio);

        JLabel lblNombre = new JLabel(accesorio.getNombre());
        lblNombre.setFont(new Font("Bahnschrift", Font.BOLD, 18));
        lblNombre.setBounds(35, 383, 312, 30);
        add(lblNombre);

        JLabel lblPrecio = new JLabel("Bs. " + accesorio.getPrecio());
        lblPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPrecio.setForeground(new Color(128, 0, 0));
        lblPrecio.setFont(new Font("Bahnschrift", Font.BOLD, 25));
        lblPrecio.setBounds(232, 412, 115, 42);
        add(lblPrecio);

        JLabel lbfondo = new JLabel();
        lbfondo.setIcon(new ImageIcon(PanelAccesorio.class.getResource("/imagenes/blanco(1000x700).jpg")));
        lbfondo.setBounds(20, 15, 355, 450);
        add(lbfondo);
    }
}
