package vista;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class NuevoTraje extends JFrame {
	
    private JComboBox<Object> cbEstado;
    private JButton btnGuardar;
    private JButton btnCancelar;
	private JTextField txtNombre;
	
	public NuevoTraje() {
		
		setTitle("Editar / Nuevo traje");
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Acción al cerrar
		setBounds(100, 100, 400, 350); // Tamaño y posición
		setResizable(false); // Evita que el usuario cambie el tamaño
		
		JLabel lblTitle_1 = new JLabel("DATOS DEL TRAJE");
		lblTitle_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle_1.setBounds(21, 11, 200, 25);
		getContentPane().add(lblTitle_1);
		
		JLabel lblNombre_1 = new JLabel("Nombre:");
		lblNombre_1.setBounds(21, 50, 100, 14);
		getContentPane().add(lblNombre_1);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(21, 70, 340, 25);
		getContentPane().add(txtNombre);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(21, 118, 100, 14);
		getContentPane().add(lblEstado);
		
		cbEstado = new JComboBox<Object>(new Object[]{"diseño", "costura", "pruebas"});
		cbEstado.setBounds(21, 143, 340, 25);
		getContentPane().add(cbEstado);
		
	    btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(48, 213, 130, 40);
		getContentPane().add(btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(208, 213, 130, 40);
		getContentPane().add(btnCancelar);
		
	
	}
	
	// --- MÉTODOS GETTER PARA EL CONTROLADOR ---

    public JTextField getNombreTraje() {
        return txtNombre;
    }

    public String getCbEstado() {
        return (String) cbEstado.getSelectedItem();
    }
    
    public void setCbEstado(String estado) {
        cbEstado.setSelectedItem(estado);
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }
	
}
