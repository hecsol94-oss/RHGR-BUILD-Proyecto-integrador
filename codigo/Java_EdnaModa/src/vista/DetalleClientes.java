package vista;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.border.LineBorder;

// Clase que representa la ventana de detalle de un cliente
public class DetalleClientes extends JFrame {

    // Panel principal de la ventana
	private JPanel contentPane;

    // Constructor
	public DetalleClientes() {
	    // Configuración básica de la ventana
		setTitle("Detalles del Cliente - EDNA MODA"); // Título de la ventana
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Se cierra solo esta ventana
		setBounds(100, 100, 450, 450); // Tamaño y posición inicial
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Bordes internos
		setContentPane(contentPane);
		contentPane.setLayout(null); // Layout absoluto

        // Título principal de la ventana
		JLabel lblTitulo = new JLabel("INFORMACIÓN DEL CLIENTE");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo.setBounds(20, 11, 250, 25);
		contentPane.add(lblTitulo);

		// Bloque de información personal (Nombre, Superpoder, Colores, Tipo)
		JTextArea txtInfoPersonal = new JTextArea();
		txtInfoPersonal.setBorder(new LineBorder(new Color(192, 192, 192))); // Borde gris
		txtInfoPersonal.setEditable(false); // Solo lectura
		txtInfoPersonal.setText("Nombre: \nSuperpoder: \nColores: \nTipo (Héroe/Villano): "); // Texto por defecto
		txtInfoPersonal.setBounds(20, 47, 390, 100); // Posición y tamaño
		contentPane.add(txtInfoPersonal);

		// Etiqueta del bloque de información de trajes
		JLabel lblTrajes = new JLabel("Informacion de los Trajes:");
		lblTrajes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTrajes.setBounds(20, 160, 187, 14);
		contentPane.add(lblTrajes);

		// Bloque de información de los trajes del cliente
		JTextArea txtInfoTrajes = new JTextArea();
		txtInfoTrajes.setBorder(new LineBorder(new Color(192, 192, 192))); // Borde gris
		txtInfoTrajes.setEditable(false); // Solo lectura
		txtInfoTrajes.setText("Nombre del traje: \r\nEstado:"); // Texto por defecto
		txtInfoTrajes.setBounds(20, 180, 390, 100);
		contentPane.add(txtInfoTrajes);

		// Botón para editar la información del cliente
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(20, 350, 100, 30);
		contentPane.add(btnEditar);

		// Botón para eliminar al cliente (en rojo)
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setForeground(Color.RED);
		btnEliminar.setBounds(130, 350, 100, 30);
		contentPane.add(btnEliminar);

		// Botón para volver a la ventana anterior
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(310, 350, 100, 30);
		contentPane.add(btnVolver);
        
        // Separador visual para separar botones de la información
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 320, 390, 2);
		contentPane.add(separator);
	}
}