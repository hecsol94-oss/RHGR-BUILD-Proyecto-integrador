package vista;

// Importaciones necesarias para la interfaz gráfica
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

// Clase que representa la ventana de inicio de sesión
public class InicioSesion extends JFrame {
    
    // Campo de texto para el nombre de usuario
    private JTextField NomUsuario;
    
    // Campo de texto para la contraseña (enmascarada)
    private JPasswordField ContraseñaCampo;
    
    // Botón para iniciar sesión
    private JButton entrar;
    
    // Constructor de la ventana
    public InicioSesion() {
        // Configuración básica de la ventana
        setTitle("RHGR EDNA MODA"); // Título
        setBounds(100, 100, 400, 600); // Tamaño y posición
        getContentPane().setForeground(new Color(255, 255, 255)); // Color de fondo
        getContentPane().setLayout(null); // Layout absoluto
        
        // Etiqueta del campo de usuario
        JLabel NombreUsuario = new JLabel("Nombre de usuario");
        NombreUsuario.setForeground(new Color(0, 0, 0));
        NombreUsuario.setBackground(Color.WHITE);
        NombreUsuario.setBounds(72, 21, 115, 14);
        getContentPane().add(NombreUsuario);
        
        // Etiqueta del campo de contraseña
        JLabel Contraseña = new JLabel("Contraseña");
        Contraseña.setForeground(new Color(0, 0, 0));
        Contraseña.setBounds(72, 77, 96, 14);
        getContentPane().add(Contraseña);
        
        // Campo de texto para el nombre de usuario
        NomUsuario = new JTextField();
        NomUsuario.setBounds(72, 46, 233, 20);
        getContentPane().add(NomUsuario);
        NomUsuario.setColumns(10);
        
        // Campo de texto enmascarado para la contraseña
        ContraseñaCampo = new JPasswordField();
        ContraseñaCampo.setBounds(72, 102, 233, 20);
        getContentPane().add(ContraseñaCampo);
        
        // Botón para iniciar sesión
        entrar = new JButton("Entrar");
        entrar.setBounds(100, 526, 181, 22);
        getContentPane().add(entrar);
        
        // Etiqueta con el nombre de la aplicación
        JLabel lblNombreAplicacion = new JLabel("EDNA MODA");
        lblNombreAplicacion.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNombreAplicacion.setBounds(149, 143, 115, 14);
        getContentPane().add(lblNombreAplicacion);
        
        // Etiqueta descriptiva del sistema
        JLabel lblNewLabel = new JLabel("Sistema de Gestion de citas");
        lblNewLabel.setBounds(129, 168, 176, 14);
        getContentPane().add(lblNewLabel);
        
        // Logo de la empresa
        JLabel lblLogo = new JLabel("");
        lblLogo.setIcon(new ImageIcon(InicioSesion.class.getResource("/img/LOGO RHGR_BUILD.png")));
        lblLogo.setBounds(36, 193, 316, 322);
        getContentPane().add(lblLogo);
    }
    
    // Método para obtener el nombre ingresado
    public String getInfoNombre() {
        return NomUsuario.getText();
    }
    
    // Método para obtener la contraseña ingresada
    public char[] getInfoContrasenia() {
        return ContraseñaCampo.getPassword();
    }
    
    // Método para mostrar mensajes de error u otra información
    public void setRespuesta(String respuesta) {
        javax.swing.JOptionPane.showMessageDialog(InicioSesion.this, 
                respuesta,
                respuesta, javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}