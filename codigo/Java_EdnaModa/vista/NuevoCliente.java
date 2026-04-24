package vista;

//Importaciones necesarias para la interfaz gráfica
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;

//Clase que representa la ventana para crear o editar un cliente
public class NuevoCliente extends JPanel {

	// Panel principal
	private JPanel contentPane;
	
	// Campos de texto para introducir los datos del cliente
	private JTextField txtNombre;
	private JTextField txtSuperpoder;
	private JTextField txtColor;
	private JTextField txtTipo;
	private JTextField txtNombreTraje;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JComboBox<Object> cbEstado;

	// Constructor de la ventana
	public NuevoCliente() {
		
		// Configuración básica de la ventana
		setTitle("Nuevo / Editar Cliente"); // Título
		setBounds(100, 100, 400, 550); // Posición y tamaño
		
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
		lblSuperpoder.setBounds(20, 109, 100, 14);
		contentPane.add(lblSuperpoder);

		// Campo de texto para el superpoder
		txtSuperpoder = new JTextField();
		txtSuperpoder.setBounds(20, 134, 340, 25);
		contentPane.add(txtSuperpoder);

		// --- Campo: Color ---
		
		// Etiqueta del color
		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(20, 173, 100, 14);
		contentPane.add(lblColor);

		// Campo de texto para el color
		txtColor = new JTextField();
		txtColor.setBounds(20, 193, 160, 25); // Mitad del ancho
		contentPane.add(txtColor);

		// --- Campo: Tipo ---
		
		// Etiqueta del tipo
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(200, 173, 100, 14);
		contentPane.add(lblTipo);

		// Campo de texto para el tipo
		txtTipo = new JTextField();
		txtTipo.setBounds(200, 193, 160, 25); // Mitad del ancho
		contentPane.add(txtTipo);

		// --- Botones ---
		
		// Botón para guardar el cliente
		btnGuardar = new JButton("Guardar Cliente");
		btnGuardar.setBounds(47, 450, 130, 40);
		contentPane.add(btnGuardar);

		// Botón para cancelar la operación
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(207, 450, 130, 40);
		contentPane.add(btnCancelar);
		
		JLabel lblNombre_1 = new JLabel("Nombre:");
		lblNombre_1.setBounds(20, 287, 100, 14);
		contentPane.add(lblNombre_1);
		
		txtNombreTraje = new JTextField();
		txtNombreTraje.setBounds(20, 307, 340, 25);
		contentPane.add(txtNombreTraje);
		
		JLabel lblTitle_1 = new JLabel("DATOS DEL TRAJE");
		lblTitle_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle_1.setBounds(20, 248, 200, 25);
		contentPane.add(lblTitle_1);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(20, 355, 100, 14);
		contentPane.add(lblEstado);
		
		cbEstado = new JComboBox<>(new Object[]{"diseño", "costura", "pruebas"});
		cbEstado.setBounds(20, 380, 340, 25);
		contentPane.add(cbEstado);
	}
	
	//getters para el controlador acessar los componentes de la vista
		public JTextField getTxtNombre() {
			return txtNombre; 
		}
		
		public JTextField getTxtSuperpoder() {
			return txtSuperpoder; 
		}
	
		public JTextField getTxtColor() {
			return txtColor; 
		}
	
		public JTextField getTxtTipo() {
			return txtTipo; 
		}
	
		public JTextField getTxtNombreTraje() {
			return txtNombreTraje; 
		}
	
		public JComboBox<Object> getCbEstado() {
			return cbEstado; 
		}
	
		public JButton getBtnGuardar() {
			return btnGuardar; 
		}
	
		public JButton getBtnCancelar() {
			return btnCancelar; 
		}
	
}
