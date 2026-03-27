package vista;

// Importaciones necesarias para la interfaz gráfica
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

// Clase que representa la ventana para crear o editar una cita
public class NuevaCita extends JFrame {
    
    // Campos de texto para introducir datos de la cita
    private JTextField txtFecha, txtHora, txtDuracion;

    // Constructor de la ventana
    public NuevaCita() {
        
        // Configuración básica de la ventana
        setTitle("Nueva / Editar Cita"); // Título
        setBounds(100, 100, 450, 350); // Tamaño y posición
        
        // Se utiliza layout absoluto
        getContentPane().setLayout(null);

        // ComboBox para seleccionar el cliente
        JComboBox cbCliente = new JComboBox(new String[] {"Seleccionar...", "Elastic Girl", "Frozone"});
        cbCliente.setBounds(20, 40, 180, 25);
        getContentPane().add(cbCliente);

        // ComboBox para seleccionar el traje
        JComboBox cbTraje = new JComboBox(new String[] {"Prototipo A", "Súper Gala"});
        cbTraje.setBounds(230, 40, 180, 25);
        getContentPane().add(cbTraje);

        // ComboBox para seleccionar el taller
        JComboBox cbTaller = new JComboBox(new String[] {"París", "Madrid"});
        cbTaller.setBounds(20, 100, 180, 25);
        getContentPane().add(cbTaller);

        // Campo de texto para introducir el oficial responsable
        JTextField txtOficial = new JTextField();
        txtOficial.setBounds(230, 100, 180, 25);
        getContentPane().add(txtOficial);

        // Campo de texto para introducir la fecha
        txtFecha = new JTextField();
        txtFecha.setBounds(20, 160, 100, 25);
        getContentPane().add(txtFecha);

        // Botón para guardar la cita
        JButton btnGuardar = new JButton("Guardar Cita");
        btnGuardar.setBounds(20, 250, 150, 40);
        getContentPane().add(btnGuardar);
        
        // Botón para cancelar la operación
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 250, 150, 40);
        getContentPane().add(btnCancelar);
        
        // Etiqueta para el campo cliente
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(20, 15, 100, 14);
        getContentPane().add(lblCliente);
        
        // Etiqueta para el campo traje
        JLabel lblTraje = new JLabel("Traje:");
        lblTraje.setBounds(230, 15, 100, 14);
        getContentPane().add(lblTraje);
        
        // Etiqueta para el campo taller
        JLabel lblTaller = new JLabel("Taller:");
        lblTaller.setBounds(20, 75, 100, 14);
        getContentPane().add(lblTaller);
        
        // Etiqueta para el oficial responsable
        JLabel lblOficialResponsable = new JLabel("Oficial responsable:");
        lblOficialResponsable.setBounds(230, 75, 155, 14);
        getContentPane().add(lblOficialResponsable);
        
        // Etiqueta para la fecha
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(20, 135, 100, 14);
        getContentPane().add(lblFecha);
    }
}