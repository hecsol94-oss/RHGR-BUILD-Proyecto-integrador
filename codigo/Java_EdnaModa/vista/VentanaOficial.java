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
 * Ventana principal del perfil OFICIAL.
 * Menús visibles:
 *   Citas    → Lista de citas | Nueva cita (formulario oficial)
 *   Clientes → Lista de clientes  (solo lectura, sin crear/editar/borrar)
 *   Talleres → Lista de talleres  (solo lectura, sin crear/editar)
 */
public class VentanaOficial extends JFrame {

    private JPanel contentPane;

    // Items de menú del Oficial
    private JMenuItem menuItemListaCitas;
    private JMenuItem menuItemNuevaCita;
    private JMenuItem menuItemListaClientes;
    private JMenuItem menuItemListaTalleres;

    // Etiquetas del dashboard
    private JLabel lblUsuario;
    private JLabel lblSalir;
    private JLabel lblTodasLasCitas;
    private JLabel lblNumeroDeMisCitas;
    private JLabel lblNumeroDeTalleres;

<<<<<<< HEAD:codigo/Java_EdnaModa/vista/VentanaOficial.java
	// Constructor de la ventana
	public VentanaOficial() {
		
		// Configuración básica de la ventana
		setTitle("RGHR EDNA MODA - Sistema de Gestión"); // Título
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar aplicación
		setBounds(100, 100, 700, 500); // Posición y tamaño
		
		// --- BARRA DE MENÚ SUPERIOR ---
		
		// Creación de la barra de menú
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// Menú "Citas"
		JMenu citas = new JMenu("Citas");
		menuBar.add(citas);
		
		// Opción: ver lista de citas
		menuItemListaCitas = new JMenuItem("Lista de citas");
		citas.add(menuItemListaCitas);
		
		// Opción: crear nueva cita
		menuItemNuevaCita = new JMenuItem("Nueva cita");
		citas.add(menuItemNuevaCita);
		
		// Menú "Clientes"
		JMenu clientes = new JMenu("Clientes");
		menuBar.add(clientes);
		
		menuItemListaClientes = new JMenuItem("Lista de clientes");
		clientes.add(menuItemListaClientes);
		
		JMenuItem menuItemNuevoCliente = new JMenuItem("Nuevo cliente");
		clientes.add(menuItemNuevoCliente);
		menuItemNuevoCliente.setEnabled(false);
		
		// Menú "Talleres"
		JMenu talleres = new JMenu("Talleres");
		menuBar.add(talleres);
		
		menuItemListaTalleres = new JMenuItem("Lista de talleres");
		talleres.add(menuItemListaTalleres);
		
		JMenuItem menuItemNuevoTaller = new JMenuItem("Nuevo taller");
		talleres.add(menuItemNuevoTaller);
		menuItemNuevoTaller.setEnabled(false);

=======
    public VentanaOficial() {
>>>>>>> 1e4823f4ac8ca2c340b15af8eba49786a346af1a:codigo/Java_EdnaModa/src/vista/VentanaOficial.java

        setTitle("RGHR EDNA MODA - Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);

        // --- BARRA DE MENÚ ---
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menú "Citas"
        JMenu menuCitas = new JMenu("Citas");
        menuBar.add(menuCitas);

        menuItemListaCitas = new JMenuItem("Lista de citas");
        menuCitas.add(menuItemListaCitas);

        menuItemNuevaCita = new JMenuItem("Nueva cita");
        menuCitas.add(menuItemNuevaCita);

        // Menú "Clientes" — solo lista, sin "Nuevo cliente"
        JMenu menuClientes = new JMenu("Clientes");
        menuBar.add(menuClientes);

        menuItemListaClientes = new JMenuItem("Lista de clientes");
        menuClientes.add(menuItemListaClientes);

        // Menú "Talleres" — solo lista, sin "Nuevo taller"
        JMenu menuTalleres = new JMenu("Talleres");
        menuBar.add(menuTalleres);

        menuItemListaTalleres = new JMenuItem("Lista de talleres");
        menuTalleres.add(menuItemListaTalleres);

        // --- PANEL PRINCIPAL ---
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblUsuario = new JLabel("");
        lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUsuario.setBounds(400, 11, 220, 20);
        contentPane.add(lblUsuario);

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
        imagen.setIcon(new ImageIcon(VentanaOficial.class.getResource("/img/LOGO RHGR_BUILD.png")));
        imagen.setBounds(188, 0, 316, 302);
        contentPane.add(imagen);
    }

    // Getters
    public JMenuItem getMenuItemListaCitas()    { 
    	return menuItemListaCitas; 
    	}
    
    public JMenuItem getMenuItemNuevaCita()     {
    	return menuItemNuevaCita; 
    	}
    
    public JMenuItem getMenuItemListaClientes() {
    	return menuItemListaClientes; 
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
