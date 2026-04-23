package vista;

// Importaciones necesarias para la interfaz gráfica
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Taller;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Font;
import java.awt.Color;

// Clase que representa la ventana para crear un nuevo taller
public class NuevoTaller extends JFrame {

	private static final String String = null;

	// Panel principal de la ventana
	private JPanel contentPane;
	
	// Campos de texto para introducir datos del taller
	private JTextField txtNomeSala;
	// Selector para el tipo de sala
	private JComboBox cbTipoSala;
	// Botones de acción
	private JButton btnGuardar;
	private JButton btnCancelar;
    
	// Constructor de la ventana
	public NuevoTaller() {
		
		// Título de la ventana
		setTitle("Nuevo Taller");
		
		// Configuración básica de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Acción al cerrar
		setBounds(100, 100, 401, 349); // Tamaño y posición
		setResizable(false); // Evita que el usuario cambie el tamaño

		// Panel principal con fondo blanco y márgenes
		contentPane = new JPanel();
	    contentPane.setLayout(null); // Diseño absoluto (coordenadas fijas)
	    setContentPane(contentPane);

		// --- Fila 1: Nome da la Sala ---
		
		// Etiqueta para el nombre de la sala
		JLabel lblNombreSala = new JLabel("Nombre de la Sala:");
		lblNombreSala.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNombreSala.setBounds(20, 49, 120, 14);
		contentPane.add(lblNombreSala);

		// Campo de texto para introducir el nombre de la sala
		txtNomeSala = new JTextField();
		txtNomeSala.setBounds(20, 74, 340, 24); // Ocupa casi todo el ancho
		contentPane.add(txtNomeSala);
		txtNomeSala.setColumns(10);

		// --- Fila 2: Tipo de sala ---
		
		// Etiqueta para el tipo de sala
		JLabel lblTipoSala = new JLabel("Tipo de sala:");
		lblTipoSala.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTipoSala.setBounds(20, 115, 120, 14);
		contentPane.add(lblTipoSala);

		// ComboBox para seleccionar el tipo de sala (Opciones predefinidas)

		cbTipoSala = new JComboBox(new String[] {"diseño", "costura", "pruebas"});
        cbTipoSala.setBounds(20, 140, 340, 24);
        getContentPane().add(cbTipoSala);;

		// --- Fila 3: Botones ---
		
		// Botón para guardar los datos introducidos
		btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGuardar.setBounds(77, 248, 100, 30);
		contentPane.add(btnGuardar);

		// Botón para cancelar la operación
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelar.setBounds(217, 248, 100, 30);
		contentPane.add(btnCancelar);
		
		// Título visual dentro del panel
		JLabel lblDatosDelTaller = new JLabel("DATOS DEL TALLER");
		lblDatosDelTaller.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDatosDelTaller.setBounds(20, 11, 200, 25);
		contentPane.add(lblDatosDelTaller);
	}
	
	// Método para obtener el texto del campo nombre
	public String getNombreSala() {
		return txtNomeSala.getText();
	}
	
	// Método para obtener el valor seleccionado del combo
	public String getTipoSala() {
		return (String) cbTipoSala.getSelectedItem();
	}
	
	// Getter del botón guardar para el controlador
	public JButton getBtnGuardar() {
		return btnGuardar;
	}
	
	// Getter del botón cancelar para el controlador
	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	
	// Método para rellenar los campos al editar un taller existente
	public void cargarDatos(Taller taller) {
	    txtNomeSala.setText(taller.getNombre());
	    cbTipoSala.setSelectedItem(taller.getTipo());
	}
	
}