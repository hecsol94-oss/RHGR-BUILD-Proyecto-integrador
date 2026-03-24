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

public class ListaClientes extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable table;

	public ListaClientes() {
		setTitle("Lista de Clientes - EDNA MODA");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null); 

		txtBuscar = new JTextField();
		txtBuscar.setBounds(10, 40, 250, 25);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(270, 40, 89, 25);
		contentPane.add(btnBuscar);

		JButton btnNuevo = new JButton("+ Nuevo");
		btnNuevo.setBounds(385, 40, 89, 25);
		contentPane.add(btnNuevo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 80, 464, 220);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] { "Nombre", "Superpoder", "Tipo", "Trajes" }
		));
		scrollPane.setViewportView(table);

		JButton btnDetalle = new JButton("Ver detalle");
		btnDetalle.setBounds(10, 320, 110, 30);
		contentPane.add(btnDetalle);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(130, 320, 110, 30);
		contentPane.add(btnEditar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(364, 320, 110, 30);
		contentPane.add(btnEliminar);
		
		JButton btnTodos = new JButton("Todos");
		btnTodos.setBounds(92, 4, 89, 25);
		contentPane.add(btnTodos);
		
		JButton btnBuscar_2 = new JButton("Buscar");
		btnBuscar_2.setBounds(211, 5, 89, 25);
		contentPane.add(btnBuscar_2);
		
		JButton btnBuscar_3 = new JButton("Buscar");
		btnBuscar_3.setBounds(330, 5, 89, 25);
		contentPane.add(btnBuscar_3);
	}
}
