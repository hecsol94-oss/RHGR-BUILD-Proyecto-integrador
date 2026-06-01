package vista;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class NuevoEmpleado extends JFrame {

	private final Color ROJO_HEROE = new Color(204, 0, 0);
    private final Color AMARILLO_POWER = new Color(255, 204, 0);
    private final Color NEGRO_ELITE = new Color(25, 25, 25);
    private final Color GRIS_TECNICO = new Color(240, 240, 240);
    
    private JPanel contentPane;
    private JTextField txtNombre, txtApellido, txtApodo, txtUsuario;
    private JComboBox<String> cbTipo;
    private JPasswordField ContraseñaCampo, ConfirmarContraseña;
    private JButton btnGuardar, btnCancelar;
    
    public NuevoEmpleado() {
    	setTitle("REGISTRO DE EMPLEADO - EDNA MODA");
        setResizable(false); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 550);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBackground(GRIS_TECNICO);
        contentPane.setBorder(new LineBorder(NEGRO_ELITE, 2));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        JPanel header = new JPanel();
        header.setBackground(NEGRO_ELITE);
        header.setBounds(0, 0, 450, 40);
        header.setLayout(null);
        contentPane.add(header);
        
        JLabel lblTitle = new JLabel("DATOS DEL EMPLEADO");
        lblTitle.setForeground(AMARILLO_POWER);
        lblTitle.setFont(new Font("Impact", Font.PLAIN, 18));
        lblTitle.setBounds(20, 5, 200, 30);
        header.add(lblTitle);
        
        JLabel lblApodo = new JLabel("APODO:");
        estilizarLabel(lblApodo, 20, 60);
        
        txtApodo = new JTextField();
        estilizarCampo(txtApodo, 20, 80, 170, 30);
        
        JLabel lblNombre = new JLabel("NOMBRE:");
        estilizarLabel(lblNombre, 20, 120);
        
        txtNombre = new JTextField();
        estilizarCampo(txtNombre, 20, 140, 170, 30);
        
        JLabel lblApellido = new JLabel("APELLIDO:");
        estilizarLabel(lblApellido, 20, 185);
        
        txtApellido = new JTextField();
        estilizarCampo(txtApellido, 20, 205, 170, 30);
        
        JLabel lblUsuario = new JLabel("USUARIO:");
        estilizarLabel(lblUsuario, 20, 245);
        
        txtUsuario = new JTextField();
        estilizarCampo(txtUsuario, 20, 265, 170, 30);
        
        JLabel lblContraseñaCampo = new JLabel("CONTRASEÑA:");
        estilizarLabel(lblContraseñaCampo, 20, 305);
        
        ContraseñaCampo = new JPasswordField();
        estilizarContraseña(ContraseñaCampo, 20, 325, 170, 30);
        
        JLabel lblConfirmarContraseña = new JLabel("CONFIRMAR CONTRASEÑA:");
        estilizarLabel(lblConfirmarContraseña, 20, 365);
        
        ConfirmarContraseña = new JPasswordField();
        estilizarContraseña(ConfirmarContraseña, 20, 385, 170, 30);
        
        JLabel lblTipo = new JLabel("CATEGORÍA:");
        estilizarLabel(lblTipo, 210, 185);
        
        cbTipo = new JComboBox<>(new String[]{"aprendiz", "oficial", "maestro"});
        cbTipo.setBounds(210, 205, 170, 30);
        cbTipo.setBackground(Color.WHITE);
        cbTipo.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPane.add(cbTipo);
        
        btnGuardar = new JButton("CONFIRMAR");
        btnGuardar.setBounds(50, 430, 140, 45);
        estilizarBoton(btnGuardar, ROJO_HEROE, Color.WHITE);
        contentPane.add(btnGuardar);
        
        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBounds(210, 430, 140, 45);
        estilizarBoton(btnCancelar, NEGRO_ELITE, AMARILLO_POWER);
        contentPane.add(btnCancelar);
        
        JLabel lblFooter = new JLabel("EDNA MODA STRATEGIC SYSTEMS v2.6");
        lblFooter.setFont(new Font("Monospaced", Font.PLAIN, 10));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setBounds(100, 480, 200, 20);
        contentPane.add(lblFooter);
    }
    
    /**
     * Aplica estilo visual a una etiqueta (JLabel) dentro del formulario.
     * Configura la fuente, color, posición y añade automáticamente el
     * componente al panel principal.
     *
     * @param lbl etiqueta a estilizar
     * @param x posición horizontal en píxeles dentro del panel
     * @param y posición vertical en píxeles dentro del panel
     */
    private void estilizarLabel(JLabel lbl, int x, int y) {
        lbl.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl.setForeground(NEGRO_ELITE);
        lbl.setBounds(x, y, 200, 15);
        contentPane.add(lbl);
    }
    
    /**
     * Aplica estilo visual a un campo de texto (JTextField).
     * Define tamaño, borde, fuente y lo añade al panel principal para mantener
     * una apariencia uniforme en la interfaz.
     *
     * @param txt campo de texto a estilizar
     * @param x posición horizontal en píxeles
     * @param y posición vertical en píxeles
     * @param w ancho del campo en píxeles
     * @param h alto del campo en píxeles
     */
    private void estilizarCampo(JTextField txt, int x, int y, int w, int h) {
        txt.setBounds(x, y, w, h);
        txt.setBorder(new LineBorder(NEGRO_ELITE, 1));
        txt.setFont(new Font("Monospaced", Font.BOLD, 14));
        contentPane.add(txt);
    }
    
    /**
     * Aplica estilo visual a un botón dentro del formulario.
     * Configura colores de fondo y texto, fuente, borde y cursor interactivo
     * para mejorar la experiencia de usuario.
     *
     * @param btn botón al que se aplicará el estilo
     * @param bg color de fondo del botón
     * @param fg color del texto del botón
     */
    private void estilizarBoton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Impact", Font.PLAIN, 16));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(bg.darker(), 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * Aplica estilo visual a un campo de contraseña (JPasswordField).
     * Define tamaño, borde, fuente y lo añade al panel principal para mantener
     * una apariencia uniforme en la interfaz.
     *
     * @param pword campo de contraseña a estilizar
     * @param x posición horizontal en píxeles
     * @param y posición vertical en píxeles
     * @param w ancho del campo en píxeles
     * @param h alto del campo en píxeles
     */
    private void estilizarContraseña(JPasswordField pword, int x, int y, int w, int h) {
        pword.setBounds(x, y, w, h);
        pword.setBorder(new LineBorder(NEGRO_ELITE, 1));
        pword.setFont(new Font("Monospaced", Font.BOLD, 14));
        contentPane.add(pword);
    }

	/**
	 * @return the txtNombre
	 */
	public JTextField getTxtNombre() {
		return txtNombre;
	}

	/**
	 * @return the txtApellido
	 */
	public JTextField getTxtApellido() {
		return txtApellido;
	}

	/**
	 * @return the txtApodo
	 */
	public JTextField getTxtApodo() {
		return txtApodo;
	}

	/**
	 * @return the txtUsuario
	 */
	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	/**
	 * 
	 * @return
	 */
	public JComboBox<String> getCbTipo() {
	    return cbTipo;
	}
	
	/**
	 * 
	 * @param tipo
	 */
	public void setCbTipo(String categoria) {
		cbTipo.setSelectedItem(categoria);
    }

	/**
	 * @return the contraseñaCampo
	 */
	public JPasswordField getContraseñaCampo() {
		return ContraseñaCampo;
	}

	/**
	 * @return the confirmarContraseña
	 */
	public JPasswordField getConfirmarContraseña() {
		return ConfirmarContraseña;
	}

	/**
	 * @return the btnGuardar
	 */
	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	/**
	 * @return the btnCancelar
	 */
	public JButton getBtnCancelar() {
		return btnCancelar;
	}
    
}
