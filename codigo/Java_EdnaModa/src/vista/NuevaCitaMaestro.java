package vista;

// Importaciones necesarias para la interfaz gráfica
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

// Clase que representa la ventana para crear o editar una cita
public class NuevaCitaMaestro extends JFrame {
    
    // Campos de texto para introducir datos de la cita
    private JTextField txtFecha, txtHora, txtDuracion;
    private JTextField textField;
    private JTextField textField_1;

    // Constructor de la ventana
    public NuevaCitaMaestro() {
        
        // Configuración básica de la ventana
        setTitle("Nueva / Editar Cita"); // Título
        setBounds(100, 100, 450, 450); // Tamaño y posición
        
        // Se utiliza layout absoluto
        getContentPane().setLayout(null);

        // ComboBox para seleccionar el cliente
        JComboBox cbCliente = new JComboBox(new String[] {});
        cbCliente.setBounds(20, 66, 180, 25);
        getContentPane().add(cbCliente);

        // ComboBox para seleccionar el traje
        JComboBox cbTraje = new JComboBox(new String[] {});
        cbTraje.setBounds(230, 66, 180, 25);
        getContentPane().add(cbTraje);

        // ComboBox para seleccionar el taller
        JComboBox cbTaller = new JComboBox(new String[] {"París", "Madrid", "Milan", "New York", "Berlin"});
        cbTaller.setBounds(20, 126, 180, 25);
        getContentPane().add(cbTaller);

        // Campo de texto para introducir el oficial responsable
        JTextField txtOficial = new JTextField();
        txtOficial.setBounds(230, 126, 180, 25);
        getContentPane().add(txtOficial);

        // Campo de texto para introducir la fecha
        txtFecha = new JTextField();
        txtFecha.setBounds(20, 186, 100, 25);
        getContentPane().add(txtFecha);

        // Botón para guardar la cita
        JButton btnGuardar = new JButton("Guardar Cita");
        btnGuardar.setBounds(20, 344, 150, 40);
        getContentPane().add(btnGuardar);
        
        // Botón para cancelar la operación
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 344, 150, 40);
        getContentPane().add(btnCancelar);
        
        // Etiqueta para el campo cliente
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(20, 41, 100, 14);
        getContentPane().add(lblCliente);
        
        // Etiqueta para el campo traje
        JLabel lblTraje = new JLabel("Traje:");
        lblTraje.setBounds(230, 41, 100, 14);
        getContentPane().add(lblTraje);
        
        // Etiqueta para el campo taller
        JLabel lblTaller = new JLabel("Taller:");
        lblTaller.setBounds(20, 101, 100, 14);
        getContentPane().add(lblTaller);
        
        // Etiqueta para el oficial responsable
        JLabel lblOficialResponsable = new JLabel("Oficial responsable:");
        lblOficialResponsable.setBounds(230, 101, 155, 14);
        getContentPane().add(lblOficialResponsable);
        
        // Etiqueta para la fecha
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(20, 161, 100, 14);
        getContentPane().add(lblFecha);
        
        //boton para crear un nuevo cliente en caso de que este no exista en la lista de clientes
        JButton btnCliente = new JButton("¿Existe el cliente? Registralo");
        btnCliente.setBounds(22, 247, 388, 25);
        getContentPane().add(btnCliente);
        
        //boton para crear un nuevo taller en caso de que este no exista en la lista de talleres
        JButton btnTaller = new JButton("¿Existe el taller? añadelo");
        btnTaller.setBounds(24, 283, 386, 25);
        getContentPane().add(btnTaller);
        
        JLabel lblDatosDeLa = new JLabel("DATOS DE LA CITA");
        lblDatosDeLa.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDatosDeLa.setBounds(20, 11, 200, 25);
        getContentPane().add(lblDatosDeLa);
        
        textField = new JTextField();
        textField.setBounds(167, 187, 100, 25);
        getContentPane().add(textField);
        
        JLabel lblHora = new JLabel("Hora:");
        lblHora.setBounds(167, 162, 100, 14);
        getContentPane().add(lblHora);
        
        textField_1 = new JTextField();
        textField_1.setBounds(310, 187, 100, 25);
        getContentPane().add(textField_1);
        
        JLabel lblDuracion = new JLabel("Duracion:");
        lblDuracion.setBounds(310, 162, 100, 14);
        getContentPane().add(lblDuracion);
    }
}