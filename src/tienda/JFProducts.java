package tienda;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Controladores.ConexionDB;
import Controladores.ControladorCatalogo;
import Modelos.Catalogo;

import javax.swing.ImageIcon;

public class JFProducts extends JFrame {
    JScrollPane scrollPane;
    private boolean scrolled = false;
    private JPanel panelTarjetas;
    private JPanel panelTarjetasContenido;
    private JPanel panelLateral;
    private JLabel lblCubos;
    private JButton btnNuevo;

    public JFProducts() {
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Inicialización del panel principal
        panelTarjetas = new JPanel();
        panelTarjetas.setBackground(new Color(105, 105, 105));
        panelTarjetas.setBounds(160, 120, 825, 540);
        getContentPane().add(panelTarjetas);
        panelTarjetas.setLayout(null);

        // Botón "Nuevo"
        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBackground(new Color(130, 214, 103));
        btnNuevo.setFont(new Font("Bahnschrift", Font.PLAIN, 13));
        btnNuevo.setBounds(715, 11, 100, 40);
        panelTarjetas.add(btnNuevo);

        panelTarjetasContenido = new JPanel();
        panelTarjetasContenido.setBounds(160, 190, 825, 470);
        panelTarjetasContenido.setLayout(new GridLayout(0, 2));

        scrollPane = new JScrollPane(panelTarjetasContenido);

        lblCubos = new JLabel();
        lblCubos.setForeground(new Color(255, 255, 255));
        lblCubos.setText("Cubos");
        lblCubos.setFont(new Font("Bahnschrift", Font.BOLD, 30));
        lblCubos.setBounds(21, 26, 513, 36);
        panelTarjetas.add(lblCubos);
        scrollPane.setBounds(160, 190, 825, 470);
        getContentPane().add(scrollPane);

        configurarBarraSuperior();

        // Inicialización de conexión y controlador
        try (Connection conexion = ConexionDB.obtenerConexion()) {
            ControladorCatalogo controlador = new ControladorCatalogo(conexion);

            // Cargar cubos iniciales
            List<Catalogo> listaCubos = controlador.obtenerCatalogoPorTipo(1); // Tipo 1: Cubos
            cargarBotonesProductos(listaCubos);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de la base de datos.");
        }

        // Barra lateral
        configurarBarraLateral();

        // Configuración del botón "Nuevo"
        configurarBotonNuevo();
    }

    private void configurarBarraLateral() {
        panelLateral = new JPanel();
        panelLateral.setBackground(new Color(30, 34, 35));
        getContentPane().add(panelLateral);
        panelLateral.setBounds(0, 120, 160, 540);
        panelLateral.setLayout(null);

        JButton btnCubos = new JButton("Cubos");
        btnCubos.setForeground(new Color(50, 85, 40));
        btnCubos.setFont(new Font("Dialog", Font.BOLD, 14));
        btnCubos.setBounds(0, 100, 160, 50);
        panelLateral.add(btnCubos);
        btnCubos.addActionListener(e -> cargarProductos(1, "Cubos"));

        JButton btnMods = new JButton("Mods");
        btnMods.setForeground(new Color(50, 85, 40));
        btnMods.setFont(new Font("Dialog", Font.BOLD, 14));
        btnMods.setBounds(0, 150, 160, 50);
        panelLateral.add(btnMods);
        btnMods.addActionListener(e -> cargarProductos(2, "Mods"));

        JButton btnAccesorios = new JButton("Lubricantes y accesorios");
        btnAccesorios.setForeground(new Color(50, 85, 40));
        btnAccesorios.setFont(new Font("Dialog", Font.BOLD, 10));
        btnAccesorios.setBounds(0, 200, 160, 50);
        panelLateral.add(btnAccesorios);
        btnAccesorios.addActionListener(e -> cargarProductos(3, "Lubricantes y accesorios"));

        JLabel lblContacto = new JLabel();
        lblContacto.setIcon(new ImageIcon(JFProducts.class.getResource("/imagenes/contacto(155x120).jpg")));
        lblContacto.setBounds(0, 410, 155, 120);
        panelLateral.add(lblContacto);
    }

    private void configurarBotonNuevo() {
        btnNuevo.addActionListener(e -> {
            if(lblCubos.getText().equals("Lubricantes y accesorios")){
            	RegistroProductoAccesorio registroAccesorio = new RegistroProductoAccesorio();
                mostrarPanelRegistro(registroAccesorio);
            }
            else {
            	RegistroProductoCubo registroCubo = new RegistroProductoCubo();
                mostrarPanelRegistro(registroCubo);
            }
        });
    }

