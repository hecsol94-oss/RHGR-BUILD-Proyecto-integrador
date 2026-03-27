package vista;

// Importaciones necesarias para la interfaz gráfica y eventos
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// Clase principal de la ventana que hereda de JFrame
public class VentanaOficial extends JFrame {

	// Panel principal donde se añaden todos los componentes
	private JPanel contentPane;

	// Constructor de la ventana
	public VentanaOficial() {
		
		// Configuración básica de la ventana
		setTitle("RGHR EDNA MODA - Sistema de Gestión"); // Título de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Acción al cerrar
		setBounds(100, 100, 700, 500); // Tamaño y posición
		
		// --- BARRA DE MENÚ SUPERIOR ---
		
		// Creación de la barra de menú
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// Menú "Citas"
		JMenu Citas = new JMenu("Citas");
		menuBar.add(Citas);
		
		// Opción para ver lista de citas
		JMenuItem ListaCitas = new JMenuItem("Lista de citas");
		Citas.add(ListaCitas);
		
		// Opción para crear nueva cita (deshabilitada por defecto)
		JMenuItem nuevaCita = new JMenuItem("Nueva cita");
		Citas.add(nuevaCita);
		nuevaCita.setEnabled(false);
		
		// Menú "Clientes" (deshabilitado)
		JMenu Clientes = new JMenu("Clientes");
		menuBar.add(Clientes);
		Clientes.setEnabled(false);
		
		// Subopciones de clientes
		JMenuItem listaClientes = new JMenu("Lista de clientes");
		Clientes.add(listaClientes);
		
		JMenuItem nuevoCliente = new JMenu("Nuevo cliente");
		Clientes.add(nuevoCliente);
		
		// Menú "Talleres" (deshabilitado)
		JMenu Talleres = new JMenu("Talleres");
		menuBar.add(Talleres);
		Talleres.setEnabled(false);
		
		// Subopciones de talleres
		JMenuItem listaTalleres = new JMenu("Lista de talleres");
		Talleres.add(listaTalleres);
		
		JMenuItem nuevoTaller = new JMenu("Nuevo taller");
		Talleres.add(nuevoTaller);

		// Creación del panel principal
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Márgenes internos
		setContentPane(contentPane);
		contentPane.setLayout(null); // Layout absoluto

		// --- ETIQUETA DE USUARIO / APRENDIZ ---
		
		// Etiqueta para mostrar el usuario (alineada a la derecha)
		JLabel lblUsuario = new JLabel("");
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setBounds(400, 11, 220, 20);
		contentPane.add(lblUsuario);
		
		// Etiqueta para "Salir"
		JLabel lblSalir = new JLabel("");
		lblSalir.setForeground(Color.BLUE);
		lblSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSalir.setBounds(625, 11, 40, 20);
		contentPane.add(lblSalir);

		// --- SECCIÓN INFERIOR: ACCESOS DIRECTOS ---
		
		// Texto descriptivo de todas las citas
		JLabel lblTodasCitas = new JLabel("TODAS LAS CITAS:");
		lblTodasCitas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTodasCitas.setBounds(45, 310, 150, 20);
		contentPane.add(lblTodasCitas);
		
		// Panel contenedor de número de citas
		JPanel boxCitas = new JPanel();
		boxCitas.setBackground(new Color(192, 192, 192)); // Color gris
		boxCitas.setBorder(new LineBorder(new Color(0, 0, 0))); // Borde negro
		boxCitas.setBounds(45, 335, 170, 80);
		contentPane.add(boxCitas);
		
		// Etiqueta donde se mostrará el número de citas
		JLabel lblTodasLasCitas = new JLabel("");
		boxCitas.add(lblTodasLasCitas);

		// Texto descriptivo de "Mis citas"
		JLabel lblMisCitas = new JLabel("MIS CITAS:");
		lblMisCitas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMisCitas.setBounds(260, 310, 150, 20);
		contentPane.add(lblMisCitas);
		
		// Panel contenedor de mis citas
		JPanel boxMisCitas = new JPanel();
		boxMisCitas.setBackground(new Color(192, 192, 192));
		boxMisCitas.setBorder(new LineBorder(new Color(0, 0, 0)));
		boxMisCitas.setBounds(260, 335, 170, 80);
		contentPane.add(boxMisCitas);
		
		// Etiqueta para número de mis citas
		JLabel lblNumeroDeMisCitas = new JLabel("");
		boxMisCitas.add(lblNumeroDeMisCitas);

		// Texto descriptivo de talleres
		JLabel lblTalleresTab = new JLabel("TALLERES:");
		lblTalleresTab.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTalleresTab.setBounds(475, 310, 150, 20);
		contentPane.add(lblTalleresTab);
		
		// Panel contenedor de talleres
		JPanel boxTalleres = new JPanel();
		boxTalleres.setBackground(new Color(192, 192, 192));
		boxTalleres.setBorder(new LineBorder(new Color(0, 0, 0)));
		boxTalleres.setBounds(475, 335, 170, 80);
		contentPane.add(boxTalleres);
		
		// Etiqueta para número de talleres
		JLabel lblNumeroDeTalleres = new JLabel("");
		boxTalleres.add(lblNumeroDeTalleres);
		
		// Imagen del logo
		JLabel imagen = new JLabel("");
		imagen.setIcon(new ImageIcon(VentanaMaestro.class.getResource("/img/LOGO RHGR_BUILD.png")));
		imagen.setBounds(188, 0, 316, 302);
		contentPane.add(imagen);
	}
}