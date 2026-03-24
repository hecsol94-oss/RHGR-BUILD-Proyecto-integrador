package vista;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class VentanaMaestro extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				VentanaMaestro frame = new VentanaMaestro();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public VentanaMaestro() {
		setTitle("RGHR EDNA MODA - Sistema de Gestión");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		
		// --- BARRA DE MENÚ SUPERIOR ---
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnCitas = new JMenu("Citas");
		menuBar.add(mnCitas);
		mnCitas.add(new JMenuItem("Lista de las citas"));
		mnCitas.add(new JMenuItem("Nueva cita"));
		
		JMenu mnClientes = new JMenu("Clientes");
		menuBar.add(mnClientes);
		mnClientes.add(new JMenuItem("Lista de clientes"));
		mnClientes.add(new JMenuItem("Nuevo cliente"));
		
		JMenu mnTalleres = new JMenu("Talleres");
		menuBar.add(mnTalleres);
		mnTalleres.add(new JMenuItem("Nuevo taller"));
		mnTalleres.add(new JMenuItem("Lista de talleres"));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// --- ETIQUETA DE USUARIO / APRENDIZ ---
		JLabel lblUsuario = new JLabel("");
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setBounds(400, 11, 220, 20);
		contentPane.add(lblUsuario);
		
		JLabel lblSalir = new JLabel("");
		lblSalir.setForeground(Color.BLUE);
		lblSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSalir.setBounds(625, 11, 40, 20);
		contentPane.add(lblSalir);

		// --- SECCIÓN INFERIOR: ACCESOS DIRECTOS ---
		
		// Todas las citas
		JLabel lblTodasCitas = new JLabel("TODAS LAS CITAS:");
		lblTodasCitas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTodasCitas.setBounds(45, 310, 150, 20);
		contentPane.add(lblTodasCitas);
		
		JPanel boxCitas = new JPanel();
		boxCitas.setBackground(new Color(192, 192, 192));
		boxCitas.setBorder(new LineBorder(new Color(0, 0, 0)));
		boxCitas.setBounds(45, 335, 170, 80);
		contentPane.add(boxCitas);
		
		JLabel lblTodasLasCitas = new JLabel("");
		boxCitas.add(lblTodasLasCitas);

		// Mis citas
		JLabel lblMisCitas = new JLabel("MIS CITAS:");
		lblMisCitas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMisCitas.setBounds(260, 310, 150, 20);
		contentPane.add(lblMisCitas);
		
		JPanel boxMisCitas = new JPanel();
		boxMisCitas.setBackground(new Color(192, 192, 192));
		boxMisCitas.setBorder(new LineBorder(new Color(0, 0, 0)));
		boxMisCitas.setBounds(260, 335, 170, 80);
		contentPane.add(boxMisCitas);
		
		JLabel lblNumeroDeMisCitas = new JLabel("");
		boxMisCitas.add(lblNumeroDeMisCitas);

		// Talleres
		JLabel lblTalleresTab = new JLabel("TALLERES:");
		lblTalleresTab.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTalleresTab.setBounds(475, 310, 150, 20);
		contentPane.add(lblTalleresTab);
		
		JPanel boxTalleres = new JPanel();
		boxTalleres.setBackground(new Color(192, 192, 192));
		boxTalleres.setBorder(new LineBorder(new Color(0, 0, 0)));
		boxTalleres.setBounds(475, 335, 170, 80);
		contentPane.add(boxTalleres);
		
		JLabel lblNumeroDeTalleres = new JLabel("New label");
		boxTalleres.add(lblNumeroDeTalleres);
		
		JLabel imagen = new JLabel("");
		imagen.setIcon(new ImageIcon(VentanaMaestro.class.getResource("/img/LOGO RHGR_BUILD.png")));
		imagen.setBounds(188, 0, 316, 302);
		contentPane.add(imagen);
	}
}