package vista;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class DetalleEmpleados extends JFrame {

	private final Color ROJO_HEROE = new Color(204, 0, 0);
    private final Color AMARILLO_POWER = new Color(255, 204, 0);
    private final Color NEGRO_ELITE = new Color(25, 25, 25);
    private final Color GRIS_TECNICO = new Color(240, 240, 240);
    
    private JPanel contentPane;
    private JTextField txtNombreEmpleado, txtApellidoEmpleado, txtApodoEmpleado, txtUsuarioEmpleado;
    private JPasswordField Contraseña, ConfirmarContraseña;
    private JList<String> listCategoria;
    private DefaultListModel<String> modeloLista;
    private JButton btnEditar, btnEliminar, btnVolver, btnNuevoUsuario;
    
    public DetalleEmpleados() {
    	setTitle("DOSSIER DE EMPLEADO - CONFIDENCIAL");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setMinimumSize(new Dimension(1000, 750));
        
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(NEGRO_ELITE);
        contentPane.setBorder(new EmptyBorder(40, 60, 40, 60)); 
        setContentPane(contentPane);
        
        JPanel expediente = new JPanel(new BorderLayout(0, 30));
        expediente.setBackground(GRIS_TECNICO);
        expediente.setBorder(new CompoundBorder(
            new LineBorder(ROJO_HEROE, 4),
            new EmptyBorder(30, 40, 30, 40)
        ));
        contentPane.add(expediente, BorderLayout.CENTER);
        
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setOpaque(false);
        
        JLabel lblTitulo = new JLabel("EXPEDIENTE TÉCNICO DE EMPLEADO");
        lblTitulo.setFont(new Font("Impact", Font.PLAIN, 42));
        lblTitulo.setForeground(NEGRO_ELITE);
        panelHeader.add(lblTitulo, BorderLayout.WEST);
        
        btnNuevoUsuario = new JButton("+ NUEVO USUARIO");
        estilizarBoton(btnNuevoUsuario, 220, 50);
        btnNuevoUsuario.setBackground(ROJO_HEROE);
        btnNuevoUsuario.setForeground(Color.WHITE);
        panelHeader.add(btnNuevoUsuario, BorderLayout.EAST);
        
        expediente.add(panelHeader, BorderLayout.NORTH);
        
        JPanel panelCuerpo = new JPanel(new GridBagLayout());
        panelCuerpo.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        
        JPanel panelInfo = new JPanel(new GridLayout(2, 2, 30, 20));
        panelInfo.setBackground(GRIS_TECNICO);
        panelInfo.setBorder(new TitledBorder(new LineBorder(NEGRO_ELITE, 2), "DATOS DE IDENTIFICACIÓN", 
                           TitledBorder.LEADING, TitledBorder.TOP, new Font("Impact", Font.PLAIN, 18), NEGRO_ELITE));
        
        txtNombreEmpleado = crearCampoTexto(panelInfo, "NOMBRE:");
        txtApellidoEmpleado = crearCampoTexto(panelInfo, "APELLIDO:");
        txtApodoEmpleado = crearCampoTexto(panelInfo, "APODO:");
        txtUsuarioEmpleado = crearCampoTexto(panelInfo, "USUARIO:");
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0; gbc.weighty = 0.3;
        panelCuerpo.add(panelInfo, gbc);
        
        JPanel panelListaContenedor = new JPanel(new BorderLayout(0, 10));
        panelListaContenedor.setOpaque(false);
    }
    
    /**
     * Crea un campo de texto con etiqueta asociada y lo añade directamente al panel.
     * El campo se configura como no editable y con un estilo visual consistente con la interfaz.
     *
     * @param panel panel donde se insertará el campo completo (label + textbox)
     * @param label texto descriptivo que acompaña al campo
     * @return JTextField creado y añadido al contenedor
     */
    private JTextField crearCampoTexto(JPanel panel, String label) {
        JPanel contenedorCampo = new JPanel(new BorderLayout(5, 5));
        contenedorCampo.setOpaque(false);
        contenedorCampo.setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 12));
        contenedorCampo.add(lbl, BorderLayout.NORTH);

        JTextField txt = new JTextField();
        txt.setEditable(false);
        txt.setPreferredSize(new Dimension(0, 35));
        txt.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txt.setBorder(new MatteBorder(0, 0, 2, 0, NEGRO_ELITE)); 
        txt.setBackground(GRIS_TECNICO);
        contenedorCampo.add(txt, BorderLayout.CENTER);
        
        panel.add(contenedorCampo);
        return txt;
    }
    
    /**
     * Aplica un estilo visual estándar a un botón de la interfaz.
     * Define colores, tamaño, fuente, borde y comportamiento del cursor.
     *
     * @param btn botón al que se le aplicará el estilo
     * @param w ancho del botón
     * @param h alto del botón
     */
    private void estilizarBoton(JButton btn, int w, int h) {
        btn.setPreferredSize(new Dimension(w, h));
        btn.setBackground(NEGRO_ELITE);
        btn.setForeground(AMARILLO_POWER);
        btn.setFont(new Font("Impact", Font.PLAIN, 18));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(AMARILLO_POWER, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
