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

public class DetalleClientes extends JFrame {

	private JPanel contentPane;

	public DetalleClientes() {
		setTitle("Detalles del Cliente - EDNA MODA");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("INFORMACIÓN DEL CLIENTE");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo.setBounds(20, 11, 250, 25);
		contentPane.add(lblTitulo);

		// Bloque de información personal (Nombre, Superpoder, etc.)
		JTextArea txtInfoPersonal = new JTextArea();
		txtInfoPersonal.setBorder(new LineBorder(new Color(192, 192, 192)));
		txtInfoPersonal.setEditable(false);
		txtInfoPersonal.setText("Nombre: \nSuperpoder: \nColores: \nTipo (Héroe/Villano): ");
		txtInfoPersonal.setBounds(20, 47, 390, 100);
		contentPane.add(txtInfoPersonal);

		// Bloque de información de trajes
		JLabel lblTrajes = new JLabel("Informacion de los Trajes:");
		lblTrajes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTrajes.setBounds(20, 160, 187, 14);
		contentPane.add(lblTrajes);

		JTextArea txtInfoTrajes = new JTextArea();
		txtInfoTrajes.setBorder(new LineBorder(new Color(192, 192, 192)));
		txtInfoTrajes.setEditable(false);
		txtInfoTrajes.setText("Nombre del traje: \r\nEstado:");
		txtInfoTrajes.setBounds(20, 180, 390, 100);
		contentPane.add(txtInfoTrajes);

		// Botones de acción inferiores
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(20, 350, 100, 30);
		contentPane.add(btnEditar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setForeground(Color.RED);
		btnEliminar.setBounds(130, 350, 100, 30);
		contentPane.add(btnEliminar);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(310, 350, 100, 30);
		contentPane.add(btnVolver);
        
        // Separador visual
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 320, 390, 2);
		contentPane.add(separator);
	}
}
