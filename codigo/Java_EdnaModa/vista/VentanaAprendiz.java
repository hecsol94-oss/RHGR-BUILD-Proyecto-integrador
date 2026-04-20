package vista;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

/**
 * Ventana principal del perfil APRENDIZ.
 * Menús visibles: Citas (solo lista) | Talleres (solo lista)
 * Sin acceso a Clientes ni a formularios de creación/edición.
 */
public class VentanaAprendiz extends JFrame {

    private JPanel contentPane;

    // Únicos items de menú permitidos para el Aprendiz
    private JMenuItem menuItemListaCitas;
    private JMenuItem menuItemListaTalleres;

    // Etiquetas del dashboard
    private JLabel lblUsuario;
    private JLabel lblSalir;
    private JLabel lblTodasLasCitas;
    private JLabel lblNumeroDeMisCitas;
    private JLabel lblNumeroDeTalleres;

<<<<<<< HEAD:codigo/Java_EdnaModa/vista/VentanaAprendiz.java
	// Constructor de la ventana
	public VentanaAprendiz() {
		
		// Configuración básica de la ventana
		setTitle("RGHR EDNA MODA - Sistema de Gestión"); // Título
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar aplicación
		setBounds(100, 100, 700, 500); // Posición y tamaño
		
		// --- BARRA DE MENÚ SUPERIOR ---
		
		// Creación de la barra de menú
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// Menú "Citas"
		JMenu Citas = new JMenu("Citas");
		menuBar.add(Citas);
		
		// Opción: ver lista de citas
		menuItemListaCitas = new JMenuItem("Lista de citas");
		Citas.add(menuItemListaCitas);
		
		// Opción: crear nueva cita
		JMenuItem nuevaCita = new JMenuItem("Nueva cita");
		Citas.add(nuevaCita);
		nuevaCita.setEnabled(false);
		
		// Menú "Clientes"
		JMenu Clientes = new JMenu("Clientes");
		menuBar.add(Clientes);
		Clientes.setEnabled(false);

		JMenuItem listaClientes = new JMenuItem("Lista de clientes");
		Clientes.add(listaClientes);
		
		JMenuItem nuevoCliente = new JMenuItem("Nuevo cliente");
		Clientes.add(nuevoCliente);
		
		// Menú "Talleres"
		JMenu Talleres = new JMenu("Talleres");
		menuBar.add(Talleres);
		
		menuItemListaTalleres = new JMenuItem("Lista de talleres");
		Talleres.add(menuItemListaTalleres);
		
		JMenuItem nuevoTaller = new JMenuItem("Nuevo taller");
		Talleres.add(nuevoTaller);
		nuevoTaller.setEnabled(false);
=======
    public VentanaAprendiz() {
>>>>>>> 1e4823f4ac8ca2c340b15af8eba49786a346af1a:codigo/Java_EdnaModa/src/vista/VentanaAprendiz.java

        setTitle("RGHR EDNA MODA - Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);

        // --- BARRA DE MENÚ ---
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menú "Citas" — solo lista, sin nueva cita
        JMenu menuCitas = new JMenu("Citas");
        menuBar.add(menuCitas);

        menuItemListaCitas = new JMenuItem("Lista de citas");
        menuCitas.add(menuItemListaCitas);
        // El Aprendiz no tiene acceso a Clientes ni puede crear citas.

        // Menú "Talleres" — solo lista, sin nuevo taller
        JMenu menuTalleres = new JMenu("Talleres");
        menuBar.add(menuTalleres);

        menuItemListaTalleres = new JMenuItem("Lista de talleres");
        menuTalleres.add(menuItemListaTalleres);

        // --- PANEL PRINCIPAL ---
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Etiqueta usuario
        lblUsuario = new JLabel("");
        lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUsuario.setBounds(400, 11, 220, 20);
        contentPane.add(lblUsuario);

        // Etiqueta salir (logout)
        lblSalir = new JLabel("");
        lblSalir.setForeground(Color.BLUE);
        lblSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblSalir.setBounds(625, 11, 40, 20);
        contentPane.add(lblSalir);

        // --- DASHBOARD ---

        JLabel lblTodasCitas = new JLabel("TODAS LAS CITAS:");
        lblTodasCitas.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblTodasCitas.setBounds(45, 310, 150, 20);
        contentPane.add(lblTodasCitas);

        JPanel boxCitas = new JPanel();
        boxCitas.setBackground(new Color(192, 192, 192));
        boxCitas.setBorder(new LineBorder(new Color(0, 0, 0)));
        boxCitas.setBounds(45, 335, 170, 80);
        contentPane.add(boxCitas);

        lblTodasLasCitas = new JLabel("");
        boxCitas.add(lblTodasLasCitas);

        JLabel lblMisCitas = new JLabel("MIS CITAS:");
        lblMisCitas.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblMisCitas.setBounds(260, 310, 150, 20);
        contentPane.add(lblMisCitas);

        JPanel boxMisCitas = new JPanel();
        boxMisCitas.setBackground(new Color(192, 192, 192));
        boxMisCitas.setBorder(new LineBorder(new Color(0, 0, 0)));
        boxMisCitas.setBounds(260, 335, 170, 80);
        contentPane.add(boxMisCitas);

        lblNumeroDeMisCitas = new JLabel("");
        boxMisCitas.add(lblNumeroDeMisCitas);

        JLabel lblTalleresTab = new JLabel("TALLERES:");
        lblTalleresTab.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblTalleresTab.setBounds(475, 310, 150, 20);
        contentPane.add(lblTalleresTab);

        JPanel boxTalleres = new JPanel();
        boxTalleres.setBackground(new Color(192, 192, 192));
        boxTalleres.setBorder(new LineBorder(new Color(0, 0, 0)));
        boxTalleres.setBounds(475, 335, 170, 80);
        contentPane.add(boxTalleres);

        lblNumeroDeTalleres = new JLabel("");
        boxTalleres.add(lblNumeroDeTalleres);

        // Logo
        JLabel imagen = new JLabel("");
        imagen.setIcon(new ImageIcon(VentanaAprendiz.class.getResource("/img/LOGO RHGR_BUILD.png")));
        imagen.setBounds(188, 0, 316, 302);
        contentPane.add(imagen);
    }

    // Getters
    public JMenuItem getMenuItemListaCitas()    {
    	return menuItemListaCitas; 
    	}
    
    public JMenuItem getMenuItemListaTalleres() {
    	return menuItemListaTalleres; 
    	}
    
    public JLabel getLblUsuario()               {
    	return lblUsuario; 
    	}
    
    public JLabel getLblSalir()                 {
    	return lblSalir;
    	}
    
    public JLabel getLblTodasLasCitas()         {
    	return lblTodasLasCitas; 
    	}
    
    public JLabel getLblNumeroDeMisCitas()      {
    	return lblNumeroDeMisCitas; 
    	}
    
    public JLabel getLblNumeroDeTalleres()      {
    	return lblNumeroDeTalleres; 
    	}
}
