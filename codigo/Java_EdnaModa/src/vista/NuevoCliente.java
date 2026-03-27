package vista;

// Importaciones necesarias para la interfaz gráfica
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

// Clase que representa la ventana para crear o editar un cliente
public class NuevoCliente extends JFrame {

	// Panel principal
	private JPanel contentPane;
	
	// Campos de texto para introducir los datos del cliente
	private JTextField txtNombre;
	private JTextField txtSuperpoder;
	private JTextField txtColor;
	private JTextField txtTipo;

	// Constructor de la ventana
	public NuevoCliente() {
		
		// Configuración básica de la ventana
		setTitle("Nuevo / Editar Cliente"); // Título
		setBounds(100, 100, 400, 350); // Posición y tamaño
		
		// Creación del panel principal con layout absoluto
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Título de la sección
		JLabel lblTitle = new JLabel("DATOS DEL CLIENTE");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14)); // Fuente en negrita
		lblTitle.setBounds(20, 11, 200, 25);
		contentPane.add(lblTitle);

		// --- Campo: Nombre ---
		
		// Etiqueta del nombre
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 50, 100, 14);
		contentPane.add(lblNombre);

		// Campo de texto para introducir el nombre
		txtNombre = new JTextField();
		txtNombre.setBounds(20, 70, 340, 25); // Ancho amplio
		contentPane.add(txtNombre);

		// --- Campo: Superpoder ---
		
		// Etiqueta del superpoder
		JLabel lblSuperpoder = new JLabel("Superpoder:");
		lblSuperpoder.setBounds(20, 110, 100, 14);
		contentPane.add(lblSuperpoder);

		// Campo de texto para el superpoder
		txtSuperpoder = new JTextField();
		txtSuperpoder.setBounds(20, 130, 340, 25);
		contentPane.add(txtSuperpoder);

		// --- Campo: Color ---
		
		// Etiqueta del color
		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(20, 170, 100, 14);
		contentPane.add(lblColor);

		// Campo de texto para el color
		txtColor = new JTextField();
		txtColor.setBounds(20, 190, 160, 25); // Mitad del ancho
		contentPane.add(txtColor);

		// --- Campo: Tipo ---
		
		// Etiqueta del tipo
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(200, 170, 100, 14);
		contentPane.add(lblTipo);

		// Campo de texto para el tipo
		txtTipo = new JTextField();
		txtTipo.setBounds(200, 190, 160, 25); // Mitad del ancho
		contentPane.add(txtTipo);

		// --- Botones ---
		
		// Botón para guardar el cliente
		JButton btnGuardar = new JButton("Guardar Cliente");
		btnGuardar.setBounds(50, 250, 130, 40);
		contentPane.add(btnGuardar);

		// Botón para cancelar la operación
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(210, 250, 130, 40);
		contentPane.add(btnCancelar);
	}
}