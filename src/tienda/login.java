package tienda;

import Controladores.ConexionDB;
import Controladores.ControladorUsuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class login extends JFrame {
    private JPanel contentPane;
    private JTextField txUsername;
    private JPasswordField passwordField;
    private JButton btnIngresar;
    private JButton btnRegistrarse;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                login frame = new login();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(Color.BLACK);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel Fondo = new JLabel();
        Fondo.setIcon(new ImageIcon(login.class.getResource("/imagenes/Logo1.jpg")));
        Fondo.setBounds(242, 42, 501, 321);
        contentPane.add(Fondo);

        txUsername = new JTextField();
        txUsername.setBounds(422, 404, 310, 30);
        contentPane.add(txUsername);
        txUsername.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(422, 460, 310, 30);
        contentPane.add(passwordField);

        JLabel txContrasenia = new JLabel("Contraseña:");
        txContrasenia.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        txContrasenia.setForeground(Color.WHITE);
        txContrasenia.setBounds(268, 460, 122, 31);
        contentPane.add(txContrasenia);

        JLabel txUsuario = new JLabel("Usuario:");
        txUsuario.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        txUsuario.setForeground(Color.WHITE);
        txUsuario.setBounds(268, 408, 92, 23);
        contentPane.add(txUsuario);

        btnIngresar = new JButton("Iniciar sesión");
        btnIngresar.setBackground(Color.WHITE);
        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });
        btnIngresar.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        btnIngresar.setBounds(288, 540, 180, 50);
        contentPane.add(btnIngresar);

        btnRegistrarse = new JButton("¡Regístrate!");
        btnRegistrarse.setBackground(new Color(255, 219, 89));
        btnRegistrarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegistroUsuario n = new RegistroUsuario();
                n.setVisible(true);
            }
        });
        btnRegistrarse.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        btnRegistrarse.setBounds(517, 540, 180, 50);
        contentPane.add(btnRegistrarse);
    }

    private void autenticar() {
        String username = txUsername.getText();
        String password = new String(passwordField.getPassword());

        // Caso especial para el administrador
        if (username.equals("1234") && password.equals("josias123")) {
            JOptionPane.showMessageDialog(this, "Inicio de sesión como administrador exitoso", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            inicio adminInicio = new inicio();
            adminInicio.setVisible(true);
            return;
        }

        // Autenticación normal desde la base de datos
        try (Connection conexion = ConexionDB.obtenerConexion()) {
            ControladorUsuario controlador = new ControladorUsuario(conexion);

            int ci;
            try {
                ci = Integer.parseInt(username); // Convertir username a número
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El usuario debe ser un número (CI válido)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (controlador.autenticarUsuario(ci, password)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                UInicio userInicio = new UInicio(ci);
                userInicio.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
