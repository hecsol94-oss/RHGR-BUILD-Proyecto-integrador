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
public class NuevaCitaOficial extends JFrame {
    
    // Constructor de la ventana
    public NuevaCitaOficial() {
        
        // Configuración básica de la ventana
        setTitle("Nueva / Editar Cita"); // Título
        setBounds(100, 100, 400, 349); // Tamaño y posición
        
        // Se utiliza layout absoluto
        getContentPane().setLayout(null);

        // Campo de texto para introducir el oficial responsable
        JTextField txtOficial = new JTextField();
        txtOficial.setBounds(20, 204, 340, 25);
        getContentPane().add(txtOficial);     

        // Botón para guardar la cita
        JButton btnGuardar = new JButton("Guardar Cita");
        btnGuardar.setBounds(20, 250, 150, 40);
        getContentPane().add(btnGuardar);
        
        // Botón para cancelar la operación
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(210, 250, 150, 40);
        getContentPane().add(btnCancelar);
        
        // Etiqueta para los aprendices responsables
        JLabel lblAprendicesResponsables = new JLabel("Aprendices:");
        lblAprendicesResponsables.setBounds(20, 179, 182, 14);
        getContentPane().add(lblAprendicesResponsables);
        
        // textarea que contiene los detalles de la cita la cual le ha inscrito el maestro
        JTextArea txtDetalles = new JTextArea();
        txtDetalles.setText("Fecha: 24/03/2026\nHora: 10:00\nCliente: Mr. Increíble\nTraje: Classic Blue\nTaller: París");
        txtDetalles.setEditable(false);
        txtDetalles.setBounds(20, 69, 340, 99);
        getContentPane().add(txtDetalles);
        
        // Titulo del formulario que contiene la ventana NuevaCita
        JLabel lblDatosDeLaCita = new JLabel("DATOS DE LA CITA");
        lblDatosDeLaCita.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDatosDeLaCita.setBounds(20, 11, 200, 25);
        getContentPane().add(lblDatosDeLaCita);
        
        // etiqueta para las citas asignadas por el maestro
        JLabel lblCitaMaestro = new JLabel("Citas asignadas por el maestro");
        lblCitaMaestro.setBounds(20, 47, 219, 14);
        getContentPane().add(lblCitaMaestro);
    
    }
}