package vista;

//Importaciones necesarias para la interfaz gráfica
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

//Clase que representa la ventana de listado de clientes
public class ListaClientes extends JPanel {

	// Panel principal
	private JPanel contentPane;
	
	// Campo de texto para búsqueda
	private JTextField txtBuscar;
	
	// Tabla donde se mostrarán los clientes
	private JTable table;
	
	private JButton btnBuscar;
	private JButton btnNuevo;
	private JButton btnDetalle;
	private JButton btnEditar;
	private JButton btnVolver;
	private JButton btnTodos;
	private JButton btnHeroe;
	private JButton btnVillano;
	private JButton btnEliminar;

	// Constructor de la ventana
	public ListaClientes() {
		
		// Configuración básica de la ventana
		setTitle("Lista de Clientes - EDNA MODA"); // Título
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Solo cierra esta ventana
		setBounds(100, 100, 500, 400); // Tamaño y posición
		
		// Creación del panel principal
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Márgenes
		setContentPane(contentPane);
		contentPane.setLayout(null); // Layout absoluto

		// Campo de texto para introducir búsqueda
		txtBuscar = new JTextField();
		txtBuscar.setBounds(10, 40, 250, 25);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		// Botón para ejecutar la búsqueda
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(270, 40, 89, 25);
		contentPane.add(btnBuscar);

		// Botón para crear un nuevo cliente
		btnNuevo = new JButton("+ Nuevo");
		btnNuevo.setBounds(385, 40, 89, 25);
		contentPane.add(btnNuevo);

		// Scroll para la tabla (permite desplazamiento)
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 80, 464, 220);
		contentPane.add(scrollPane);

		// Creación de la tabla
		table = new JTable();
		
		// Modelo de la tabla (columnas definidas, sin datos iniciales)
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] { "Nombre", "Superpoder", "Tipo", "Trajes" }
		));
		
		// Se añade la tabla al scroll
		scrollPane.setViewportView(table);
		
		// Botón para mostrar todos los clientes
		btnTodos = new JButton("Todos");
		btnTodos.setBounds(92, 4, 89, 25);
		contentPane.add(btnTodos);
		
		// Botón para filtrar por héroes
		btnHeroe = new JButton("Heroe");
		btnHeroe.setBounds(211, 5, 89, 25);
		contentPane.add(btnHeroe);
		
		// Botón para filtrar por villanos
		btnVillano = new JButton("Villano");
		btnVillano.setBounds(330, 5, 89, 25);
		contentPane.add(btnVillano);
		
		// Botón para ver detalle del cliente seleccionado
		btnDetalle = new JButton("Ver detalle");
		btnDetalle.setBounds(10, 320, 110, 30);
		contentPane.add(btnDetalle);

		// Botón para editar el cliente seleccionado
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(127, 320, 110, 30);
		contentPane.add(btnEditar);
		
		// Botón para eliminar el cliente seleccionado
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(247, 320, 110, 30);
		contentPane.add(btnEliminar);
		
		// Botón para volver a la ventana anterior
		btnVolver = new JButton("Volver");
		btnVolver.setBounds(364, 320, 110, 30);
		contentPane.add(btnVolver);
	}
	public JTextField getTxtBuscar() {
		return txtBuscar; 
	}
	
	public JTable getTable() {
		return table; 
	}
	
	public JButton getBtnBuscar() {
		return btnBuscar; 
	}
	
	public JButton getBtnNuevo() {
		return btnNuevo; 
	}
	
	public JButton getBtnDetalle() {
		return btnDetalle; 
	}
	
	public JButton getBtnEditar() {
		return btnEditar; 
	}
	
	public JButton getBtnVolver() {
		return btnVolver; 
	}
	
	public JButton getBtnTodos() {
		return btnTodos; 
	}
	
	public JButton getBtnHeroe() {
		return btnHeroe; 
	}
	
	public JButton getBtnVillano() {
		return btnVillano; 
	}
	
	public JButton getBtnEliminar() {
		return btnEliminar; 
	}
	
	public void deshabilitarBotones() {
    	btnEditar.setEnabled(false);
    	btnEliminar.setEnabled(false);
    	btnNuevo.setEnabled(false);

    }
	
}
