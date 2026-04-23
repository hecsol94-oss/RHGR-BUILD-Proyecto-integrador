package vista;

// Importaciones necesarias para la interfaz gráfica
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;

// Clase que representa la ventana para crear o editar una cita
public class NuevaCita2 extends JFrame {

    // Botones de acción
	private JButton btnGuardar;
	private JButton btnCancelar;

    // Campos de texto para datos de la cita
    private JTextArea txtDetalles;

    // Componentes de selección de datos
    private JComboBox cbAprendiz1;
    private JComboBox cbAprendiz2;
    
    // Constructor de la ventana
    public NuevaCita2() {
        
        // Configuración básica de la ventana
        setTitle("Nueva / Editar Cita"); // Título
        setBounds(100, 100, 400, 349); // Tamaño y posición
        
        // Se utiliza layout absoluto
        getContentPane().setLayout(null);

        // Botón para guardar la cita
        btnGuardar = new JButton("Guardar Cita");
        btnGuardar.setBounds(20, 250, 150, 40);
        getContentPane().add(btnGuardar);
        
        // Botón para cancelar la operación
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(210, 250, 150, 40);
        getContentPane().add(btnCancelar);
        
        // Etiqueta para los aprendices responsables
        JLabel lblAprendiz1 = new JLabel("Aprendiz nº1:");
        lblAprendiz1.setBounds(30, 196, 108, 14);
        getContentPane().add(lblAprendiz1);
        
        // textarea que contiene los detalles de la cita la cual le ha inscrito el maestro
        txtDetalles = new JTextArea();
        txtDetalles.setText("Fecha:\nHora:\nCliente:\nTraje:\nTaller:\nDuracion:");
        txtDetalles.setEditable(false);
        txtDetalles.setBounds(20, 69, 340, 112);
        getContentPane().add(txtDetalles);
        
        // Titulo del formulario que contiene la ventana NuevaCita
        JLabel lblDatosDeLaCita = new JLabel("DATOS DE LA CITA");
        lblDatosDeLaCita.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDatosDeLaCita.setBounds(20, 11, 200, 25);
        getContentPane().add(lblDatosDeLaCita);
        
        // etiqueta para las citas asignadas por el maestro
        JLabel lblCitaMaestro = new JLabel("Te han asignado a una nueva cita");
        lblCitaMaestro.setBounds(20, 47, 219, 14);
        getContentPane().add(lblCitaMaestro);
        
        JLabel lblAprendiz2 = new JLabel("Aprendiz nº2:");
        lblAprendiz2.setBounds(30, 225, 108, 14);
        getContentPane().add(lblAprendiz2);
        
        cbAprendiz1 = new JComboBox(new Object[]{"Ninguno"});
        cbAprendiz1.setBounds(180, 191, 180, 25);
        getContentPane().add(cbAprendiz1);
        
        cbAprendiz2 = new JComboBox(new Object[]{"Ninguno"});
        cbAprendiz2.setBounds(180, 221, 180, 25);
        getContentPane().add(cbAprendiz2);
    
    }

    // Getters para que el controlador acceda a los componentes

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JTextArea getTxtDetalles() {
        return txtDetalles;
    }

    public JComboBox getCbAprendiz1() {
        return cbAprendiz1;
    }

    public JComboBox getCbAprendiz2() {
        return cbAprendiz2;
    }
}