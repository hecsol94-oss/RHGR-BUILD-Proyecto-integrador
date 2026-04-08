package vista;

// Importaciones necesarias para la interfaz gráfica y gestión de eventos
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

// Clase principal que representa la ventana del usuario tipo "Maestro"
public class VentanaMaestro extends JFrame {

	// Panel principal donde se colocan todos los componentes
	private JPanel contentPane;

	// Constructor de la ventana
	public VentanaMaestro() {
		
		// Configuración básica de la ventana
		setTitle("RGHR EDNA MODA - Sistema de Gestión"); // Título
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar aplicación
		setBounds(100, 100, 700, 500); // Posición y tamaño
		
		// --- BARRA DE MENÚ SUPERIOR ---
		
		// Creación de la barra de menú
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// Menú "Citas"
		JMenu Citas = new JMenu("Citas");
		menuBar.add(Citas);
		
		// Opción: ver lista de citas
		JMenuItem ListaCitas = new JMenuItem("Lista de citas");
		Citas.add(ListaCitas);
		
		// Opción: crear nueva cita
		JMenuItem nuevaCita = new JMenuItem("Nueva cita");
		Citas.add(nuevaCita);
		
		// Menú "Clientes"
		JMenu Clientes = new JMenu("Clientes");
		menuBar.add(Clientes);
		
		JMenuItem listaClientes = new JMenuItem("Lista de clientes");
		Clientes.add(listaClientes);
		
		JMenuItem nuevoCliente = new JMenuItem("Nuevo cliente");
		Clientes.add(nuevoCliente);
		
		// Menú "Talleres"
		JMenu Talleres = new JMenu("Talleres");
		menuBar.add(Talleres);
		
		JMenuItem listaTalleres = new JMenuItem("Lista de talleres");
		Talleres.add(listaTalleres);
		
		JMenuItem nuevoTaller = new JMenuItem("Nuevo taller");
		Talleres.add(nuevoTaller);

		// Creación del panel principal
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Márgenes
		setContentPane(contentPane);
		contentPane.setLayout(null); // Layout absoluto

		// --- ETIQUETA DE USUARIO / APRENDIZ ---
		
		// Etiqueta para mostrar el usuario actual
		JLabel lblUsuario = new JLabel("");
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT); // Alineación derecha
		lblUsuario.setBounds(400, 11, 220, 20);
		contentPane.add(lblUsuario);
		
		// Etiqueta para acción de salir (logout)
		JLabel lblSalir = new JLabel("");
		lblSalir.setForeground(Color.BLUE); // Color azul
		lblSalir.setFont(new Font("Tahoma", Font.BOLD, 11)); // Fuente en negrita
		lblSalir.setBounds(625, 11, 40, 20);
		contentPane.add(lblSalir);

		// --- SECCIÓN INFERIOR: ACCESOS DIRECTOS ---
		
		// Texto: todas las citas
		JLabel lblTodasCitas = new JLabel("TODAS LAS CITAS:");
		lblTodasCitas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTodasCitas.setBounds(45, 310, 150, 20);
		contentPane.add(lblTodasCitas);
		
		// Panel contenedor para el número total de citas
		JPanel boxCitas = new JPanel();
		boxCitas.setBackground(new Color(192, 192, 192)); // Color gris
		boxCitas.setBorder(new LineBorder(new Color(0, 0, 0))); // Borde negro
		boxCitas.setBounds(45, 335, 170, 80);
		contentPane.add(boxCitas);
		
		// Etiqueta donde se mostrará el número total de citas
		JLabel lblTodasLasCitas = new JLabel("");
		boxCitas.add(lblTodasLasCitas);

		// Texto: mis citas
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
		
		// Etiqueta donde se mostrará el número de mis citas
		JLabel lblNumeroDeMisCitas = new JLabel("");
		boxMisCitas.add(lblNumeroDeMisCitas);

		// Texto: talleres
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
		
		// Etiqueta donde se mostrará el número de talleres
		JLabel lblNumeroDeTalleres = new JLabel("");
		boxTalleres.add(lblNumeroDeTalleres);
		
		// Imagen del logo de la aplicación
		JLabel imagen = new JLabel("");
		imagen.setIcon(new ImageIcon(VentanaMaestro.class.getResource("/img/LOGO RHGR_BUILD.png")));
		imagen.setBounds(188, 0, 316, 302);
		contentPane.add(imagen);
	}
}