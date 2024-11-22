package tienda;

import Controladores.ConexionDB;
import Controladores.ControladorUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class RegistroUsuario extends JFrame {
    private JPanel contentPane;
    private JTextField txUsuario;
    private JTextField txContrasenia;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RegistroUsuario frame = new RegistroUsuario();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public RegistroUsuario() {
        getContentPane().setBackground(new Color(30, 34, 35));
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(240, 228, 531, 298);
        getContentPane().add(panel);
        panel.setLayout(null);

        JButton btnRegistrar = new JButton("Regístrate");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Está seguro de registrarse con este usuario y contraseña?",
                        "Confirmación de registro",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirmacion == JOptionPane.YES_OPTION) {
                    registrarUsuario();
                }
            }
        });
        btnRegistrar.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
        btnRegistrar.setForeground(SystemColor.window);
        btnRegistrar.setBackground(new Color(100, 100, 100));
        btnRegistrar.setBounds(132, 217, 300, 50);
        panel.add(btnRegistrar);

        JLabel lblUsuario = new JLabel("Ingrese usuario (CI):");
        lblUsuario.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        lblUsuario.setBounds(32, 89, 150, 18);
        panel.add(lblUsuario);

        JLabel lblContrasenia = new JLabel("Ingrese contraseña:");
        lblContrasenia.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        lblContrasenia.setBounds(32, 144, 150, 18);
        panel.add(lblContrasenia);

        txUsuario = new JTextField();
        txUsuario.setText("");
        txUsuario.setBounds(201, 80, 262, 34);
        panel.add(txUsuario);
        txUsuario.setColumns(10);

        txContrasenia = new JTextField();
        txContrasenia.setText("");
        txContrasenia.setColumns(10);
        txContrasenia.setBounds(201, 135, 262, 34);
        panel.add(txContrasenia);

        JLabel lblRegistrate = new JLabel("¡REGÍSTRATE!");
        lblRegistrate.setForeground(new Color(255, 255, 255));
        lblRegistrate.setFont(new Font("Bahnschrift", Font.PLAIN, 40));
        lblRegistrate.setBounds(395, 84, 280, 49);
        getContentPane().add(lblRegistrate);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon(RegistroUsuario.class.getResource("/imagenes/Logo (300x150).jpg")));
        lblNewLabel.setBounds(38, 37, 300, 150);
        getContentPane().add(lblNewLabel);

        JButton btnBack = new JButton(">");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                login n = new login();
                n.setVisible(true);
            }
        });
        btnBack.setBackground(SystemColor.window);
        btnBack.setBounds(866, 68, 50, 50);
        getContentPane().add(btnBack);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    private void registrarUsuario() {
        try (Connection conexion = ConexionDB.obtenerConexion()) {
            ControladorUsuario controlador = new ControladorUsuario(conexion);

            // Validar entrada
            String ciString = txUsuario.getText();
            String contrasenia = txContrasenia.getText();

            if (ciString.isEmpty() || contrasenia.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Por favor, complete todos los campos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int ci;
            try {
                ci = Integer.parseInt(ciString);
                if (ci <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "El CI debe ser un número positivo",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Intentar registrar al usuario
            if (controlador.registrarUsuario(ci, contrasenia)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Registro exitoso",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
                login n = new login();
                n.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al registrar el usuario. El CI ya puede estar en uso.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Error al conectar con la base de datos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
