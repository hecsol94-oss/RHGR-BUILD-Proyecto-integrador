package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

public class NuevoCliente extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtSuperpoder;
	private JTextField txtColor;
	private JTextField txtTipo;

	public NuevoCliente() {
		setTitle("Nuevo / Editar Cliente");
		setBounds(100, 100, 400, 350);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblTitle = new JLabel("DATOS DEL CLIENTE");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setBounds(20, 11, 200, 25);
		contentPane.add(lblTitle);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 50, 100, 14);
		contentPane.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(20, 70, 340, 25);
		contentPane.add(txtNombre);

		JLabel lblSuperpoder = new JLabel("Superpoder:");
		lblSuperpoder.setBounds(20, 110, 100, 14);
		contentPane.add(lblSuperpoder);

		txtSuperpoder = new JTextField();
		txtSuperpoder.setBounds(20, 130, 340, 25);
		contentPane.add(txtSuperpoder);

		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(20, 170, 100, 14);
		contentPane.add(lblColor);

		txtColor = new JTextField();
		txtColor.setBounds(20, 190, 160, 25);
		contentPane.add(txtColor);

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(200, 170, 100, 14);
		contentPane.add(lblTipo);

		txtTipo = new JTextField();
		txtTipo.setBounds(200, 190, 160, 25);
		contentPane.add(txtTipo);

		JButton btnGuardar = new JButton("Guardar Cliente");
		btnGuardar.setBounds(50, 250, 130, 40);
		contentPane.add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(210, 250, 130, 40);
		contentPane.add(btnCancelar);
	}
}