package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class NuevoTraje extends JFrame {

    private final Color ROJO_HEROE = new Color(204, 0, 0);
    private final Color AMARILLO_POWER = new Color(255, 204, 0);
    private final Color NEGRO_ELITE = new Color(25, 25, 25);
    private final Color GRIS_TECNICO = new Color(240, 240, 240);
	
    private JComboBox<Object> cbEstado;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JTextField txtNombre;
	
    public NuevoTraje() {
        setTitle("ESPECIFICACIONES DEL TRAJE");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setSize(400, 350);
        setLocationRelativeTo(null); 
        setResizable(false);
        
        JPanel contentPane = new JPanel();
        contentPane.setBackground(GRIS_TECNICO);
        contentPane.setBorder(new LineBorder(NEGRO_ELITE, 2));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JPanel header = new JPanel();
        header.setBackground(NEGRO_ELITE);
        header.setBounds(0, 0, 400, 40);
        header.setLayout(null);
        contentPane.add(header);
		
        JLabel lblTitle = new JLabel("ORDEN DE PRODUCCIÓN");
        lblTitle.setFont(new Font("Impact", Font.PLAIN, 18));
        lblTitle.setForeground(AMARILLO_POWER);
        lblTitle.setBounds(20, 5, 250, 30);
        header.add(lblTitle);
		
        JLabel lblNombre = new JLabel("IDENTIFICADOR DEL DISEÑO:");
        lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNombre.setBounds(25, 60, 200, 14);
        contentPane.add(lblNombre);
		
        txtNombre = new JTextField();
        txtNombre.setBounds(25, 80, 340, 30);
        txtNombre.setFont(new Font("Monospaced", Font.BOLD, 14));
        txtNombre.setBorder(new LineBorder(NEGRO_ELITE));
        contentPane.add(txtNombre);
		
        JLabel lblEstado = new JLabel("FASE ACTUAL:");
        lblEstado.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblEstado.setBounds(25, 125, 100, 14);
        contentPane.add(lblEstado);
		
        cbEstado = new JComboBox<Object>(new Object[]{"diseño", "costura", "pruebas"});
        cbEstado.setBounds(25, 145, 340, 30);
        cbEstado.setBackground(Color.WHITE);
        cbEstado.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPane.add(cbEstado);
		
        btnGuardar = new JButton("GUARDAR");
        btnGuardar.setBounds(45, 210, 135, 45);
        estilizarBoton(btnGuardar, ROJO_HEROE, Color.WHITE);
        contentPane.add(btnGuardar);
		
        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBounds(210, 210, 135, 45);
        estilizarBoton(btnCancelar, NEGRO_ELITE, AMARILLO_POWER);
        contentPane.add(btnCancelar);
        
        JLabel lblFooter = new JLabel("EDNA MODA-MFT UNIT v1.0");
        lblFooter.setFont(new Font("Monospaced", Font.PLAIN, 10));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setBounds(130, 280, 200, 20);
        contentPane.add(lblFooter);
    }

    /**
     * Aplica un estilo visual personalizado a un botón dentro de la interfaz.
     * Este método configura tamaño, colores, fuente y bordes para mantener
     * la coherencia estética de la aplicación (tema "Elite").
     *
     * @param btn botón al que se le aplicará el estilo
     * @param w ancho deseado del botón en píxeles
     * @param h alto deseado del botón en píxeles
     */
    private void estilizarBoton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Impact", Font.PLAIN, 16));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(bg.darker(), 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
	
    // --- MÉTODOS GETTER PARA EL CONTROLADOR (MANTENIDOS) ---

 // --- MÉTODOS GETTER PARA EL CONTROLADOR ---

 	/**
 	 * 
 	 * @return
 	 */
     public JTextField getNombreTraje() {
         return txtNombre;
     }

    /**
     * 
     * @return
     */
     public String getCbEstado() {
         return (String) cbEstado.getSelectedItem();
     }
     
     /**
      * 
      * @param estado
      */
     public void setCbEstado(String estado) {
         cbEstado.setSelectedItem(estado);
     }

     /**
      * 
      * @return
      */
     public JButton getBtnGuardar() {
         return btnGuardar;
     }

     /**
      * 
      * @return
      */
     public JButton getBtnCancelar() {
         return btnCancelar;
     }
}