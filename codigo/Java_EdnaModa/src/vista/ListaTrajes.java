package vista;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class ListaTrajes extends JFrame {

	// Panel principal donde se añaden todos los componentes
	private JPanel contentPane;
	
	// Campo de texto para realizar búsquedas
	private JTextField txtBuscar;
	
	// Tabla donde se mostrarán los trajes
	private JTable table;

	// Constructor de la ventana
	public ListaTrajes() {
		
		// ---------------- CONFIGURACIÓN DE LA VENTANA ----------------
		
		setTitle("Lista de Trajes - EDNA MODA"); // Título de la ventana
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
		setBounds(100, 100, 500, 400); // Posición y tamaño
		
		// ---------------- PANEL PRINCIPAL ----------------
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Márgenes internos
		setContentPane(contentPane);
		contentPane.setLayout(null); // Layout absoluto (posicionamiento manual)

		// ---------------- BÚSQUEDA ----------------

		// Campo de texto donde el usuario introduce el texto a buscar
		txtBuscar = new JTextField();
		txtBuscar.setBounds(10, 40, 250, 25);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		// Botón para ejecutar la búsqueda según el texto introducido
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(270, 40, 89, 25);
		contentPane.add(btnBuscar);

		// Botón para abrir la ventana de creación de un nuevo traje
		JButton btnNuevo = new JButton("+ Nuevo");
		btnNuevo.setBounds(385, 40, 89, 25);
		contentPane.add(btnNuevo);

		// ---------------- TABLA DE DATOS ----------------

		// Scroll que contiene la tabla (permite desplazarse si hay muchos datos)
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 80, 464, 220);
		contentPane.add(scrollPane);

		// Creación de la tabla
		table = new JTable();
		
		// Modelo de la tabla: define columnas pero sin datos iniciales
		table.setModel(new DefaultTableModel(
			new Object[][] {}, // Sin filas inicialmente
			new String[] { "Nombre", "Estado" } // Columnas
		));
		
		// Se añade la tabla dentro del scroll
		scrollPane.setViewportView(table);

		// ---------------- BOTONES DE ACCIÓN ----------------

		// Botón para ver el detalle del traje seleccionado
		JButton btnDetalle = new JButton("Ver detalle");
		btnDetalle.setBounds(10, 320, 110, 30);
		contentPane.add(btnDetalle);

		// Botón para editar el traje seleccionado
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(127, 320, 110, 30);
		contentPane.add(btnEditar);

		// Botón para eliminar el traje seleccionado
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(247, 320, 110, 30);
		contentPane.add(btnEliminar);

		// Botón para cerrar la ventana o volver atrás
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(364, 320, 110, 30);
		contentPane.add(btnVolver);
		
		// ---------------- FILTROS POR ESTADO ----------------
		
		// Botón para mostrar todos los trajes sin filtro
		JButton btnTodos = new JButton("Todos");
		btnTodos.setBounds(21, 3, 89, 25);
		contentPane.add(btnTodos);
		
		// Botón para filtrar trajes en estado "Diseño"
		JButton btnDiseño = new JButton("Diseño");
		btnDiseño.setBounds(140, 4, 89, 25);
		contentPane.add(btnDiseño);
		
		// Botón para filtrar trajes en estado "Costura"
		JButton btnCostura = new JButton("Costura");
		btnCostura.setBounds(259, 4, 89, 25);
		contentPane.add(btnCostura);
		
		// Botón para filtrar trajes en estado "Taller"
		JButton btnTaller = new JButton("Taller");
		btnTaller.setBounds(374, 4, 89, 25);
		contentPane.add(btnTaller);
	}
}