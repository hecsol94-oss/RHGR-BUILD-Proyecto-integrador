package vista;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class NuevoTraje extends JFrame {
	
	// Panel principal donde se añaden todos los componentes
	private JPanel contentPane;
	
	// Campo de texto para el nombre del traje
	private JTextField txtNombre;
		
	// Constructor de la clase (se ejecuta al crear la ventana)
	public NuevoTraje(){
		
		// ---------------- CONFIGURACIÓN DE LA VENTANA ----------------
		
	    setTitle("Nuevo / Editar Traje"); // Título de la ventana
	    setBounds(100, 100, 400, 350);   // Posición (x,y) y tamaño (ancho, alto)
				
	    // ---------------- PANEL PRINCIPAL ----------------
	    
	    // Se crea el panel principal
	    contentPane = new JPanel();
	    
	    // Se usa layout nulo (posicionamiento manual con coordenadas)
	    contentPane.setLayout(null);
	    
	    // Se establece el panel como contenido de la ventana
	    setContentPane(contentPane);

	    // ---------------- TÍTULO ----------------
	    
	    // Etiqueta principal del formulario
	    JLabel lblTitle = new JLabel("DATOS DEL TRAJE");
	    
	    // Se establece la fuente (Tahoma, negrita, tamaño 14)
	    lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
	    
	    // Posición y tamaño del label
	    lblTitle.setBounds(20, 11, 200, 25);
	    
	    // Se añade al panel
	    contentPane.add(lblTitle);

	    // ---------------- CAMPO NOMBRE ----------------
				
	    // Etiqueta "Nombre"
	    JLabel lblNombre = new JLabel("Nombre:");
	    lblNombre.setBounds(20, 50, 100, 14);
	    contentPane.add(lblNombre);

	    // Campo de texto donde el usuario escribe el nombre
	    txtNombre = new JTextField();
	    txtNombre.setBounds(20, 70, 340, 25);
	    contentPane.add(txtNombre);
	    
	    // ---------------- CAMPO ESTADO ----------------
	    
	    // Etiqueta "Estado"
	    JLabel lblEstado = new JLabel("Estado:");
	    lblEstado.setBounds(20, 118, 100, 14);
	    contentPane.add(lblEstado);
	    
	    // ComboBox (lista desplegable) con opciones de estado
        JComboBox cbEstado = new JComboBox(
        	new String[] {"Diseño", "Costura", "Taller"}
        );
        
        // Posición y tamaño
        cbEstado.setBounds(20, 143, 340, 25);
        
        // Se añade al contenedor principal
        getContentPane().add(cbEstado);
        
        // ---------------- BOTÓN GUARDAR ----------------
        
        JButton btnGuardar = new JButton("Guardar");
        
        // Fuente del botón
        btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        // Posición y tamaño
        btnGuardar.setBounds(73, 248, 100, 30);
        
        contentPane.add(btnGuardar);
        
        // ---------------- BOTÓN CANCELAR ----------------
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCancelar.setBounds(213, 248, 100, 30);
        contentPane.add(btnCancelar);
        
	}
}