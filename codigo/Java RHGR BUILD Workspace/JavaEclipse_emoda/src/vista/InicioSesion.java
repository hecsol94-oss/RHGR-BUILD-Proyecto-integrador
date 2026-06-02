package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class InicioSesion extends JFrame {

    private final Color ROJO_HEROE = new Color(204, 0, 0);
    private final Color AMARILLO_POWER = new Color(255, 204, 0);
    private final Color NEGRO_ELITE = new Color(25, 25, 25);
    private final Color GRIS_TECNICO = new Color(240, 240, 240);

    private JTextField NomUsuario;
    private JPasswordField ContraseñaCampo;
    private JButton entrar;

    public InicioSesion() {

        setTitle("RHGR STRATEGIC ACCESS - EDNA MODA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 700);
        setLocationRelativeTo(null); 

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(NEGRO_ELITE);
        setContentPane(panelPrincipal);

        JPanel loginCard = new JPanel(new GridBagLayout());
        loginCard.setBackground(GRIS_TECNICO);
        loginCard.setBorder(new LineBorder(AMARILLO_POWER, 3));

        panelPrincipal.add(loginCard);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 40, 10, 40);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel lblLogo = new JLabel();
        try {
            lblLogo.setIcon(new ImageIcon(InicioSesion.class.getResource("/img/LOGO RHGR_BUILD.png")));
        } catch (Exception e) {
            lblLogo.setText("LOGO RHGR");
        }
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        loginCard.add(lblLogo, gbc);

        JLabel lblTitulo = new JLabel("IDENTIFICACIÓN DE AGENTE");
        lblTitulo.setFont(new Font("Impact", Font.PLAIN, 22));
        lblTitulo.setForeground(ROJO_HEROE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        loginCard.add(lblTitulo, gbc);

        JLabel lblUser = new JLabel("NOMBRE DE USUARIO");
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 12));

        gbc.gridy = 2;
        loginCard.add(lblUser, gbc);

        NomUsuario = new JTextField();
        NomUsuario.setFont(new Font("Monospaced", Font.BOLD, 16));
        NomUsuario.setBorder(new LineBorder(NEGRO_ELITE, 1));
        NomUsuario.setPreferredSize(new Dimension(200, 45));

        gbc.gridy = 3;
        loginCard.add(NomUsuario, gbc);

        JLabel lblPass = new JLabel("CONTRASEÑA DE ACCESO");
        lblPass.setFont(new Font("Tahoma", Font.BOLD, 12));

        gbc.gridy = 4;
        loginCard.add(lblPass, gbc);

        ContraseñaCampo = new JPasswordField();
        ContraseñaCampo.setFont(new Font("Monospaced", Font.BOLD, 16));
        ContraseñaCampo.setBorder(new LineBorder(NEGRO_ELITE, 1));
        ContraseñaCampo.setPreferredSize(new Dimension(200, 45));

        gbc.gridy = 5;
        loginCard.add(ContraseñaCampo, gbc);

        entrar = new JButton("ACCEDER AL SISTEMA");
        entrar.setFont(new Font("Impact", Font.PLAIN, 20));
        entrar.setBackground(NEGRO_ELITE);
        entrar.setForeground(AMARILLO_POWER);
        entrar.setFocusPainted(false);
        entrar.setBorder(new LineBorder(ROJO_HEROE, 2));
        entrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        entrar.setPreferredSize(new Dimension(200, 55));

        gbc.gridy = 6;
        gbc.insets = new Insets(30, 40, 10, 40);
        loginCard.add(entrar, gbc);

        gbc.gridy = 7;
        gbc.weighty = 1;
        loginCard.add(Box.createVerticalGlue(), gbc);

        JLabel lblCopy = new JLabel("DESIGNED BY EDNA MODA - NO CAPES");
        lblCopy.setFont(new Font("Tahoma", Font.ITALIC, 10));
        lblCopy.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 8;
        gbc.weighty = 0;
        loginCard.add(lblCopy, gbc);

        mostrarBienvenida();
    }

    private void mostrarBienvenida() {
    	JOptionPane.showMessageDialog(
        	    this,
        	    "Le damos la bienvenida a la aplicacion de gestion de citas de Edna Moda\n"
        	    + "segun el rol que contengas, podras realizar una serie de acciones:\n"
        	    + "\n"
        	    + "--> Aprendiz: solo puedes ver tus citas en las que trabajas, sin tener\n"
        	    + "acceso a la creacion y modificacion de estas\n"
        	    + "\n"
        	    + "--> Oficial: eres capaz de ver todas las citas, modificar tus citas\n"
        	    + "crear citas (que el maestro haya creado para usted) asignando a los aprendices, y\n "
        	    + "ver todos los talleres\n"
        	    + "\n"
        	    + "--> Maestro: Contiene todas las funciones habilitadas, las cuales son\n "
        	    + "ver y modificar citas, reservar citas donde y cuando quieran y\n"
        	    + "asignarlas a un oficial. Añadir, modificar y borrar clientes. Crear,\n"
        	    + "modificar y borrar citas y añadir, modificar y borrar talleres disponibles\n",
        	    "¡Bienvenido!",
        	    JOptionPane.INFORMATION_MESSAGE
        	);
    }

 /** Método para obtener el botón de entrar (usado por el controlador para asignar el listener) */
    
    /**
     * 
     * @return
     */
    public JButton getBtnEntrar() {
        return entrar;
    }
    
    /** Método para obtener el nombre ingresado */
    
    /**
     * 
     * @return
     */
    public String getInfoNombre() {
        return NomUsuario.getText();
    }
    
    /** Método para obtener la contraseña ingresada */
    
    /**
     * 
     * @return
     */
    public char[] getInfoContrasenia() {
        return ContraseñaCampo.getPassword();
    }
    
    /** Método para mostrar mensajes de error u otra información */
    
    /**
     * 
     * @param respuesta
     */
    public void setRespuesta(String respuesta) {
        javax.swing.JOptionPane.showMessageDialog(InicioSesion.this, 
                respuesta,
                respuesta, javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}