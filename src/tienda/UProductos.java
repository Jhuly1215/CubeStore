package tienda;

import Controladores.ControladorCatalogo;
import Controladores.ConexionDB;
import Modelos.Catalogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class UProductos extends JFrame {
    JScrollPane scrollPane;
    private boolean scrolled = false;
    private PanelAccesorio panelmods;
    private PanelCubos panelcubos;
    private JPanel panelTarjetas;
    private JPanel panelTarjetasContenido;
    private JPanel panelLateral;
    private UdescripcionCubo p;
    private UdescripccionAccesorio pa;
    private JLabel lblCubos;
    
    private int usuarioId;    
    
    public UProductos(int usuarioId) {
    	
    	this.usuarioId = usuarioId;
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

        JButton btnPedido = new JButton("Pedido");
        btnPedido.setBackground(new Color(130, 214, 103));
        btnPedido.setFont(new Font("Bahnschrift", Font.PLAIN, 13));
        btnPedido.setBounds(715, 11, 100, 40);
        panelTarjetas.add(btnPedido);

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
        btnCubos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPedido.setBackground(new Color(230, 110, 111));
                try (Connection conexion = ConexionDB.obtenerConexion()) {
                    ControladorCatalogo controlador = new ControladorCatalogo(conexion);
                    List<Catalogo> listaCubos = controlador.obtenerCatalogoPorTipo(1); // Tipo 1: Cubos
                    cargarBotonesProductos(listaCubos);
                    lblCubos.setText("Cubos");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton btnMods = new JButton("Mods");
        btnMods.setForeground(new Color(50, 85, 40));
        btnMods.setFont(new Font("Dialog", Font.BOLD, 14));
        btnMods.setBounds(0, 150, 160, 50);
        panelLateral.add(btnMods);
        btnMods.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPedido.setBackground(new Color(255, 219, 89));
                try (Connection conexion = ConexionDB.obtenerConexion()) {
                    ControladorCatalogo controlador = new ControladorCatalogo(conexion);
                    List<Catalogo> listaMods = controlador.obtenerCatalogoPorTipo(2); // Tipo 2: Mods
                    cargarBotonesProductos(listaMods);
                    lblCubos.setText("Mods");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton btnAccesorios = new JButton("Lubricantes y accesorios");
        btnAccesorios.setForeground(new Color(50, 85, 40));
        btnAccesorios.setFont(new Font("Dialog", Font.BOLD, 10));
        btnAccesorios.setBounds(0, 200, 160, 50);
        panelLateral.add(btnAccesorios);
        btnAccesorios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPedido.setBackground(new Color(113, 124, 231));
                try (Connection conexion = ConexionDB.obtenerConexion()) {
                    ControladorCatalogo controlador = new ControladorCatalogo(conexion);
                    List<Catalogo> listaAccesorios = controlador.obtenerCatalogoPorTipo(3); // Tipo 3: Accesorios
                    cargarBotonesProductos(listaAccesorios);
                    lblCubos.setText("Lubricantes y accesorios");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JLabel lblContacto = new JLabel("New label");
        lblContacto.setIcon(new ImageIcon(UProductos.class.getResource("/imagenes/contacto(155x120).jpg")));
        lblContacto.setBounds(0, 410, 155, 120);
        panelLateral.add(lblContacto);
        btnPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                UFPedido p = new UFPedido(usuarioId);
                p.setVisible(true);
            }
        });
    }

    // Función para cargar productos en botones
    private void cargarBotonesProductos(List<Catalogo> listaProductos) {
        panelTarjetasContenido.removeAll();
        for (Catalogo producto : listaProductos) {
            JButton btnprod = new JButton();
            btnprod.setLayout(new BorderLayout());

            if (producto.getIdTipo() == 3) { // Tipo 3: Accesorios
                PanelAccesorio panelDetalles = new PanelAccesorio(producto);
                btnprod.add(panelDetalles, BorderLayout.CENTER);
                btnprod.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pa = new UdescripccionAccesorio(producto, usuarioId);
                        pa.setSize(1000, 540);
                        pa.setLocation(0, 120);
                        pa.setVisible(true);
                        limpiarPaneles();
                        getContentPane().add(pa, BorderLayout.CENTER);
                        revalidate();
                        repaint();
                    }
                });
            } else { // Cubos o Mods
                PanelCubos panelDetalles = new PanelCubos(producto);
                btnprod.add(panelDetalles, BorderLayout.CENTER);
                btnprod.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        p = new UdescripcionCubo(producto, usuarioId);
                        p.setSize(1000, 540);
                        p.setLocation(0, 120);
                        p.setVisible(true);
                        limpiarPaneles();
                        getContentPane().add(p, BorderLayout.CENTER);
                        revalidate();
                        repaint();
                    }
                });
            }

            btnprod.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            btnprod.setPreferredSize(new Dimension(300, 500));
            panelTarjetasContenido.add(btnprod);
        }
        panelTarjetasContenido.revalidate();
        panelTarjetasContenido.repaint();
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
            // Acción para el botón de inicio
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
            UNosotros n = new UNosotros(usuarioId);
            n.setVisible(true);
        });
        getContentPane().add(btnNosotros);

        // Botón del usuario
        JButton btnUsuario = new JButton();
        btnUsuario.setIcon(new ImageIcon(UProductos.class.getResource("/imagenes/user(80x80).jpg")));
        btnUsuario.setBounds(882, 22, 80, 80);
        getContentPane().add(btnUsuario);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UProductos frame = new UProductos(1234);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
