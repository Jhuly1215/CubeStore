package tienda;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class UNosotros extends JFrame {

	JPanel panelInferior;
	private int usuarioId;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UNosotros frame = new UNosotros(1234);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UNosotros(int usuarioId) {
		this.usuarioId=usuarioId;
		getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
		//panel inferior
        panelInferior = new JPanel();
        panelInferior.setBounds(0, 130, 985, 540);
        panelInferior.setBackground(new Color(128, 128, 128));
        panelInferior.setLayout(null);
        getContentPane().add(panelInferior);
        
        JTextArea txtrProveerANuestros = new JTextArea();
        txtrProveerANuestros.setBackground(new Color(255, 255, 255));
        txtrProveerANuestros.setFont(new Font("Bahnschrift", Font.PLAIN, 18));
        txtrProveerANuestros.setLineWrap(true);
        txtrProveerANuestros.setWrapStyleWord(true);
        txtrProveerANuestros.setText("Proveer a nuestros clientes con la más amplia e innovadora gama de cubos Rubik, superando sus expectativas a través de un servicio excepcional, entrega oportuna y productos de alta calidad. Nos esforzamos por ser reconocidos como la elección preferida en el mercado del speedcubing, contribuyendo al disfrute y entretenimiento de nuestros clientes.");
        txtrProveerANuestros.setBounds(77, 135, 426, 188);
        panelInferior.add(txtrProveerANuestros);
        
        JTextArea txtrVisinDeSir = new JTextArea();
        txtrVisinDeSir.setFont(new Font("Bahnschrift", Font.PLAIN, 18));
        txtrVisinDeSir.setText("Ser la empresa referente a nivel nacional en la comercialización de cubos Rubik, reconocida por su excelencia en el servicio al cliente, innovación constante y compromiso con la calidad. Aspiramos a establecer estándares en la industria, manteniendo un crecimiento sostenible y generando un impacto positivo en la comunidad.");
        txtrVisinDeSir.setLineWrap(true);
        txtrVisinDeSir.setWrapStyleWord(true);
        txtrVisinDeSir.setBounds(76, 392, 829, 107);
        panelInferior.add(txtrVisinDeSir);
        
        JLabel lblMision = new JLabel("NUESTRA MISION");
        lblMision.setForeground(new Color(255, 255, 255));
        lblMision.setFont(new Font("Bahnschrift", Font.PLAIN, 25));
        lblMision.setBounds(78, 103, 267, 32);
        panelInferior.add(lblMision);
        
        JLabel lblVision = new JLabel("¿QUE VISIÓN TIENE SIR CUBE STORE?");
        lblVision.setForeground(Color.WHITE);
        lblVision.setFont(new Font("Bahnschrift", Font.PLAIN, 25));
        lblVision.setBounds(77, 356, 492, 32);
        panelInferior.add(lblVision);
        
        JLabel lblNosotros = new JLabel("NOSOTROS");
        lblNosotros.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNosotros.setForeground(Color.WHITE);
        lblNosotros.setFont(new Font("Bahnschrift", Font.BOLD, 55));
        lblNosotros.setBounds(-41, 27, 408, 65);
        panelInferior.add(lblNosotros);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon(Nosotros.class.getResource("/imagenes/nosotros(350x260).jpg")));
        lblNewLabel.setBounds(564, 102, 350, 260);
        panelInferior.add(lblNewLabel);
        
        
        
		//panel superior
        JButton btnLogo = new JButton("");
        btnLogo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
             UNosotros n = new UNosotros(usuarioId);
             n.setVisible(true);
        	}
        });
        btnLogo.setIcon(new ImageIcon(JFProducts.class.getResource("/imagenes/user(80x80).jpg")));
        btnLogo.setHorizontalTextPosition(SwingConstants.CENTER);
        btnLogo.setBounds(882, 22, 80, 80);
        getContentPane().add(btnLogo);

        JButton btnUsuario = new JButton("");
        btnUsuario.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
                UNosotros n = new UNosotros(usuarioId);
                n.setVisible(true);
        	}
        });
        btnUsuario.setIcon(new ImageIcon(JFProducts.class.getResource("/imagenes/logo(100x75).jpg")));
        btnUsuario.setHorizontalTextPosition(SwingConstants.CENTER);
        btnUsuario.setBounds(25, 25, 100, 75);
        getContentPane().add(btnUsuario);

        JButton btnProductos = new JButton("Productos");
        btnProductos.setHorizontalTextPosition(SwingConstants.CENTER);
        btnProductos.setIcon(new ImageIcon(JFProducts.class.getResource("/imagenes/rojo(1000x700).jpg")));
        btnProductos.setFont(new Font("Bahnschrift", Font.PLAIN, 12));
        btnProductos.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
             UProductos n = new UProductos(usuarioId);
             n.setVisible(true);
        	}
        });
        btnProductos.setBounds(250, 50, 100, 30);
        getContentPane().add(btnProductos);

        JButton btnInicio = new JButton("Inicio");
        btnInicio.setIcon(new ImageIcon(JFProducts.class.getResource("/imagenes/amarillo(1000x700).jpg")));
        btnInicio.setHorizontalTextPosition(SwingConstants.CENTER);
        btnInicio.setFont(new Font("Bahnschrift", Font.PLAIN, 13));
        btnInicio.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		   dispose();
                UInicio n = new UInicio(usuarioId);
                n.setVisible(true);
        	}
        });
        btnInicio.setBounds(175, 50, 75, 30);
        getContentPane().add(btnInicio);

        JButton btnNosotros = new JButton("Nosotros");
        btnNosotros.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
             UNosotros n = new UNosotros(usuarioId);
             n.setVisible(true);
        	}
        });
        btnNosotros.setIcon(new ImageIcon(JFProducts.class.getResource("/imagenes/Azul(1000x700).jpg")));
        btnNosotros.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNosotros.setFont(new Font("Bahnschrift", Font.PLAIN, 12));
        btnNosotros.setBounds(350, 50, 100, 30);
        getContentPane().add(btnNosotros);;
	}

}
