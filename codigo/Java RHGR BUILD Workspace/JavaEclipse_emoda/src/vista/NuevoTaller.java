package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import modelo.Taller;

public class NuevoTaller extends JFrame {

    private final Color ROJO_HEROE = new Color(204, 0, 0);
    private final Color AMARILLO_POWER = new Color(255, 204, 0);
    private final Color NEGRO_ELITE = new Color(25, 25, 25);
    private final Color GRIS_TECNICO = new Color(240, 240, 240);

    private JPanel contentPane;
    private JTextField txtNomeSala;
    private JComboBox<String> cbTipoSala;
    private JButton btnGuardar, btnCancelar;

    public NuevoTaller() {
        setTitle("CONFIGURACIÓN DE INFRAESTRUCTURA");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setSize(400, 380);
        setLocationRelativeTo(null); 
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBackground(GRIS_TECNICO);
        contentPane.setBorder(new LineBorder(NEGRO_ELITE, 2));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JPanel header = new JPanel();
        header.setBackground(NEGRO_ELITE);
        header.setBounds(0, 0, 400, 40);
        header.setLayout(null);
        contentPane.add(header);

        JLabel lblTitle = new JLabel("REGISTRO DE SALA TÉCNICA");
        lblTitle.setForeground(AMARILLO_POWER);
        lblTitle.setFont(new Font("Impact", Font.PLAIN, 18));
        lblTitle.setBounds(20, 5, 250, 30);
        header.add(lblTitle);

        JLabel lblNombreSala = new JLabel("IDENTIFICADOR DE LA SALA:");
        estilizarLabel(lblNombreSala, 25, 60);

        txtNomeSala = new JTextField();
        estilizarCampo(txtNomeSala, 25, 80, 340, 30);

        JLabel lblTipoSala = new JLabel("ESPECIFICACIÓN DE USO:");
        estilizarLabel(lblTipoSala, 25, 125);

        cbTipoSala = new JComboBox<>(new String[] {"diseño", "costura", "pruebas"});
        cbTipoSala.setBounds(25, 145, 340, 30);
        cbTipoSala.setBackground(Color.WHITE);
        cbTipoSala.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPane.add(cbTipoSala);

        btnGuardar = new JButton("AUTORIZAR");
        btnGuardar.setBounds(45, 220, 135, 45);
        estilizarBoton(btnGuardar, ROJO_HEROE, Color.WHITE);
        contentPane.add(btnGuardar);

        btnCancelar = new JButton("ABORTAR");
        btnCancelar.setBounds(210, 220, 135, 45);
        estilizarBoton(btnCancelar, NEGRO_ELITE, AMARILLO_POWER);
        contentPane.add(btnCancelar);

        JLabel lblFooter = new JLabel("EDNA MODA & ASSOC. FACILITIES");
        lblFooter.setFont(new Font("Monospaced", Font.PLAIN, 10));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setBounds(100, 310, 200, 20);
        contentPane.add(lblFooter);
    }

    /**
     * --- Métodos de Ayuda para Diseño ---
     */

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
     * --- MÉTODOS PARA EL CONTROLADOR ---
     */

 /** Método para obtener el texto del campo nombre */
    
 	/**
 	 * 
 	 * @return
 	 */
 	public String getNombreSala() {
 		return txtNomeSala.getText();
 	}
 	
 	/** Método para obtener el valor seleccionado del combo */
 	
 	/**
 	 * 
 	 * @return
 	 */
 	public String getTipoSala() {
 		return (String) cbTipoSala.getSelectedItem();
 	}
 	
 	/** Getter del botón guardar para el controlador */
 	
 	/**
 	 * 
 	 * @return
 	 */
 	public JButton getBtnGuardar() {
 		return btnGuardar;
 	}
 	
 	/** Getter del botón cancelar para el controlador */
 	
 	/**
 	 * 
 	 * @return
 	 */
 	public JButton getBtnCancelar() {
 		return btnCancelar;
 	}
 	
 	/** Método para rellenar los campos al editar un taller existente */
 	
 	/**
 	 * 
 	 * @param taller
 	 */
 	public void cargarDatos(Taller taller) {
 	    txtNomeSala.setText(taller.getNombre());
 	    cbTipoSala.setSelectedItem(taller.getTipo());
 	}
}