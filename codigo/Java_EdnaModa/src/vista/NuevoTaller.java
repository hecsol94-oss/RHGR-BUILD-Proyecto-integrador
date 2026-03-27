package vista;

// Importaciones necesarias para la interfaz gráfica
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

// Clase que representa la ventana para crear un nuevo taller
public class NuevoTaller extends JFrame {

	// Panel principal de la ventana
	private JPanel contentPane;
	
	// Campos de texto para introducir datos del taller
	private JTextField txtNomeSala;
	private JTextField txtTipoSala;

	// Constructor de la ventana
	public NuevoTaller() {
		
		// Título de la ventana
		setTitle("Nuevo Taller");
		
		// Configuración básica de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Acción al cerrar
		setBounds(100, 100, 320, 240); // Tamaño y posición
		setResizable(false); // Evita que el usuario cambie el tamaño

		// Panel principal con fondo blanco y márgenes
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15)); // Espaciado interno
		setContentPane(contentPane);
		contentPane.setLayout(null); // Layout absoluto (posiciones manuales)

		// --- Fila 1: Nome da la Sala ---
		
		// Etiqueta para el nombre de la sala
		JLabel lblNomeSala = new JLabel("Nome da la Sala:");
		lblNomeSala.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNomeSala.setBounds(20, 25, 120, 14);
		contentPane.add(lblNomeSala);

		// Campo de texto para introducir el nombre de la sala
		txtNomeSala = new JTextField();
		txtNomeSala.setBounds(20, 50, 260, 20); // Ocupa casi todo el ancho
		contentPane.add(txtNomeSala);
		txtNomeSala.setColumns(10);

		// --- Fila 2: Tipo de sala ---
		
		// Etiqueta para el tipo de sala
		JLabel lblTipoSala = new JLabel("Tipo de sala:");
		lblTipoSala.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTipoSala.setBounds(20, 85, 120, 14);
		contentPane.add(lblTipoSala);

		// Campo de texto para introducir el tipo de sala
		txtTipoSala = new JTextField();
		txtTipoSala.setBounds(20, 110, 260, 20); // Ocupa casi todo el ancho
		contentPane.add(txtTipoSala);
		txtTipoSala.setColumns(10);

		// --- Fila 3: Botones ---
		
		// Botón para guardar los datos introducidos
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGuardar.setBounds(30, 155, 100, 30);
		contentPane.add(btnGuardar);

		// Botón para cancelar la operación
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelar.setBounds(170, 155, 100, 30);
		contentPane.add(btnCancelar);
	}
}