    private void mostrarPanelRegistro(JPanel panelRegistro) {
        panelRegistro.setSize(1000, 540);
        panelRegistro.setLocation(0, 120);
        limpiarPaneles();
        getContentPane().add(panelRegistro, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void cargarProductos(int tipo, String titulo) {
        lblCubos.setText(titulo);
        try (Connection conexion = ConexionDB.obtenerConexion()) {
            ControladorCatalogo controlador = new ControladorCatalogo(conexion);
            List<Catalogo> listaProductos = controlador.obtenerCatalogoPorTipo(tipo);
            cargarBotonesProductos(listaProductos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void cargarBotonesProductos(List<Catalogo> listaProductos) {
        panelTarjetasContenido.removeAll();
        for (Catalogo producto : listaProductos) {
            JButton btnprod = new JButton();
            btnprod.setLayout(new BorderLayout());

            if (producto.getIdTipo() == 3) { // Tipo 3: Accesorios
                PanelAccesorio panelDetalles = new PanelAccesorio(producto);
                btnprod.add(panelDetalles, BorderLayout.CENTER);
                btnprod.addActionListener(e -> mostrarPanelDescripcion(new panelDescripcionAccesorio(producto)));
            } else { // Cubos o Mods
                PanelCubos panelDetalles = new PanelCubos(producto);
                btnprod.add(panelDetalles, BorderLayout.CENTER);
                btnprod.addActionListener(e -> mostrarPanelDescripcion(new panelDescripcion(producto)));
            }

            btnprod.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            btnprod.setPreferredSize(new Dimension(300, 500));
            panelTarjetasContenido.add(btnprod);
        }
        panelTarjetasContenido.revalidate();
        panelTarjetasContenido.repaint();
    }

    private void mostrarPanelDescripcion(JPanel panelDescripcion) {
        panelDescripcion.setSize(1000, 540);
        panelDescripcion.setLocation(0, 120);
        limpiarPaneles();
        getContentPane().add(panelDescripcion, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void limpiarPaneles() {
        getContentPane().remove(panelTarjetasContenido);
        getContentPane().remove(panelTarjetas);
        getContentPane().remove(panelLateral);
        getContentPane().remove(scrollPane);
        panelTarjetasContenido.setVisible(false);
        panelTarjetas.setVisible(false);
        panelLateral.setVisible(false);
        scrollPane.setVisible(false);
    }

    private void configurarBarraSuperior() {
        // Botón de Inicio
        JButton btnInicio = new JButton("Inicio");
        btnInicio.setIcon(new ImageIcon(UProductos.class.getResource("/imagenes/amarillo(1000x700).jpg")));
        btnInicio.setHorizontalTextPosition(SwingConstants.CENTER);
        btnInicio.setFont(new Font("Bahnschrift", Font.PLAIN, 13));
        btnInicio.setBounds(175, 50, 75, 30);
        btnInicio.addActionListener(e -> {
        	dispose();
            inicio n = new inicio();
            n.setVisible(true);
        });
        getContentPane().add(btnInicio);

        // Botón de Productos
        JButton btnProductos = new JButton("Productos");
        btnProductos.setHorizontalTextPosition(SwingConstants.CENTER);
        btnProductos.setIcon(new ImageIcon(UProductos.class.getResource("/imagenes/rojo(1000x700).jpg")));
        btnProductos.setFont(new Font("Bahnschrift", Font.PLAIN, 12));
        btnProductos.setBounds(250, 50, 100, 30);
        getContentPane().add(btnProductos);

        // Botón de Nosotros
        JButton btnNosotros = new JButton("Nosotros");
        btnNosotros.setIcon(new ImageIcon(UProductos.class.getResource("/imagenes/Azul(1000x700).jpg")));
        btnNosotros.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNosotros.setFont(new Font("Bahnschrift", Font.PLAIN, 12));
        btnNosotros.setBounds(350, 50, 100, 30);
        btnNosotros.addActionListener(e -> {
        	dispose();
            Nosotros n = new Nosotros();
            n.setVisible(true);
        });
        getContentPane().add(btnNosotros);
        JButton btnPedidos = new JButton("Pedidos");
        btnPedidos.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
                UFTodosPedidos n = new UFTodosPedidos();
                n.setVisible(true);
        	}
        });
        btnPedidos.setIcon(new ImageIcon(JFProducts.class.getResource("/imagenes/verde(1000x700).jpg")));
        btnPedidos.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPedidos.setFont(new Font("Bahnschrift", Font.PLAIN, 12));
        btnPedidos.setBounds(450, 50, 100, 30);
        getContentPane().add( btnPedidos);
        // Botón del usuario
        JButton btnUsuario = new JButton();
        btnUsuario.setIcon(new ImageIcon(UProductos.class.getResource("/imagenes/user(80x80).jpg")));
        btnUsuario.setBounds(882, 22, 80, 80);
        getContentPane().add(btnUsuario);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFProducts frame = new JFProducts();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

