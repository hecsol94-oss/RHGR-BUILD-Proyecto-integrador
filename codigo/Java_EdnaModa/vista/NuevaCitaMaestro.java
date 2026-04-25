
package vista;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

// Clase que representa la ventana para crear o editar una cita (versión Maestro)
public class NuevaCitaMaestro extends JPanel {

	// Componentes de selección de datos
	private JComboBox<String> cbCliente;
	private JComboBox<String> cbTraje;
	private JComboBox<String> cbTaller;

	// Campos de texto para datos de la cita
	private JTextField txtOficial;
	private JTextField txtFecha;
	private JTextField txtHora;
	private JTextField txtDuracion;

	// Botones de acción
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JButton btnCliente;
	private JButton btnTaller;

	// Constructor de la ventana
	public NuevaCitaMaestro() {

		setTitle("Nueva / Editar Cita");
		setBounds(100, 100, 450, 450);
		getContentPane().setLayout(null);

		// ComboBox para seleccionar el cliente (se rellena desde el controlador)
		cbCliente = new JComboBox<>(new String[] {});
		cbCliente.setBounds(20, 66, 180, 25);
		getContentPane().add(cbCliente);

		// ComboBox para seleccionar el traje (se filtra según el cliente elegido)
		cbTraje = new JComboBox<>(new String[] {});
		cbTraje.setBounds(230, 66, 180, 25);
		getContentPane().add(cbTraje);

		// ComboBox para seleccionar el taller (se rellena desde la BBDD)
		cbTaller = new JComboBox<>(new String[] {});
		cbTaller.setBounds(20, 126, 180, 25);
		getContentPane().add(cbTaller);

		// Campo de texto para el oficial responsable
		txtOficial = new JTextField();
		txtOficial.setBounds(230, 126, 180, 25);
		getContentPane().add(txtOficial);

		// Campo de texto para la fecha (formato yyyy-MM-dd)
		txtFecha = new JTextField();
		txtFecha.setBounds(20, 186, 100, 25);
		getContentPane().add(txtFecha);

		// Campo de texto para la hora (formato HH:mm)
		txtHora = new JTextField();
		txtHora.setBounds(167, 187, 100, 25);
		getContentPane().add(txtHora);

		// Campo de texto para la duración en horas
		txtDuracion = new JTextField();
		txtDuracion.setBounds(310, 187, 100, 25);
		getContentPane().add(txtDuracion);

		// Botón para guardar la cita
		btnGuardar = new JButton("Guardar Cita");
		btnGuardar.setBounds(20, 344, 150, 40);
		getContentPane().add(btnGuardar);

		// Botón para cancelar la operación
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(260, 344, 150, 40);
		getContentPane().add(btnCancelar);

		// Botón para registrar un cliente nuevo si no existe en el combo
		btnCliente = new JButton("¿Existe el cliente? Regístralo");
		btnCliente.setBounds(22, 247, 388, 25);
		getContentPane().add(btnCliente);

		// Botón para añadir un taller nuevo si no existe en el combo
		btnTaller = new JButton("¿Existe el taller? Añádelo");
		btnTaller.setBounds(24, 283, 386, 25);
		getContentPane().add(btnTaller);

		JLabel lblDatosDeLa = new JLabel("DATOS DE LA CITA");
		lblDatosDeLa.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDatosDeLa.setBounds(20, 11, 200, 25);
		getContentPane().add(lblDatosDeLa);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(20, 41, 100, 14);
		getContentPane().add(lblCliente);

		JLabel lblTraje = new JLabel("Traje:");
		lblTraje.setBounds(230, 41, 100, 14);
		getContentPane().add(lblTraje);

		JLabel lblTaller = new JLabel("Taller:");
		lblTaller.setBounds(20, 101, 100, 14);
		getContentPane().add(lblTaller);

		JLabel lblOficialResponsable = new JLabel("Oficial responsable:");
		lblOficialResponsable.setBounds(230, 101, 155, 14);
		getContentPane().add(lblOficialResponsable);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(20, 161, 140, 14);
		getContentPane().add(lblFecha);

		JLabel lblHora = new JLabel("Hora:");
		lblHora.setBounds(167, 162, 110, 14);
		getContentPane().add(lblHora);

		JLabel lblDuracion = new JLabel("Duración:");
		lblDuracion.setBounds(310, 162, 100, 14);
		getContentPane().add(lblDuracion);

	}

	// Getters para que el controlador acceda a los componentes

	public JComboBox<String> getCbCliente() {
		return cbCliente;
	}

	public JComboBox<String> getCbTraje() {
		return cbTraje;
	}

	public JComboBox<String> getCbTaller() {
		return cbTaller;
	}

	public JTextField getTxtOficial() {
		return txtOficial;
	}

	public JTextField getTxtFecha() {
		return txtFecha;
	}

	public JTextField getTxtHora() {
		return txtHora;
	}

	public JTextField getTxtDuracion() {
		return txtDuracion;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnCliente() {
		return btnCliente;
	}

	public JButton getBtnTaller() {
		return btnTaller;
	}
}